package com.github.jinahya.sakila.persistence.service;

import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotNull;

import java.util.function.Function;

interface ____PersistenceService {

    /**
     * Applies an entity manager, as joined to a transaction, to specified function, and returns the result.
     *
     * @param function the function to be applied with the entity manager.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    <R> R applyEntityManagerInTransaction(@NotNull Function<? super EntityManager, ? extends R> function);

    <R> R applyEntityManager(@NotNull Function<? super EntityManager, ? extends R> function);
}
