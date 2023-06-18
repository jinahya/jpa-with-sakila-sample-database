package com.github.jinahya.sakila.persistence.util;

import org.slf4j.Logger;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalAmount;
import java.util.Objects;
import java.util.function.BiFunction;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Utilities related to temporal classes.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class TemporalUtils {

    private static final Logger log = getLogger(lookup().lookupClass());

    private static long getUnitsOf(final TemporalAmount amount, final Temporal now, final ChronoUnit unit) {
        Objects.requireNonNull(unit, "unit is null");
        Objects.requireNonNull(now, "now is null");
        Objects.requireNonNull(amount, "amount is null");
        return unit.between(now, now.plus(amount));
    }

    private static long getMinutesOf(final TemporalAmount amount, final Temporal now) {
        return getUnitsOf(amount, now, ChronoUnit.MINUTES);
    }

    public static long getMinutesOf(final TemporalAmount amount) {
        for (final var unit : amount.getUnits()) {
            if (unit == ChronoUnit.MINUTES) {
                continue;
            }
            if (amount.get(unit) != 0L) {
                return getMinutesOf(amount, Instant.now());
            }
        }
        return amount.get(ChronoUnit.MINUTES);
    }

    private static long getDaysOf(final TemporalAmount amount, final Temporal now) {
        return getUnitsOf(amount, now, ChronoUnit.DAYS);
    }

    public static long getDaysOf(final TemporalAmount amount) {
        for (final var unit : amount.getUnits()) {
            if (unit == ChronoUnit.DAYS) {
                continue;
            }
            if (amount.get(unit) != 0L) {
                return getDaysOf(amount, Instant.now());
            }
        }
        return amount.get(ChronoUnit.DAYS);
    }

    public static <R> R applyStartEndDateOf(
            final Year year, final BiFunction<? super LocalDate, ? super LocalDate, ? extends R> function) {
        Objects.requireNonNull(year, "year is null");
        Objects.requireNonNull(function, "function is null");
        final var startInclusive = year.atDay(1).with(TemporalAdjusters.firstDayOfYear());
        final var endInclusive = year.atDay(1).with(TemporalAdjusters.lastDayOfYear());
        return function.apply(startInclusive, endInclusive);
    }

    public static <R> R applyStartEndDateOf(
            final YearMonth month, final BiFunction<? super LocalDate, ? super LocalDate, ? extends R> function) {
        Objects.requireNonNull(month, "month is null");
        Objects.requireNonNull(function, "function is null");
        final var startInclusive = month.atDay(1);
        final var endInclusive = month.atEndOfMonth();
        return function.apply(startInclusive, endInclusive);
    }

    public static <R> R applyStartEndTimeOf(
            final Year year, final BiFunction<? super LocalDateTime, ? super LocalDateTime, ? extends R> function) {
        Objects.requireNonNull(year, "year is null");
        final var startInclusive = LocalDate.ofYearDay(year.getValue(), 1).atStartOfDay();
        final var endExclusive = startInclusive.plusYears(1L);
        return function.apply(startInclusive, endExclusive);
    }

    public static <R> R applyStartEndTimeOf(
            final YearMonth month,
            final BiFunction<? super LocalDateTime, ? super LocalDateTime, ? extends R> function) {
        Objects.requireNonNull(month, "month is null");
        final var startInclusive = month.atDay(1).atStartOfDay();
        final var endExclusive = startInclusive.plusMonths(1L);
        return function.apply(startInclusive, endExclusive);
    }

    public static <R> R applyStartEndTimeOf(
            final LocalDate date,
            final BiFunction<? super LocalDateTime, ? super LocalDateTime, ? extends R> function) {
        Objects.requireNonNull(date, "date is null");
        final var startInclusive = date.atStartOfDay();
        final var endExclusive = startInclusive.plusDays(1);
        return function.apply(startInclusive, endExclusive);
    }

    private TemporalUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
