package com.github.jinahya.sakila.persistence;

import org.slf4j.Logger;

import java.util.Objects;

import static java.lang.invoke.MethodHandles.lookup;
import static org.mockito.Mockito.spy;
import static org.slf4j.LoggerFactory.getLogger;

abstract class ____BaseEntityTestBase<ENTITY extends __BaseEntity<?>> {

    private static final Logger log = getLogger(lookup().lookupClass());

    ____BaseEntityTestBase(final Class<ENTITY> entityClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
    }

    /**
     * Returns a new instance of {@link #entityClass}.
     *
     * @return a new instance of {@link #entityClass}.
     * @see #newEntitySpy()
     */
    final ENTITY newEntityInstance() {
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
    final ENTITY newEntitySpy() {
        return spy(newEntityInstance());
    }

    /**
     * The entity class to test.
     */
    final Class<ENTITY> entityClass;
}
