package com.github.jinahya.persistence.sakila;

import com.github.jinahya.persistence.sakila.util.____Utils;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;

import java.util.Objects;
import java.util.function.Function;

@EnableAutoWeld
@AddPackages({___EntityManagerFactoryProducer.class})
@Slf4j
abstract class __BaseEntityIT<T extends __BaseEntity<U>, U extends Comparable<? super U>>
        extends ___BaseEntityTestBase<T, U> {

    __BaseEntityIT(final Class<T> entityClass, final Class<U> idClass) {
        super(entityClass, idClass);
    }

    /**
     * Applies an injected instance of {@link EntityManager} to specified function, and returns the result.
     *
     * @param function the function.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    <R> R applyEntityManager(final Function<? super EntityManager, ? extends R> function) {
        return applyEntityManagerInTransaction(function, true);
    }

    <R> R applyEntityManagerInTransaction(final Function<? super EntityManager, ? extends R> function, final boolean rollback) {
        Objects.requireNonNull(function, "function is null");
        return ____Utils.applyEntityManagerInTransaction(entityManager, function);
    }

    @___Uncloseable
    @Inject
    private EntityManager entityManager;
}
