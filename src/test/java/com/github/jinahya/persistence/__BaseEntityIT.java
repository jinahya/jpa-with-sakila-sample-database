package com.github.jinahya.persistence;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.RollbackException;
import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@EnableAutoWeld
@AddPackages({EntityManagerFactoryProducer.class})
@Slf4j
abstract class __BaseEntityIT<T extends __BaseEntity<U>, U>
        extends ___BaseEntityTestBase<T, U> {

    protected __BaseEntityIT(final Class<T> entityClass, final Class<U> idClass) {
        super(entityClass, idClass);
    }

    private EntityManager entityManagerProxy() {
        final Method close;
        try {
            close = EntityManager.class.getMethod("close");
        } catch (final NoSuchMethodException nsme) {
            throw new RuntimeException(nsme);
        }
        return (EntityManager) Proxy.newProxyInstance(
                entityManager.getClass().getClassLoader(),
                new Class<?>[]{EntityManager.class},
                (p, m, a) -> {
                    if (m.equals(close)) {
                        throw new UnsupportedOperationException("you're not allowed to close the entity manager");
                    }
                    return m.invoke(entityManager, a);
                }
        );
    }

    /**
     * Applies an injected instance of {@link EntityManager} to specified function, and returns the result.
     *
     * @param function the function.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    protected <R> R applyEntityManager(final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        final EntityManager entityManager = entityManagerProxy();
        final var transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            return function.apply(entityManager);
        } finally {
            try {
                transaction.commit();
            } catch (final RollbackException re) {
                log.error("rolled back", re.getCause());
            }
        }
    }

    /**
     * Accepts an injected instance of {@link EntityManager} to specified consumer.
     *
     * @param consumer the consumer.
     */
    protected void acceptEntityManager(final Consumer<? super EntityManager> consumer) {
        applyEntityManager(em -> {
            consumer.accept(em);
            return null;
        });
    }

    @Inject
    private EntityManager entityManager;
}
