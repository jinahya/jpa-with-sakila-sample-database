package com.github.jinahya.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.Objects;
import java.util.function.Function;

/**
 * An abstract service class gets injected with a managed entity manager.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@ValidateOnExecution(type = {ExecutableType.ALL})
@ApplicationScoped
@Slf4j
abstract class ___PersistenceService {

    /**
     * Creates a new instance.
     */
    ___PersistenceService() {
        super();
    }

    /**
     * Applies a database connection, {@link EntityManager#unwrap(Class) unwrapped} from an injected instance of
     * {@link EntityManager}, to specified function, and returns the result.
     *
     * @param function the function to be applied with the connection.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    <R> R applyConnection(final Function<? super Connection, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return applyEntityManagerInTransaction(em -> ____Utils.applyConnection(em, function));
    }

    <R> R applyEntityManagerInTransaction(final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return ____Utils.applyEntityManagerInTransaction(getEntityManager(), function);
    }

    <R> R applyEntityManager(final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return function.apply(getEntityManager());
    }

    private EntityManager getEntityManager() {
        var result = entityManagerProxy;
        if (result == null) {
            entityManagerProxy = result = ____Utils.createUnCloseableProxy(EntityManager.class, entityManager);
        }
        return result;
    }

    @Inject
    private EntityManager entityManager;

    private EntityManager entityManagerProxy;
}
