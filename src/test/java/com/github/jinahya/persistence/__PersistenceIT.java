package com.github.jinahya.persistence;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;

import java.util.Objects;
import java.util.function.Function;

@AddPackages({____EntityManagerProducer.class})
@EnableAutoWeld
class __PersistenceIT {

    <R> R applyEntityManager(final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return function.apply(getEntityManager());
    }

    EntityManager getEntityManager() {
        var result = entityManagerProxy;
        if (result == null) {
            result = entityManagerProxy = ____Utils.createUnCloseableProxy(EntityManager.class, entityManager);
        }
        return result;
    }

    @Inject
    private EntityManager entityManager;

    private EntityManager entityManagerProxy;
}
