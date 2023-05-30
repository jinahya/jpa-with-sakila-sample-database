package com.github.jinahya.persistence.sakila.util;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Objects;

/**
 * Utilities related to temporal classes.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
public final class TemporalUtils {

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

    private TemporalUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
