package com.github.jinahya.sakila.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.github.jinahya.sakila.persistence.__ActivatableTestUtils.getActive_DoesNotThrow_;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * An abstract class for testing entities implement {@link __Activatable} interface.
 *
 * @param <ENTITY> entity type parameter
 */
abstract class __ActivatableEntityTest<
        ENTITY extends __BaseEntity<?> & __Activatable<ENTITY>>
        extends ____BaseEntityTestBase<ENTITY> {

    __ActivatableEntityTest(final Class<ENTITY> entityClass) {
        super(entityClass);
    }

    @DisplayName("getActive()Integer")
    @Nested
    class GetActiveTest {

        @DisplayName("() does not throw")
        @Test
        void _DoesNotThrow_() {
            getActive_DoesNotThrow_(__ActivatableEntityTest.this::newEntityInstance);
        }
    }

    @DisplayName("setActive(Integer)")
    @Nested
    class setActiveTest {

        @DisplayName("(null) does not throw")
        @Test
        void _DoesNotThrow_Null() {
            final var instance = newEntityInstance();
            assertThatCode(
                    () -> {
                        instance.setActive(null);
                    }
            ).doesNotThrowAnyException();
        }

        @DisplayName("(!null) does not throw")
        @Test
        void _DoesNotThrow_NotNull() {
            final var instance = newEntityInstance();
            assertThatCode(
                    () -> {
                        instance.setActive(current().nextInt());
                    }
            ).doesNotThrowAnyException();
        }
    }

    @DisplayName("getActiveAsBoolean()Boolean")
    @Nested
    class GetActiveAsBooleanTest {

        @DisplayName("getActive()null -> null")
        @Test
        void _Null_GetActiveNull() {
            // GIVEN
            final var instance = newEntitySpy();
            when(instance.getActive()).thenReturn(null);
            // WHEN
            final var activeAsBoolean = instance.getActiveAsBoolean();
            // THEN
            assertThat(activeAsBoolean).isNull();
        }

        @DisplayName("getActive()zero -> FALSE")
        @Test
        void _False_GetActiveZero() {
            // GIVEN
            final var instance = newEntitySpy();
            when(instance.getActive()).thenReturn(0);
            // WHEN
            final var activeAsBoolean = instance.getActiveAsBoolean();
            // THEN
            assertThat(activeAsBoolean).isFalse();
        }

        @DisplayName("getActive()!zero -> TRUE")
        @Test
        void _True_GetActiveNonZero() {
            // GIVEN
            final var instance = newEntitySpy();
            when(instance.getActive()).thenReturn(current().nextInt() | 1);
            // WHEN
            final var activeAsBoolean = instance.getActiveAsBoolean();
            // THEN
            assertThat(activeAsBoolean).isTrue();
        }
    }

    @DisplayName("setActiveAsBoolean(Boolean)")
    @Nested
    class SetActiveAsBooleanTest {

        @DisplayName("(null) -> setActive(null)")
        @Test
        void _SetActiveNull_Null() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setActiveAsBoolean(null);
            // THEN
            verify(instance, times(1)).setActive(null);
        }

        @DisplayName("(FALSE) -> setActive(0)")
        @Test
        void _SetActiveZero_False() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setActiveAsBoolean(Boolean.FALSE);
            // THEN
            verify(instance, times(1)).setActive(0);
        }

        @DisplayName("(TRUE) -> setActive(!0)")
        @Test
        void _SetActiveNonZero_True() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setActiveAsBoolean(Boolean.TRUE);
            // THEN
            verify(instance, times(1)).setActive(intThat(v -> v != 0));
        }
    }
}
