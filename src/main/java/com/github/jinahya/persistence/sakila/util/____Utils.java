package com.github.jinahya.persistence.sakila.util;

import jakarta.persistence.Column;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static java.lang.invoke.MethodHandles.privateLookupIn;
import static java.lang.invoke.MethodType.methodType;

/**
 * Defines a bunch of utility methods.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class ____Utils {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static <T> T bind(final T obj, final ResultSet results) throws SQLException {
        Objects.requireNonNull(obj, "obj is null");
        Objects.requireNonNull(results, "results is null");
        for (final var field : ____Utils.getFieldsAnnotatedWithColumn(obj.getClass())) {
            final var columnName = field.getAnnotation(Column.class).name();
            final var value = results.getObject(columnName);
            if (!field.canAccess(obj)) {
                field.setAccessible(true);
            }
            try {
                field.set(obj, value);
            } catch (final IllegalAccessException e) {
                throw new RuntimeException("failed to set " + field + " with " + value, e);
            }
        }
        return obj;
    }

    public static <T> T bind(final Class<T> cls, final ResultSet results) throws SQLException {
        Objects.requireNonNull(cls, "cls is null");
        return bind(instantiate(cls), results);
    }

    private static <T> T instantiate(final Class<T> type) {
        Objects.requireNonNull(type, "type is null");
        try {
            final var lookup = privateLookupIn(type, MethodHandles.lookup());
            final var constructor = lookup.findConstructor(type, methodType(void.class));
            try {
                return type.cast(constructor.invoke());
            } catch (final Throwable t) {
                throw new RuntimeException("failed to instantiate " + type);
            }
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException("failed to instantiate " + type, roe);
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

    /**
     * Creates a proxy of specified object whose {@code close()} method is prohibited.
     *
     * @param cls type of the object.
     * @param obj the object to be proxied.
     * @param <T> proxy type parameter
     * @return a proxy instance of {@code obj}.
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T createUnCloseableProxy(final Class<T> cls, final T obj) {
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
                        throw new UnsupportedOperationException("you're not allowed to close " + obj);
                    }
                    try {
                        return m.invoke(obj, a);
                    } catch (final InvocationTargetException ite) {
                        // just for debugging
                        if (ite.getTargetException() instanceof ConstraintViolationException cve) {
                            cve.getConstraintViolations().forEach(cv -> {
                                log.error("constraint violation: {}", cv);
                            });
                        }
                        throw ite;
                    }
                }
        );
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

    public static List<Field> getFieldsAnnotatedWithColumn(final Class<?> cls) {
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

    public static String sha1(final byte[] password) {
        if (Objects.requireNonNull(password, "password is null").length == 0) {
            throw new IllegalArgumentException("empty password");
        }
        return digest("SHA-1", password);
    }

    public static String sha2(final byte[] password) {
        if (Objects.requireNonNull(password, "password is null").length == 0) {
            throw new IllegalArgumentException("empty password");
        }
        return digest("SHA-256", password);
    }

    private ____Utils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
