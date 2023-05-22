package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static org.mockito.Mockito.spy;

@Slf4j
abstract class ___BaseEntityTestBase<T extends __BaseEntity<U>, U> {

    protected ___BaseEntityTestBase(final Class<T> entityClass, final Class<U> idClass) {
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
    protected T newEntityInstance() {
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
    protected T newEntitySpy() {
        return spy(newEntityInstance());
    }

    /**
     * The entity class to test.
     */
    protected final Class<T> entityClass;

    /**
     * The type of id of the {@link #entityClass}
     */
    protected final Class<U> idClass;
}
