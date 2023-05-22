package com.github.jinahya.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * An abstract test class for testing subclasses of {@link _BaseEntity} class.
 *
 * @param <T> entity type parameter
 * @param <U> id type parameter
 */
abstract class _BaseEntityTest<T extends _BaseEntity<U>, U>
        extends __BaseEntityTest<T, U> {

    /**
     * Creates a new instance with specified entity class and id class.
     *
     * @param entityClass the entity class to test.
     * @param idClass     the type of id of the {@code entityClass}.
     */
    protected _BaseEntityTest(final Class<T> entityClass, final Class<U> idClass) {
        super(entityClass, idClass);
    }

    @DisplayName("toString()!blank")
    @Test
    void toString_NotBlank_() {
        final var string = newEntityInstance().toString();
        assertThat(string).isNotBlank();
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
            final var dateTime = instance.getLastUpdateAsLocalDateTime();
            // THEN
            assertThat(dateTime).isNull();
        }

        @DisplayName("getLastUpdate()!null -> !null")
        @Test
        void _NotNull_NonNull() {
            // GIVEN
            final T instance = newEntitySpy();
            // WHEN
            final var timestamp = new Timestamp(System.currentTimeMillis());
            when(instance.getLastUpdate()).thenReturn(timestamp);
            // THEN
            final var dateTime = instance.getLastUpdateAsLocalDateTime();
            assertThat(dateTime).isNotNull().satisfies(v -> {
                assertThat(Timestamp.valueOf(v)).isEqualTo(timestamp);
            });
        }
    }
}
