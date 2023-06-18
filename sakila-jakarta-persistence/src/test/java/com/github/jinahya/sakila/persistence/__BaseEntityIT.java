package com.github.jinahya.sakila.persistence;

import com.github.jinahya.sakila.persistence.util.PersistenceUtils;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.function.Function;

@EnableAutoWeld
@AddPackages({___EntityManagerFactoryProducer.class})
abstract class __BaseEntityIT<T extends __BaseEntity<U>, U extends Comparable<? super U>>
        extends ___BaseEntityTestBase<T, U> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
        return PersistenceUtils.applyEntityManagerInTransactionAndRollback(entityManager, function);
    }

    @___Uncloseable
    @Inject
    private EntityManager entityManager;
}
