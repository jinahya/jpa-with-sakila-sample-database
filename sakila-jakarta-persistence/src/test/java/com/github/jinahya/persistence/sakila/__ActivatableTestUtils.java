package com.github.jinahya.persistence.sakila;

import java.util.Objects;
import java.util.function.Supplier;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Utilities for testing classes implement {@link __Activatable} interface.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
final class __ActivatableTestUtils {

    // ---------------------------------------------------------------------------------------------- getActive()Integer
    static <T extends __BaseEntity<?> & __Activatable<T>> void getActive_DoesNotThrow_(final Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        final var instance = Objects.requireNonNull(supplier.get(), "null supplied");
        assertThatCode(
                () -> {
                    final var active = instance.getActive();
                }
        ).doesNotThrowAnyException();
    }

    // ---------------------------------------------------------------------------------------------- setActive(Integer)
    static <T extends __BaseEntity<?> & __Activatable<T>> void setActive_DoesNotThrow_Null(final Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        final var instance = Objects.requireNonNull(supplier.get(), "null supplied");
        assertThatCode(
                () -> {
                    instance.setActive(null);
                }
        ).doesNotThrowAnyException();
    }

    static <T extends __BaseEntity<?> & __Activatable<T>> void setActive_DoesNotThrow_NotNull(final Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        final var instance = Objects.requireNonNull(supplier.get(), "null supplied");
        assertThatCode(
                () -> {
                    instance.setActive(current().nextInt());
                }
        ).doesNotThrowAnyException();
    }

    // ------------------------------------------------------------------------------------- getActiveAsBoolean()Boolean
    static <T extends __BaseEntity<?> & __Activatable<T>> void getActiveAsBoolean_Null_GetActiveNull(
            final Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        // GIVEN
        final var instance = ofNullable(supplier.get())
                .filter(v -> mockingDetails(v).isSpy())
                .orElseThrow(() -> new IllegalArgumentException("null or non-spy supplied"));
        when(instance.getActive()).thenReturn(null);
        // WHEN
        final var activeAsBoolean = instance.getActiveAsBoolean();
        // THEN
        assertThat(activeAsBoolean).isNull();
    }

    static <T extends __BaseEntity<?> & __Activatable<T>> void getActiveAsBoolean_False_GetActiveZero(
            final Supplier<? extends T> supplier) {
        // GIVEN
        final var instance = ofNullable(supplier.get())
                .filter(v -> mockingDetails(v).isSpy())
                .orElseThrow(() -> new IllegalArgumentException("null or non-spy supplied"));
        when(instance.getActive()).thenReturn(0);
        // WHEN
        final var activeAsBoolean = instance.getActiveAsBoolean();
        // THEN
        assertThat(activeAsBoolean).isFalse();
    }

    static <T extends __BaseEntity<?> & __Activatable<T>> void getActiveAsBoolean_True_GetActiveNonZero(
            final Supplier<? extends T> supplier) {
        // GIVEN
        final var instance = ofNullable(supplier.get())
                .filter(v -> mockingDetails(v).isSpy())
                .orElseThrow(() -> new IllegalArgumentException("null or non-spy supplied"));
        when(instance.getActive()).thenReturn(current().nextInt() | 1);
        // WHEN
        final var activeAsBoolean = instance.getActiveAsBoolean();
        // THEN
        assertThat(activeAsBoolean).isTrue();
    }

    // ------------------------------------------------------------------------------------- setActiveAsBoolean(Boolean)
    static <T extends __BaseEntity<?> & __Activatable<T>> void setActiveAsBoolean_SetActiveNull_Null(
            final Supplier<? extends T> supplier) {
        // GIVEN
        final var instance = ofNullable(supplier.get())
                .filter(v -> mockingDetails(v).isSpy())
                .orElseThrow(() -> new IllegalArgumentException("null or non-spy supplied"));
        // WHEN
        instance.setActiveAsBoolean(null);
        // THEN
        verify(instance, times(1)).setActive(null);
    }

    static <T extends __BaseEntity<?> & __Activatable<T>> void setActiveAsBoolean_SetActiveZero_False(
            final Supplier<? extends T> supplier) {
        // GIVEN
        final var instance = ofNullable(supplier.get())
                .filter(v -> mockingDetails(v).isSpy())
                .orElseThrow(() -> new IllegalArgumentException("null or non-spy supplied"));
        // WHEN
        instance.setActiveAsBoolean(FALSE);
        // THEN
        verify(instance, times(1)).setActive(0);
    }

    static <T extends __BaseEntity<?> & __Activatable<T>> void setActiveAsBoolean_SetActiveNonZero_True(
            final Supplier<? extends T> supplier) {
        // GIVEN
        final var instance = ofNullable(supplier.get())
                .filter(v -> mockingDetails(v).isSpy())
                .orElseThrow(() -> new IllegalArgumentException("null or non-spy supplied"));
        // WHEN
        instance.setActiveAsBoolean(TRUE);
        // THEN
        verify(instance, times(1)).setActive(intThat(v -> v != 0));
    }

    // ------------------------------------------------------------------------------------------------------ activate()
    static <T extends __BaseEntity<?> & __Activatable<T>> void Activate_SetActiveAsBooleanTrue_(final Supplier<? extends T> supplier) {
        // GIVEN
        final var instance = ofNullable(supplier.get())
                .filter(v -> mockingDetails(v).isSpy())
                .orElseThrow(() -> new IllegalArgumentException("null or non-spy supplied"));
        // WHEN
        final var result = instance.activate();
        // THEN
        verify(instance, times(1)).setActiveAsBoolean(TRUE);
        assertThat(result).isSameAs(instance);
    }

    // ---------------------------------------------------------------------------------------------------- deactivate()
    static <T extends __BaseEntity<?> & __Activatable<T>> void Deactivate_SetActiveBooleanFalse_(final Supplier<? extends T> supplier) {
        // GIVEN
        final var instance = ofNullable(supplier.get())
                .filter(v -> mockingDetails(v).isSpy())
                .orElseThrow(() -> new IllegalArgumentException("null or non-spy supplied"));
        // WHEN
        final var result = instance.deactivate();
        // THEN
        verify(instance, times(1)).setActiveAsBoolean(FALSE);
        assertThat(result).isSameAs(instance);
    }

    private __ActivatableTestUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
