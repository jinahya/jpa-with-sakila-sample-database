package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence.util.TemporalUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.TemporalAccessor;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;

public final class ___PersistenceServiceUtils {

    private static final Logger log = getLogger(lookup().lookupClass());

    /**
     * Returns a predicate tests specified expression whether it is
     * {@link CriteriaBuilder#between(Expression, Comparable, Comparable) between} specified ends.
     *
     * @param builder      a criteria builder.
     * @param expression   the expression to test.
     * @param minInclusive a minimum inclusive start value.
     * @param maxInclusive a maximum inclusive end value.
     * @param <T>          attribute type parameter
     * @return a predicate tests entities whose specified attributes are between {@code minInclusive}, and
     * {@code maxInclusive}, both inclusive.
     */
    static <T extends TemporalAccessor & Comparable<? super T>> Predicate between(
            final CriteriaBuilder builder, final Expression<T> expression, final T minInclusive, final T maxInclusive) {
        requireNonNull(builder, "builder is null");
        requireNonNull(expression, "expression is null");
        requireNonNull(minInclusive, "minInclusive is null");
        requireNonNull(maxInclusive, "maxInclusive is null");
        return builder.and(
                builder.greaterThanOrEqualTo(expression, minInclusive),
                builder.lessThanOrEqualTo(expression, maxInclusive)
        );
    }

    /**
     * Returns a predicate tests specified expression whether it is
     * {@link CriteriaBuilder#greaterThanOrEqualTo(Expression, Comparable) greater than or equal to} specified starting
     * value and is {@link CriteriaBuilder#lessThan(Expression, Comparable) less than} specified end value.
     *
     * @param builder      a criteria builder.
     * @param expression   the expression to test.
     * @param minInclusive a minimum inclusive start value.
     * @param maxExclusive a maximum exclusive end value.
     * @param <T>          attribute type parameter
     * @return a predicate tests entities whose specified attributes is between specified ends.
     */
    static <T extends TemporalAccessor & Comparable<? super T>> Predicate betweenHalfOpen(
            final CriteriaBuilder builder, final Expression<T> expression, final T minInclusive, final T maxExclusive) {
        requireNonNull(builder, "builder is null");
        requireNonNull(expression, "expression is null");
        requireNonNull(minInclusive, "minInclusive is null");
        requireNonNull(maxExclusive, "maxExclusive is null");
        return builder.and(
                builder.greaterThanOrEqualTo(expression, minInclusive),
                builder.lessThan(expression, maxExclusive)
        );
    }

    static Predicate dateIn(final CriteriaBuilder builder, final Expression<LocalDate> expression, final Year year) {
        requireNonNull(builder, "builder is null");
        requireNonNull(expression, "expression is null");
        requireNonNull(year, "year is null");
        return TemporalUtils.applyStartEndDateOf(year, (s, e) -> between(builder, expression, s, e));
    }

    static Predicate dateIn(final CriteriaBuilder builder, final Expression<LocalDate> expression,
                            final YearMonth month) {
        requireNonNull(builder, "builder is null");
        requireNonNull(month, "month is null");
        return TemporalUtils.applyStartEndDateOf(month, (s, e) -> between(builder, expression, s, e));
    }

    static Predicate dateTimeIn(final CriteriaBuilder builder, final Expression<LocalDateTime> expression,
                                final Year year) {
        requireNonNull(year, "year is null");
        return TemporalUtils.applyStartEndTimeOf(year, (s, e) -> betweenHalfOpen(builder, expression, s, e));
    }

    static Predicate dateTimeIn(final CriteriaBuilder builder, final Expression<LocalDateTime> expression,
                                final YearMonth month) {
        requireNonNull(month, "month is null");
        return TemporalUtils.applyStartEndTimeOf(month, (s, e) -> betweenHalfOpen(builder, expression, s, e));
    }

    static Predicate dateTimeIn(final CriteriaBuilder builder, final Expression<LocalDateTime> expression,
                                final LocalDate date) {
        requireNonNull(date, "date is null");
        return TemporalUtils.applyStartEndTimeOf(date, (s, e) -> betweenHalfOpen(builder, expression, s, e));
    }

    /**
     * Creates a new instance.
     */
    private ___PersistenceServiceUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
