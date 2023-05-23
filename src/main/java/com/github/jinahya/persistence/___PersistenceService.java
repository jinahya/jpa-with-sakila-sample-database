package com.github.jinahya.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.Objects;
import java.util.function.Function;

@ApplicationScoped
@Slf4j
abstract class ___PersistenceService {

    ___PersistenceService() {
        super();
    }

    <R> R applyConnection(final Function<? super Connection, ? extends R> function) {
        return applyEntityManagerInTransaction(em -> _EntityManagerUtils.applyConnection(em, function));
    }

    <R> R applyEntityManagerInTransaction(final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return _EntityManagerUtils.applyEntityManagerInTransaction(getEntityManager(), function);
    }

    <R> R applyEntityManager(final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return function.apply(getEntityManager());
    }

    private EntityManager getEntityManager() {
        var result = entityManagerProxy;
        if (result == null) {
            result = entityManagerProxy = _LangUtils.uncloseableProxy(EntityManager.class, entityManager);
        }
        return result;
    }

    @Inject
    private EntityManager entityManager;

    private EntityManager entityManagerProxy;
}
