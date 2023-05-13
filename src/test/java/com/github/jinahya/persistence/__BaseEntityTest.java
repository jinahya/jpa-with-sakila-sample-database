package com.github.jinahya.persistence;

import java.lang.reflect.Constructor;
import java.util.Objects;

import static org.mockito.Mockito.spy;

abstract class __BaseEntityTest<T extends __BaseEntity<U>, U> {

    protected __BaseEntityTest(final Class<T> entityClass, final Class<U> idClass) {
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
            final Constructor<T> constructor = entityClass.getConstructor();
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
