package com.github.jinahya.persistence.sakila;

import com.github.jinahya.persistence.sakila.util.____Utils;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;

import java.util.Objects;
import java.util.function.Function;

@AddPackages({___EntityManagerProducer.class})
@EnableAutoWeld
public class __PersistenceIT {

    /**
     * Applies an injected instance of {@link EntityManager} to specified function, and returns the result.
     *
     * @param function the function.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    protected <R> R applyEntityManager(final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return ____Utils.applyEntityManagerInTransactionAndRollback(entityManager, function);
    }

    @___Uncloseable
    @Inject
    private EntityManager entityManager;
}
