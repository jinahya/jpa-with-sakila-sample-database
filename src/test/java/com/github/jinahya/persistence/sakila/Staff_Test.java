package com.github.jinahya.persistence.sakila;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class Staff_Test
        extends _BaseEntityTest<Staff, Integer> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    Staff_Test() {
        super(Staff.class, Integer.class);
    }

    @DisplayName("activeAsBoolean")
    @Nested
    class ActiveAsBooleanTest {

        @DisplayName("getActive()null -> getActiveAsBoolean()null")
        @Test
        void getActiveAsBoolean_Null_GetActiveNull() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            when(instance.getActive()).thenReturn(null);
            final Boolean activeAsBoolean;
            try {
                activeAsBoolean = instance.getActiveAsBoolean();
            } catch (final UnsupportedOperationException uoe) {
                log.warn("unsupported", uoe);
                return;
            }
            // THEN
            assertThat(activeAsBoolean).isNull();
            verify(instance, times(1)).getActive();
        }

        @DisplayName("getActive()0 -> getActiveAsBoolean()FALSE")
        @Test
        void getActiveAsBoolean_False_0() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            when(instance.getActive()).thenReturn(0);
            final Boolean activeAsBoolean;
            try {
                activeAsBoolean = instance.getActiveAsBoolean();
            } catch (final UnsupportedOperationException uoe) {
                log.warn("unsupported", uoe);
                return;
            }
            // THEN
            assertThat(activeAsBoolean).isFalse();
            verify(instance, times(1)).getActive();
        }

        @DisplayName("getActive()!0 -> getActiveAsBoolean()TRUE")
        @Test
        void getActiveAsBoolean_False_Not0() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            when(instance.getActive()).thenReturn(ThreadLocalRandom.current().nextInt() | 1); // !0, simply.
            final Boolean activeAsBoolean;
            try {
                activeAsBoolean = instance.getActiveAsBoolean();
            } catch (final UnsupportedOperationException uoe) {
                log.warn("unsupported", uoe);
                return;
            }
            // THEN
            assertThat(activeAsBoolean).isTrue();
            verify(instance, times(1)).getActive();
        }

        @DisplayName("setActiveAsBoolean(null) -> setActive(null)")
        @Test
        void setActiveAsBoolean_SetActiveNull_Null() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            try {
                instance.setActiveAsBoolean(null);
            } catch (final UnsupportedOperationException uoe) {
                log.warn("unsupported", uoe);
                return;
            }
            // THEN
            verify(instance, times(1)).setActive(null);
        }

        @DisplayName("setActiveAsBoolean(FALSE) -> setActive(0)")
        @Test
        void setActiveAsBoolean_SetActive0_False() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            try {
                instance.setActiveAsBoolean(Boolean.FALSE);
            } catch (final UnsupportedOperationException uoe) {
                log.warn("unsupported", uoe);
                return;
            }
            // THEN
            verify(instance, times(1)).setActive(0);
        }

        @DisplayName("setActiveAsBoolean(TRUE) -> setActive(1)")
        @Test
        void setActiveAsBoolean_SetActive1_True() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            try {
                instance.setActiveAsBoolean(Boolean.TRUE);
            } catch (final UnsupportedOperationException uoe) {
                log.warn("unsupported", uoe);
                return;
            }
            // THEN
            verify(instance, times(1)).setActive(1);
        }
    }
}
