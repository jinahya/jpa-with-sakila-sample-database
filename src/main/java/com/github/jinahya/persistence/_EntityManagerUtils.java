package com.github.jinahya.persistence;

import jakarta.persistence.EntityManager;

import java.sql.Connection;
import java.util.Objects;
import java.util.function.Function;

class _EntityManagerUtils {

    static <R> R applyConnectionInTransaction(final EntityManager entityManager,
                                              final Function<? super Connection, ? extends R> function) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(function, "function is null");
        return applyInTransaction(entityManager, em -> {
            final var connection = em.unwrap(Connection.class);
            final var uncloseable = _LangUtils.uncloseableProxy(Connection.class, connection);
            return function.apply(connection);
        });
    }

    static <R> R applyInTransaction(final EntityManager entityManager,
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
