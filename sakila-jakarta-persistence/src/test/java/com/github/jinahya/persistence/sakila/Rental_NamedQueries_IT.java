package com.github.jinahya.persistence.sakila;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static com.github.jinahya.persistence.sakila.RentalConstants.PARAMETER_RENTAL_DATE_MAX_EXCLUSIVE;
import static com.github.jinahya.persistence.sakila.RentalConstants.PARAMETER_RENTAL_DATE_MAX_INCLUSIVE;
import static com.github.jinahya.persistence.sakila.RentalConstants.PARAMETER_RENTAL_DATE_MIN;
import static com.github.jinahya.persistence.sakila.RentalConstants.PARAMETER_RENTAL_DATE_MIN_INCLUSIVE;
import static com.github.jinahya.persistence.sakila.RentalConstants.PARAMETER_RENTAL_ID_MIN_EXCLUSIVE;
import static com.github.jinahya.persistence.sakila.RentalConstants.QUERY_FIND_ALL;
import static com.github.jinahya.persistence.sakila.RentalConstants.QUERY_FIND_ALL_BY_RENTAL_DATE_BETWEEN;
import static com.github.jinahya.persistence.sakila.RentalConstants.QUERY_FIND_ALL_BY_RENTAL_DATE_BETWEEN_HALF_OPEN;
import static com.github.jinahya.persistence.sakila.RentalConstants.QUERY_FIND_BY_RENTAL_ID;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Comparator.comparing;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // @BeforeAll + !static
class Rental_NamedQueries_IT
        extends __PersistenceIT {

    private static final Logger log = getLogger(lookup().lookupClass());

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

        // TODO: add testcases!
    }

    @DisplayName(QUERY_FIND_ALL)
    @Nested
    class FindAllRentalIdTest {

        // TODO: add testcases!
    }

    @DisplayName(QUERY_FIND_ALL_BY_RENTAL_DATE_BETWEEN)
    @Nested
    class FindAllByRentalDateBetweenTest {

        @Test
        void __() {
            final var maxResults = current().nextInt(128, 256);
            final var rentalDateMinInclusive = minRentalDate;
            final var rentalDateMaxInclusive = maxRentalDate;
            var rentalDateMinHolder = LocalDateTime.ofEpochSecond(0L, 0, ZoneOffset.UTC); // 1970-01-01T00:00:00.000Z
            var rentalIdMinExclusiveHolder = 0;
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
    }

    @DisplayName(QUERY_FIND_ALL_BY_RENTAL_DATE_BETWEEN_HALF_OPEN)
    @Nested
    class FindAllByRentalDateHalOpenTest {

        @Test
        void __() {
            final var maxResults = current().nextInt(128, 256);
            final var rentalDateMinInclusive = minRentalDate;
            final var rentalDateMaxExclusive = maxRentalDate.plusDays(1L).with(LocalTime.MIDNIGHT);
            var rentalDateMinHolder = LocalDateTime.ofEpochSecond(0L, 0, ZoneOffset.UTC); // 1970-01-01T00:00:00.000Z
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
                        .isSortedAccordingTo(comparing(Rental::getRentalDate).thenComparing(Rental::getRentalId))
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
    }

    private LocalDateTime minRentalDate;

    private LocalDateTime maxRentalDate;
}
