package com.github.jinahya.persistence;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
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
abstract class _BaseEntityIT<T extends _BaseEntity<U>, U>
        extends __BaseEntityTest<T, U> {

    protected _BaseEntityIT(final Class<T> entityClass, final Class<U> idClass) {
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

    protected <R> R applyEntityManager(final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        final EntityManager entityManager = entityManagerProxy();
        final var transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            try {
                return function.apply(entityManager);
            } finally {
                transaction.commit();
            }
        } catch (final Exception e) {
            if (e instanceof RuntimeException re) {
                throw re;
            }
            throw new RuntimeException(e);
        }
    }

    protected <R> R acceptEntityManager(final Consumer<? super EntityManager> consumer) {
        return applyEntityManager(em -> {
            consumer.accept(em);
            return null;
        });
    }

    @Inject
    private EntityManager entityManager;
}
