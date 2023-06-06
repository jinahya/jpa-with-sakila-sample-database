package com.github.jinahya.persistence.sakila.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Defines a bunch of utility methods.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class PersistenceUtils {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final ThreadLocal<Boolean> ROLLBACK = new ThreadLocal<>();

    /**
     * Unwraps specified entity manager into an instance of {@link Connection}.
     *
     * @param entityManager the entity manager to unwrap.
     * @return an instance of {@link Connection} unwrapped from the {@code entityManager}.
     * @see EntityManager#unwrap(Class)
     * @see <a
     * href="https://wiki.eclipse.org/EclipseLink/Examples/JPA/EMAPI#Getting_a_JDBC_Connection_from_an_EntityManager">Getting
     * a JDBC Connection from an EntityManager</a> (EclipseLink)
     * @see <a
     * href="https://docs.jboss.org/hibernate/orm/current/javadocs/org/hibernate/SharedSessionContract.html#doWork(org.hibernate.jdbc.Work)">SharedSessionContract#doWork(Work)</a>
     * (Hibernate)
     */
    private static Connection unwrapConnection(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        // tries to unwrap into an instance of java.sql.Connection
        // EclipseLink
        try {
            final Connection connection = entityManager.unwrap(Connection.class);
            if (connection != null) {
                return connection;
            }
        } catch (final Exception e) {
        }
        // Hibernate
        // https://thorben-janssen.com/hibernate-tips-get-the-sql-connection-used-by-your-hibernate-session/
        try {
            final Class<?> sessionClass = Class.forName("org.hibernate.Session");
            final Object sessionInstance = entityManager.unwrap(sessionClass);
            final Class<?> workClass = Class.forName("org.hibernate.jdbc.ReturningWork");
            final Method executeMethod = workClass.getMethod("execute", Connection.class);
            final Object proxy = Proxy.newProxyInstance(
                    MethodHandles.lookup().lookupClass().getClassLoader(),
                    new Class[]{workClass},
                    (p, m, a) -> {
                        if (m.equals(executeMethod)) {
                            return (Connection) Objects.requireNonNull(a[0], "a[0] is null");
                        }
                        return null;
                    }
            );
            final Method doReturningWorkMethod = sessionClass.getMethod("doReturningWork", workClass);
            return (Connection) doReturningWorkMethod.invoke(sessionInstance, proxy);
        } catch (final ReflectiveOperationException roe) {
        }
        throw new PersistenceException("failed to unwrap connection from " + entityManager);
    }

    /**
     * Applies an instance of {@link Connection}, unwrapped from specified entity manager, to specified funcction, and
     * returns the result.
     *
     * @param entityManager the entity manager from which the {@link Connection} is unwrapped.
     * @param function      the function to be applied with the {@link Connection} unwrapped from the
     *                      {@code entityManager}.
     * @param <R>           result type parameter.
     * @return the result of the {@code function}.
     */
    public static <R> R applyConnection(final EntityManager entityManager,
                                        final Function<? super Connection, ? extends R> function) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(function, "function is null");
        return applyEntityManagerInTransaction(entityManager, em -> {
            final var connection = unwrapConnection(em);
            final var proxy = ReflectionUtils.createUnCloseableProxy(Connection.class, connection);
            return function.apply(proxy);
        });
    }

    /**
     * Applies specified entity manager, <em>joined to a transaction</em>, to specified function, and returns the
     * result.
     *
     * @param entityManager the entity manager.
     * @param function      the function to be applied with the {@code entityManager}.
     * @param <R>           result type parameter
     * @return the result of the {@code function}.
     */
    public static <R> R applyEntityManagerInTransaction(final EntityManager entityManager,
                                                        final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(function, "function is null");
        final boolean rollback = Optional.ofNullable(ROLLBACK.get()).orElse(false);
        ROLLBACK.remove();
        if (entityManager.isJoinedToTransaction()) {
            try {
                return function.apply(entityManager);
            } finally {
                final var transaction = entityManager.getTransaction();
                if (rollback) {
                    transaction.rollback();
                    log.debug("rolled back");
                } else {
                    transaction.commit();
                }
            }
        }
        final var transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            return function.apply(entityManager);
        } finally {
            if (rollback) {
                transaction.rollback();
                log.debug("rolled back");
            } else {
                transaction.commit();
            }
        }
    }

    public static <R> R setRollbackAndGet(final Supplier<? extends R> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        ROLLBACK.set(Boolean.TRUE);
        try {
            return supplier.get();
        } finally {
            ROLLBACK.remove();
        }
    }

    public static <R> R applyEntityManagerInTransactionAndRollback(
            final EntityManager entityManager, final Function<? super EntityManager, ? extends R> function) {
        return setRollbackAndGet(() -> applyEntityManagerInTransaction(entityManager, function));
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

    private PersistenceUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
