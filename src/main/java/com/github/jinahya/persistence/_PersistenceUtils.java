package com.github.jinahya.persistence;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnitUtil;

import java.util.Objects;
import java.util.function.Function;

final class _PersistenceUtils {

    static <R> R applyEntityManagerFactory(final Function<? super EntityManagerFactory, ? extends R> function) {
        try (var emf = Persistence.createEntityManagerFactory(_PersistenceConstants.PERSISTENCE_UNIT_NAME)) {
            return function.apply(emf);
        }
    }

    static <R> R applyPersistenceUnitUtil(final Function<? super PersistenceUnitUtil, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return applyEntityManagerFactory(emf -> function.apply(emf.getPersistenceUnitUtil()));
    }

    static Object getIdentifier(final __BaseEntity<?> entity) {
        return applyPersistenceUnitUtil(puu -> {
            if (!puu.isLoaded(entity)) {
                return null;
            }
            return puu.getIdentifier(entity);
        });
    }

    private _PersistenceUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
