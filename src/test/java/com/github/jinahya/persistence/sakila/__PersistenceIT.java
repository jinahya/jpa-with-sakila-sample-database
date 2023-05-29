package com.github.jinahya.persistence.sakila;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;

import java.util.Objects;
import java.util.function.Function;

@AddPackages({___EntityManagerProducer.class})
@EnableAutoWeld
class __PersistenceIT {

    <R> R applyEntityManager(final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return function.apply(entityManager);
    }

    @___Uncloseable
    @Inject
    private EntityManager entityManager;
}
