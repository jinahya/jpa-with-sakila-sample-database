package com.github.jinahya.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Table;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.lang.invoke.MethodHandles.privateLookupIn;
import static java.lang.invoke.MethodType.methodType;

final class ____Utils {

    /**
     * Finds the value of {@link Table#name() @Table#name} from specified class.
     *
     * @param cls the class.
     * @return the value of {@link Table#name() @Table#name} of {@code cls}.
     */
    static String findTableName(final Class<?> cls) {
        Objects.requireNonNull(cls, "cls is null");
        for (var c = cls; c != null; c = c.getSuperclass()) {
            final var table = c.getAnnotation(Table.class);
            if (table != null) {
                return table.name();
            }
        }
        throw new RuntimeException("unable to find Table#name from " + cls + " and upwards");
    }

    private static Connection unwrapConnection(final EntityManager entityManager) {
        try {
            // https://wiki.eclipse.org/EclipseLink/Examples/JPA/EMAPI#Getting_a_JDBC_Connection_from_an_EntityManager
            final Connection connection = entityManager.unwrap(Connection.class);
            if (connection != null) {
                return connection;
            }
        } catch (final Exception e) {
        }
        try {
            // https://thorben-janssen.com/hibernate-tips-get-the-sql-connection-used-by-your-hibernate-session/
            final Class<?> sessionClass = Class.forName("org.hibernate.Session");
            final Object session = entityManager.unwrap(sessionClass);
            final Class<?> workClass = Class.forName("org.hibernate.jdbc.ReturningWork");
            final Method execute = workClass.getMethod("execute", Connection.class);
            final Object proxy = Proxy.newProxyInstance(
                    MethodHandles.lookup().lookupClass().getClassLoader(),
                    new Class[]{workClass},
                    (p, m, a) -> {
                        if (m.equals(execute)) {
                            return (Connection) Objects.requireNonNull(a[0], "a[0] is null");
                        }
                        return null;
                    }
            );
            final Method doReturningWork = sessionClass.getMethod("doReturningWork", workClass);
            return (Connection) doReturningWork.invoke(session, proxy);
        } catch (final ReflectiveOperationException roe) {
        }
        throw new PersistenceException("failed to unwrap connection from " + entityManager);
    }

    static <R> R applyConnection(final EntityManager entityManager,
                                 final Function<? super Connection, ? extends R> function) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(function, "function is null");
        return applyEntityManagerInTransaction(entityManager, em -> {
            final var connection = unwrapConnection(em);
            final var proxy = createUnCloseableProxy(Connection.class, connection);
            return function.apply(proxy);
        });
    }

    static <R> R applyEntityManagerInTransaction(final EntityManager entityManager,
                                                 final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(function, "function is null");
        if (entityManager.isJoinedToTransaction()) {
            return function.apply(entityManager);
        }
        final var transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            return function.apply(entityManager);
        } finally {
            transaction.commit();
        }
    }

    enum LockType {
        READ,
        WRITE
    }

    static <R> R lockTableAndGet(final Connection connection, final String tableName,
                                 final LockType lockType, final Supplier<? extends R> supplier)
            throws SQLException {
        Objects.requireNonNull(connection, "connection is null");
        Objects.requireNonNull(tableName, "tableName is null");
        Objects.requireNonNull(lockType, "lockType is null");
        Objects.requireNonNull(supplier, "supplier is null");
        try (var statement = connection.createStatement()) {
            final var lock = statement.execute("LOCK TABLES " + tableName + " " + lockType.name());
            try {
                return supplier.get();
            } finally {
                final var unlock = statement.execute("UNLOCK TABLES ");
            }
        }
    }

    private static <T> T bind(final T obj, final ResultSet results) throws ReflectiveOperationException, SQLException {
        Objects.requireNonNull(obj, "obj is null");
        Objects.requireNonNull(results, "results is null");
        for (final var field : ____Utils.getFieldsAnnotatedWithColumn(obj.getClass())) {
            final var columnName = field.getAnnotation(Column.class).name();
            final var value = results.getObject(columnName);
            if (!field.canAccess(obj)) {
                field.setAccessible(true);
            }
            field.set(obj, value);
        }
        return obj;
    }

    static <T> T bind(final Class<T> cls, final ResultSet results) throws ReflectiveOperationException, SQLException {
        Objects.requireNonNull(cls, "cls is null");
        return bind(instantiate(cls), results);
    }

    private static <T> T instantiate(final Class<T> type) throws ReflectiveOperationException {
        Objects.requireNonNull(type, "type is null");
        final var lookup = privateLookupIn(type, MethodHandles.lookup());
        final var constructor = lookup.findConstructor(type, methodType(void.class));
        try {
            return type.cast(constructor.invoke());
        } catch (final Throwable t) {
            throw new RuntimeException("failed to instantiate " + type);
        }
    }

    private static Method findCloseMethod(final Class<?> cls) {
        Objects.requireNonNull(cls, "cls is null");
        for (Class<?> c = cls; c != null; c = c.getSuperclass()) {
            try {
                return c.getMethod("close");
            } catch (final NoSuchMethodException nsme) {
                // ok.
            }
        }
        throw new IllegalArgumentException("unable to find the 'close()` method onward " + cls);
    }

    @SuppressWarnings({"unchecked"})
    static <T> T createUnCloseableProxy(final Class<T> cls, final T obj) {
        if (!Objects.requireNonNull(cls, "cls is null").isInterface()) {
            throw new IllegalArgumentException("cls is not an interface: " + cls);
        }
        Objects.requireNonNull(obj, "obj is null");
        final var method = findCloseMethod(cls);
        return (T) Proxy.newProxyInstance(
                cls.getClassLoader(),
                new Class<?>[]{cls},
                (p, m, a) -> {
                    if (m.equals(method)) {
                        throw new UnsupportedOperationException("you're not allowed to method " + obj);
                    }
                    return m.invoke(obj, a);
                }
        );
    }

    private static final Map<Locale, Map<String, Locale>> DISPLAY_COUNTRIES_AND_LOCALES = new WeakHashMap<>();

    /**
     * Returns the locale whose {@link Locale#getDisplayCountry(Locale) display country} in specified locale matches to
     * specified value.
     *
     * @param displayCountry the value to match.
     * @param inLocale       the locale to get the display language of candidates.
     * @return an optional of matched locale; {@link Optional#empty()} is not found.
     */
    static Optional<Locale> valueOfDisplayCountry(final String displayCountry, final Locale inLocale) {
        Objects.requireNonNull(displayCountry, "displayCountry is null");
        Objects.requireNonNull(inLocale, "inLocale is null");
        return Optional.ofNullable(
                DISPLAY_COUNTRIES_AND_LOCALES
                        .computeIfAbsent((Locale) inLocale.clone(), k -> new HashMap<>())
                        .computeIfAbsent(
                                displayCountry,
                                k -> Arrays.stream(Locale.getAvailableLocales())
                                        .filter(l -> Objects.equals(l.getDisplayCountry(inLocale), k))
                                        .findFirst()
                                        .orElse(null)
                        )
        );
    }

    static Optional<Locale> valueOfDisplayCountryInEnglish(final String displayCountryInEnglish) {
        return valueOfDisplayCountry(displayCountryInEnglish, Locale.ENGLISH);
    }

    private static final Map<Locale, Map<String, Locale>> DISPLAY_LANGUAGES_AND_LOCALES = new WeakHashMap<>();

    static Optional<Locale> valueOfDisplayLanguage(final String displayLanguage, final Locale inLocale) {
        if (Objects.requireNonNull(displayLanguage, "displayLanguage is null").strip().isBlank()) {
            throw new IllegalStateException("displayLanguage is blank");
        }
        Objects.requireNonNull(inLocale, "inLocale is null");
        return Optional.ofNullable(
                DISPLAY_LANGUAGES_AND_LOCALES
                        .computeIfAbsent((Locale) inLocale.clone(), k -> new HashMap<>())
                        .computeIfAbsent(
                                displayLanguage,
                                k -> Arrays.stream(Locale.getAvailableLocales())
                                        .filter(l -> {
                                            final var displayLanguageInLocale = l.getDisplayLanguage(inLocale);
                                            if (displayLanguageInLocale.isBlank()) {
                                                return false;
                                            }
                                            return Objects.equals(displayLanguageInLocale, k);
                                        })
                                        .findFirst()
                                        .orElse(null)
                        )
        );
    }

    static Optional<Locale> valueOfDisplayLanguageInEnglish(final String displayLanguageInEnglish) {
        return valueOfDisplayLanguage(displayLanguageInEnglish, Locale.ENGLISH);
    }

    static void acceptFieldsAnnotatedWithColumns(final Class<?> cls, final Consumer<? super Field> consumer) {
        Objects.requireNonNull(cls, "cls is null");
        Objects.requireNonNull(consumer, "consumer is null");
        for (var c = cls; c != null; c = c.getSuperclass()) {
            Arrays.stream(c.getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(Column.class))
                    .forEach(consumer::accept);
        }
    }

    static List<Field> getFieldsAnnotatedWithColumn(final Class<?> cls) {
        Objects.requireNonNull(cls, "cls is null");
        final var list = new ArrayList<Field>();
        acceptFieldsAnnotatedWithColumns(cls, list::add);
        return list;
    }

    private static String digest(final String algorithm, final byte[] bytes) {
        Objects.requireNonNull(algorithm, "algorithm is null");
        Objects.requireNonNull(bytes, "bytes is null");
        try {
            final var digest = MessageDigest.getInstance(algorithm).digest(bytes);
            return HexFormat.of().withLowerCase().formatHex(digest);
        } catch (final NoSuchAlgorithmException nsae) {
            throw new RuntimeException(nsae);
        }
    }

    static String sha1(final byte[] password) {
        if (Objects.requireNonNull(password, "password is null").length == 0) {
            throw new IllegalArgumentException("empty password");
        }
        return digest("SHA-1", password);
    }

    static String sha2(final byte[] password) {
        if (Objects.requireNonNull(password, "password is null").length == 0) {
            throw new IllegalArgumentException("empty password");
        }
        return digest("SHA-256", password);
    }

    private ____Utils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
