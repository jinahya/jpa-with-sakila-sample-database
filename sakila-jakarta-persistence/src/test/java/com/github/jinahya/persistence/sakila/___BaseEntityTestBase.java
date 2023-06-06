package com.github.jinahya.persistence.sakila;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import static org.mockito.Mockito.spy;

abstract class ___BaseEntityTestBase<T extends __BaseEntity<U>, U extends Comparable<? super U>> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    ___BaseEntityTestBase(final Class<T> entityClass, final Class<U> idClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
        this.idClass = Objects.requireNonNull(idClass, "idClass is null");
    }

    /**
     * Returns a new instance of {@link #entityClass}.
     *
     * @return a new instance of {@link #entityClass}.
     * @see #newEntitySpy()
     */
    T newEntityInstance() {
        try {
            final var constructor = entityClass.getDeclaredConstructor();
            if (!constructor.canAccess(null)) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance();
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException("failed to instantiate " + entityClass, roe);
        }
    }

    /**
     * Returns a new spy instance of {@link #entityClass}.
     *
     * @return a new spy instance of {@link #entityClass}.
     * @see #newEntityInstance()
     */
    T newEntitySpy() {
        return spy(newEntityInstance());
    }

    /**
     * The entity class to test.
     */
    final Class<T> entityClass;

    /**
     * The type of id of the {@link #entityClass}
     */
    final Class<U> idClass;
}
