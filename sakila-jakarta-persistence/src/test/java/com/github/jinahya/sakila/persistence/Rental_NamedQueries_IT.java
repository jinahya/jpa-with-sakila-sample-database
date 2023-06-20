package com.github.jinahya.sakila.persistence;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Optional;

import static com.github.jinahya.sakila.persistence.RentalComparators.COMPARING_RENTAL_DATE_RENTAL_ID;
import static com.github.jinahya.sakila.persistence.RentalConstants.PARAMETER_RENTAL_DATE_MAX_EXCLUSIVE;
import static com.github.jinahya.sakila.persistence.RentalConstants.PARAMETER_RENTAL_DATE_MAX_INCLUSIVE;
import static com.github.jinahya.sakila.persistence.RentalConstants.PARAMETER_RENTAL_DATE_MIN;
import static com.github.jinahya.sakila.persistence.RentalConstants.PARAMETER_RENTAL_DATE_MIN_INCLUSIVE;
import static com.github.jinahya.sakila.persistence.RentalConstants.PARAMETER_RENTAL_ID;
import static com.github.jinahya.sakila.persistence.RentalConstants.PARAMETER_RENTAL_ID_MIN_EXCLUSIVE;
import static com.github.jinahya.sakila.persistence.RentalConstants.QUERY_FIND_ALL;
import static com.github.jinahya.sakila.persistence.RentalConstants.QUERY_FIND_ALL_BY_RENTAL_DATE_BETWEEN;
import static com.github.jinahya.sakila.persistence.RentalConstants.QUERY_FIND_ALL_BY_RENTAL_DATE_BETWEEN_HALF_OPEN;
import static com.github.jinahya.sakila.persistence.RentalConstants.QUERY_FIND_BY_RENTAL_ID;
import static java.lang.invoke.MethodHandles.lookup;
import static java.time.LocalDateTime.ofEpochSecond;
import static java.util.Comparator.comparing;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // @BeforeAll + !static
class Rental_NamedQueries_IT
        extends __PersistenceIT {

    private static final Logger log = getLogger(lookup().lookupClass());

    /**
     * Creates a new instance.
     */
    Rental_NamedQueries_IT() {
        super();
    }

    @BeforeAll
    private void getMinMaxRentalDate() {
        // https://github.com/eclipse-ee4j/eclipselink/issues/778
        final var values = applyEntityManager(
                em -> em.createQuery(
                                "SELECT MIN(e.rentalDate), MAX(e.rentalDate) FROM Rental AS e",
                                Object[].class)
                        .getSingleResult()
        );
        minRentalDate = Optional.ofNullable((LocalDateTime) values[0])
                .orElseThrow(() -> new RuntimeException("no MIN(rentalDate"));
        maxRentalDate = Optional.ofNullable((LocalDateTime) values[1])
                .orElseThrow(() -> new RuntimeException("no MAX(rentalDate"));
        log.debug("MIN(rentalDate): {}", minRentalDate);
        log.debug("MAX(rentalDate): {}", maxRentalDate);
    }

    @DisplayName(QUERY_FIND_BY_RENTAL_ID)
    @Nested
    class FindByRentalIdTest {

        @DisplayName("(0)NoResultException")
        @Test
        void _NoResultException_0() {
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(QUERY_FIND_BY_RENTAL_ID, Rental.class)
                                    .setParameter(PARAMETER_RENTAL_ID, 0)
                                    .getSingleResult()
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("(1)!null")
        @Test
        void _NotNull_1() {
            final var rentalId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_BY_RENTAL_ID, Rental.class)
                            .setParameter(PARAMETER_RENTAL_ID, rentalId)
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(Rental::getRentalId)
                    .isEqualTo(rentalId);
        }

        @DisplayName("(2)!null")
        @Test
        void _NotNull_2() {
            final var rentalId = 2;
            // TODO: implement!
        }
    }

    @DisplayName(QUERY_FIND_ALL)
    @Nested
    class FindAllRentalIdTest {

        @Test
        void __() {
            final var maxResult = 8192;
            for (var i = 0; ; ) {
                final var rentalIdMinExclusive = i;
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL, Rental.class)
                                .setParameter(PARAMETER_RENTAL_ID_MIN_EXCLUSIVE, rentalIdMinExclusive)
                                .setMaxResults(maxResult)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .isSortedAccordingTo(comparing(Rental::getRentalId));
                if (list.isEmpty()) {
                    break;
                }
                i = list.get(list.size() - 1).getRentalId();
            }
        }
    }

    @DisplayName(QUERY_FIND_ALL_BY_RENTAL_DATE_BETWEEN)
    @Nested
    class FindAllByRentalDateBetweenTest {

        @Test
        void __() {
            final var maxResults = 8192;
            final var rentalDateMinInclusive = minRentalDate;
            final var rentalDateMaxInclusive = maxRentalDate;
            var rentalIdMinExclusiveHolder = 0;
            var rentalDateMinHolder = ofEpochSecond(0L, 0, ZoneOffset.UTC); // 1970-01-01T00:00:00.000Z
            while (true) {
                final var rentalIdMinExclusive = rentalIdMinExclusiveHolder;
                final var rentalDateMin = rentalDateMinHolder;
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_RENTAL_DATE_BETWEEN, Rental.class)
                                .setParameter(PARAMETER_RENTAL_DATE_MIN_INCLUSIVE, rentalDateMinInclusive)
                                .setParameter(PARAMETER_RENTAL_DATE_MAX_INCLUSIVE, rentalDateMaxInclusive)
                                .setParameter(PARAMETER_RENTAL_DATE_MIN, rentalDateMin)
                                .setParameter(PARAMETER_RENTAL_ID_MIN_EXCLUSIVE, rentalIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(Rental::getRentalDate).thenComparing(Rental::getRentalId))
                        .allSatisfy(e -> {
                            // e.rentalDate BETWEEN :rentalDateMinInclusive AND :rentalDateMaxInclusive
                            assertThat(e.getRentalDate()).isBetween(rentalDateMinInclusive, rentalDateMaxInclusive);
                            assertThat(e).satisfiesAnyOf(
                                    // (e.rentalDate = :rentalDateMin AND e.rentalId > :rentalIdMinExclusive)
                                    e2 -> assertThat(e2).satisfies(e3 -> {
                                        assertThat(e3.getRentalDate()).isEqualTo(rentalDateMin);
                                        assertThat(e3.getRentalId()).isGreaterThan(rentalIdMinExclusive);
                                    }),
                                    // OR e.rentalDate > :rentalDateMin
                                    e2 -> assertThat(e2.getRentalDate()).isAfter(rentalDateMin)
                            );
                        });
                if (list.isEmpty()) {
                    break;
                }
                final var last = list.get(list.size() - 1);
                rentalDateMinHolder = last.getRentalDate();
                rentalIdMinExclusiveHolder = last.getRentalId();
            }
        }

        @DisplayName("[2005-07-01 00:00:00 ~ 2005-07-31 23:59:59]")
        @Test
        void _6709_200507() {
            var total = 0;
            final var maxResults = 8192;
            final var rentalDateMinInclusive = YearMonth.of(2005, Month.JULY).atDay(1).atStartOfDay();
            final var rentalDateMaxInclusive = YearMonth.of(2005, Month.JULY).atEndOfMonth().atTime(23, 59, 59);
            var rentalDateMinHolder = ofEpochSecond(0L, 0, ZoneOffset.UTC); // 1970-01-01T00:00:00.000Z
            var rentalIdMinExclusiveHolder = 0;
            while (true) {
                final var rentalDateMin = rentalDateMinHolder;
                final var rentalIdMinExclusive = rentalIdMinExclusiveHolder;
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_RENTAL_DATE_BETWEEN, Rental.class)
                                .setParameter(PARAMETER_RENTAL_DATE_MIN_INCLUSIVE, rentalDateMinInclusive)
                                .setParameter(PARAMETER_RENTAL_DATE_MAX_INCLUSIVE, rentalDateMaxInclusive)
                                .setParameter(PARAMETER_RENTAL_DATE_MIN, rentalDateMin)
                                .setParameter(PARAMETER_RENTAL_ID_MIN_EXCLUSIVE, rentalIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(Rental::getRentalDate).thenComparing(Rental::getRentalId))
                        .allSatisfy(e -> {
                            // e.rentalDate BETWEEN :rentalDateMinInclusive AND :rentalDateMaxInclusive
                            assertThat(e.getRentalDate()).isBetween(rentalDateMinInclusive, rentalDateMaxInclusive);
                            assertThat(e).satisfiesAnyOf(
                                    // (e.rentalDate = :rentalDateMin AND e.rentalId > :rentalIdMinExclusive)
                                    e2 -> assertThat(e2).satisfies(e3 -> {
                                        assertThat(e3.getRentalDate()).isEqualTo(rentalDateMin);
                                        assertThat(e3.getRentalId()).isGreaterThan(rentalIdMinExclusive);
                                    }),
                                    // OR e.rentalDate > :rentalDateMin
                                    e2 -> assertThat(e2.getRentalDate()).isAfter(rentalDateMin)
                            );
                        });
                if (list.isEmpty()) {
                    break;
                }
                final var last = list.get(list.size() - 1);
                rentalDateMinHolder = last.getRentalDate();
                rentalIdMinExclusiveHolder = last.getRentalId();
                total += list.size();
            }
            assertThat(total).isEqualTo(6709);
        }

        @DisplayName("[2005-08-01 00:00:00 ~ 2005-08-31 23:59:59]")
        @Test
        void _5686_200508() {
            var total = 0;
            final var maxResults = 8192;
            final var rentalDateMinInclusive = YearMonth.of(2005, Month.AUGUST).atDay(1).atStartOfDay();
            final var rentalDateMaxInclusive = YearMonth.of(2005, Month.AUGUST).atEndOfMonth().atTime(23, 59, 59);
            var rentalDateMinHolder = ofEpochSecond(0L, 0, ZoneOffset.UTC); // 1970-01-01T00:00:00.000Z
            var rentalIdMinExclusiveHolder = 0;
            while (true) {
                final var rentalDateMin = rentalDateMinHolder;
                final var rentalIdMinExclusive = rentalIdMinExclusiveHolder;
                // TODO: implement!
                break;
            }
//            assertThat(total).isEqualTo(5686);
        }
    }

    @DisplayName(QUERY_FIND_ALL_BY_RENTAL_DATE_BETWEEN_HALF_OPEN)
    @Nested
    class FindAllByRentalDateBetweenHalOpenTest {

        @Test
        void __() {
            final var maxResults = current().nextInt(128, 256);
            final var rentalDateMinInclusive = minRentalDate;
            final var rentalDateMaxExclusive = maxRentalDate.plusDays(1L).with(LocalTime.MIDNIGHT);
            var rentalDateMinHolder = ofEpochSecond(0L, 0, ZoneOffset.UTC); // 1970-01-01T00:00:00.000Z
            var rentalIdMinExclusiveHolder = 0;
            while (true) {
                final var rentalIdMinExclusive = rentalIdMinExclusiveHolder;
                final var rentalDateMin = rentalDateMinHolder;
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_RENTAL_DATE_BETWEEN_HALF_OPEN, Rental.class)
                                .setParameter(PARAMETER_RENTAL_DATE_MIN_INCLUSIVE, rentalDateMinInclusive)
                                .setParameter(PARAMETER_RENTAL_DATE_MAX_EXCLUSIVE, rentalDateMaxExclusive)
                                .setParameter(PARAMETER_RENTAL_DATE_MIN, rentalDateMin)
                                .setParameter(PARAMETER_RENTAL_ID_MIN_EXCLUSIVE, rentalIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(COMPARING_RENTAL_DATE_RENTAL_ID)
                        .allSatisfy(e -> {
                            // e.rentalDate >= :rentalDateMinInclusive
                            assertThat(e.getRentalDate()).isAfterOrEqualTo(rentalDateMinInclusive);
                            // e.rentalDate < :rentalDateMaxExclusive
                            assertThat(e.getRentalDate()).isBefore(rentalDateMaxExclusive);
                            assertThat(e).satisfiesAnyOf(
                                    // (e.rentalDate = :rentalDateMin AND e.rentalId > :rentalIdMinExclusive)
                                    e2 -> assertThat(e2).satisfies(e3 -> {
                                        assertThat(e3.getRentalDate()).isEqualTo(rentalDateMin);
                                        assertThat(e3.getRentalId()).isGreaterThan(rentalIdMinExclusive);
                                    }),
                                    // OR e.rentalDate > :rentalDateMin
                                    e2 -> assertThat(e2.getRentalDate()).isAfter(rentalDateMin)
                            );
                        });
                if (list.isEmpty()) {
                    break;
                }
                final var last = list.get(list.size() - 1);
                rentalDateMinHolder = last.getRentalDate();
                rentalIdMinExclusiveHolder = last.getRentalId();
            }
        }

        @DisplayName("[2005-07-01 00:00:00 ~ 2005-08-01 00:00:00)")
        @Test
        void _6709_200507() {
            var total = 0;
            final var maxResults = 8192;
            final var rentalDateMinInclusive = YearMonth.of(2005, Month.JULY).atDay(1).atStartOfDay();
            final var rentalDateMaxExclusive = rentalDateMinInclusive.plusMonths(1L);
            var rentalDateMinHolder = ofEpochSecond(0L, 0, ZoneOffset.UTC); // 1970-01-01T00:00:00.000Z
            var rentalIdMinExclusiveHolder = 0;
            while (true) {
                final var rentalDateMin = rentalDateMinHolder;
                final var rentalIdMinExclusive = rentalIdMinExclusiveHolder;
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_RENTAL_DATE_BETWEEN_HALF_OPEN, Rental.class)
                                .setParameter(PARAMETER_RENTAL_DATE_MIN_INCLUSIVE, rentalDateMinInclusive)
                                .setParameter(PARAMETER_RENTAL_DATE_MAX_EXCLUSIVE, rentalDateMaxExclusive)
                                .setParameter(PARAMETER_RENTAL_DATE_MIN, rentalDateMin)
                                .setParameter(PARAMETER_RENTAL_ID_MIN_EXCLUSIVE, rentalIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(COMPARING_RENTAL_DATE_RENTAL_ID)
                        .allSatisfy(e -> {
                            // e.rentalDate BETWEEN :rentalDateMinInclusive AND :rentalDateMaxExclusive
                            assertThat(e.getRentalDate()).isBetween(rentalDateMinInclusive, rentalDateMaxExclusive);
                            assertThat(e).satisfiesAnyOf(
                                    // (e.rentalDate = :rentalDateMin AND e.rentalId > :rentalIdMinExclusive)
                                    e2 -> assertThat(e2).satisfies(e3 -> {
                                        assertThat(e3.getRentalDate()).isEqualTo(rentalDateMin);
                                        assertThat(e3.getRentalId()).isGreaterThan(rentalIdMinExclusive);
                                    }),
                                    // OR e.rentalDate > :rentalDateMin
                                    e2 -> assertThat(e2.getRentalDate()).isAfter(rentalDateMin)
                            );
                        });
                if (list.isEmpty()) {
                    break;
                }
                final var last = list.get(list.size() - 1);
                rentalDateMinHolder = last.getRentalDate();
                rentalIdMinExclusiveHolder = last.getRentalId();
                total += list.size();
            }
            assertThat(total).isEqualTo(6709);
        }

        @DisplayName("[2005-08-01 00:00:00 ~ 2005-09-01 00:00:00)")
        @Test
        void _5686_200508() {
            var total = 0;
            final var maxResults = 8192;
            final var rentalDateMinInclusive = YearMonth.of(2005, Month.AUGUST).atDay(1).atStartOfDay();
            final var rentalDateMaxExclusive = rentalDateMinInclusive.plusMonths(1L);
            var rentalDateMinHolder = ofEpochSecond(0L, 0, ZoneOffset.UTC); // 1970-01-01T00:00:00.000Z
            var rentalIdMinExclusiveHolder = 0;
            while (true) {
                final var rentalDateMin = rentalDateMinHolder;
                final var rentalIdMinExclusive = rentalIdMinExclusiveHolder;
                // TODO: implement!
                break;
            }
//            assertThat(total).isEqualTo(5686);
        }
    }

    private LocalDateTime minRentalDate;

    private LocalDateTime maxRentalDate;
}
