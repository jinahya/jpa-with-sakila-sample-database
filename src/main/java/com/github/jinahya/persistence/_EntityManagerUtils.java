package com.github.jinahya.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Objects;
import java.util.function.Function;

class _EntityManagerUtils {

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
            final var proxy = _LangUtils.uncloseableProxy(Connection.class, connection);
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

    private _EntityManagerUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
