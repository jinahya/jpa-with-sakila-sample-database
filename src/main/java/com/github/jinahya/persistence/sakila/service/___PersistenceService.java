package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.___Uncloseable;
import com.github.jinahya.persistence.sakila.util.PersistenceUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
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
abstract class ___PersistenceService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
        return PersistenceUtils.applyConnection(entityManager, function);
    }

    /**
     * Applies an entity manager, as joined to a transaction, to specified function, and returns the result.
     *
     * @param function the function to be applied with the entity manager.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    <R> R applyEntityManagerInTransaction(final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return PersistenceUtils.applyEntityManagerInTransaction(entityManager, function);
    }

    <R> R applyEntityManager(final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return function.apply(entityManager);
    }

    @___Uncloseable
    @Inject
    private EntityManager entityManager;
}
