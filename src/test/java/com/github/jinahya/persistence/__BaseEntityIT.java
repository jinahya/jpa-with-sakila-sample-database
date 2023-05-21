package com.github.jinahya.persistence;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@EnableAutoWeld
@AddPackages({EntityManagerFactoryProducer.class})
@Slf4j
abstract class __BaseEntityIT<T extends __BaseEntity<U>, U>
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
        return applyEntityManager(function, true);
    }

    /**
     * Accepts an injected instance of {@link EntityManager} to specified consumer.
     *
     * @param consumer the consumer.
     */
    @Deprecated
    void acceptEntityManager(final Consumer<? super EntityManager> consumer) {
        applyEntityManager(em -> {
            consumer.accept(em);
            return null;
        });
    }

    void acceptEntityManager(final Consumer<? super EntityManager> consumer, final boolean rollback) {
        Objects.requireNonNull(consumer, "consumer is null");
        applyEntityManager(
                em -> {
                    consumer.accept(em);
                    return null;
                },
                rollback
        );
    }

    <R> R applyEntityManager(final Function<? super EntityManager, ? extends R> function, final boolean rollback) {
        Objects.requireNonNull(function, "function is null");
        final EntityManager entityManager = getEntityManager();
        final var transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            return function.apply(entityManager);
        } finally {
            if (rollback) {
                log.debug("rolling back...");
                transaction.rollback();
            } else {
                log.debug("committing...");
                transaction.commit();
            }
        }
    }

    private EntityManager getEntityManager() {
        var proxy = entityManagerProxy;
        if (proxy == null) {
            proxy = entityManagerProxy = _LangUtils.uncloseableProxy(EntityManager.class, entityManager);
        }
        return proxy;
    }

    @Inject
    private EntityManager entityManager;

    private EntityManager entityManagerProxy;
}
