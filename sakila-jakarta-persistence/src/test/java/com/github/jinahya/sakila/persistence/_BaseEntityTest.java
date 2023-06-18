package com.github.jinahya.sakila.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * An abstract test class for unit-testing subclasses of {@link _BaseEntity} class.
 *
 * @param <T> entity type parameter
 * @param <U> id type parameter
 */
abstract class _BaseEntityTest<T extends _BaseEntity<U>, U extends Comparable<? super U>>
        extends __BaseEntityTest<T, U> {

    /**
     * Creates a new instance with specified entity class and id class.
     *
     * @param entityClass the entity class to test.
     * @param idClass     the type of id of the {@code entityClass}.
     */
    _BaseEntityTest(final Class<T> entityClass, final Class<U> idClass) {
        super(entityClass, idClass);
    }

    @DisplayName("getLastUpdateAsLocalDateTime")
    @Nested
    class GetLastUpdateAsLocalDateTimeTest {

        @DisplayName("getLastUpdate()null -> null")
        @Test
        void _Null_Null() {
            // GIVEN
            final T instance = newEntitySpy();
            // wHEN
            when(instance.getLastUpdate()).thenReturn(null);
            final var lastUpdateAsLocalDateTime = instance.getLastUpdateAsLocalDateTime();
            // THEN
            assertThat(lastUpdateAsLocalDateTime).isNull();
        }

        @DisplayName("getLastUpdate()!null -> !null")
        @Test
        void _NotNull_NonNull() {
            // GIVEN
            final T instance = newEntitySpy();
            final var lastUpdate = new Timestamp(System.currentTimeMillis());
            // WHEN
            when(instance.getLastUpdate()).thenReturn(lastUpdate);
            // THEN
            final var lastUpdateAsLocalDateTime = instance.getLastUpdateAsLocalDateTime();
            assertThat(lastUpdateAsLocalDateTime)
                    .isNotNull()
                    .satisfies(v -> {
                        assertThat(Timestamp.valueOf(v)).isEqualTo(lastUpdate);
                    });
        }
    }
}
