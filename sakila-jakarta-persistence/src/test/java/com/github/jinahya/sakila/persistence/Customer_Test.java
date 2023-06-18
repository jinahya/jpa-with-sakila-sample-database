package com.github.jinahya.sakila.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class Customer_Test
        extends _BaseEntityTest<Customer, Integer> {

    Customer_Test() {
        super(Customer.class, Integer.class);
    }

    @DisplayName("getActiveAsBoolean()Boolean")
    @Nested
    class GetActiveAsBooleanTest {

        @DisplayName("getActive()null -> null")
        @Test
        void __() {
            // GIVEN
            final var instance = newEntitySpy();
            when(instance.getActive()).thenReturn(null);
            clearInvocations(instance); // EclipseLink
            // WHEN
            final var activeAsBoolean = instance.getActiveAsBoolean();
            // THEN
            assertThat(activeAsBoolean).isNull();
            verify(instance, times(1)).getActive();
        }

        @DisplayName("getActive()0 -> FALSE")
        @Test
        void _False_Zero() {
            // GIVEN
            final var instance = newEntitySpy();
            when(instance.getActive()).thenReturn(0);
            clearInvocations(instance); // EclipseLink
            // WHEN
            final var activeAsBoolean = instance.getActiveAsBoolean();
            // THEN
            assertThat(activeAsBoolean).isFalse();
            verify(instance, times(1)).getActive();
        }

        @DisplayName("getActive()!0 -> TRUE")
        @Test
        void _True_NonZero() {
            // GIVEN
            final var instance = newEntitySpy();
            when(instance.getActive()).thenReturn(current().nextInt() | 1);
            clearInvocations(instance); // EclipseLink
            // WHEN
            final var activeAsBoolean = instance.getActiveAsBoolean();
            // THEN
            assertThat(activeAsBoolean).isTrue();
            verify(instance, times(1)).getActive();
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

        @DisplayName("(TRUE) -> setActive(1)")
        @Test
        void _SetActiveOne_True() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setActiveAsBoolean(Boolean.TRUE);
            // THEN
            verify(instance, times(1)).setActive(1);
        }
    }

    @DisplayName("activate()")
    @Nested
    class ActivateTest {

        @DisplayName("() -> setActive(TRUE)")
        @Test
        void __() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            final var result = instance.activate();
            // THEN
            verify(instance, times(1)).setActiveAsBoolean(Boolean.TRUE);
            assertThat(result).isSameAs(instance);
        }
    }

    @DisplayName("deactivate()")
    @Nested
    class DeactivateTest {

        @DisplayName("() -> setActive(FALSE)")
        @Test
        void __() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            final var result = instance.deactivate();
            // THEN
            verify(instance, times(1)).setActiveAsBoolean(Boolean.FALSE);
            assertThat(result).isSameAs(instance);
        }
    }
}
