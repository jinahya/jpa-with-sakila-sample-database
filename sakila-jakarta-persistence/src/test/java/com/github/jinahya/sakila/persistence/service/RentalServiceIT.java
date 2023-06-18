package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence.Rental;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.concurrent.atomic.LongAdder;

import static java.time.Month.AUGUST;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JULY;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

class RentalServiceIT
        extends _BaseEntityServiceIT<RentalService, Rental, Integer> {

    RentalServiceIT() {
        super(RentalService.class, Rental.class, Integer.class);
    }

    @DisplayName("countByRentalDateIn(LocalDate)")
    @Nested
    class CountByRentalDateInDateTest {

        @DisplayName("(2006-02-14)182")
        @Test
        void _182_20060214() {
            final var dateOfRentalDate = LocalDate.of(2006, FEBRUARY, 14);
            final var count = applyServiceInstance(s -> s.countByRentalDateIn(dateOfRentalDate));
            assertThat(count).isEqualTo(182);
        }

        @DisplayName("(2005-07-31)679")
        @Test
        void _679_20050731() {
            final var dateOfRentalDate = LocalDate.of(2005, JULY, 31);
            // TODO: implement!
        }
    }

    @DisplayName("selectAllByRentalDateIn(LocalDate)")
    @Nested
    class SelectAllByRentalDateInDateTest {

        @DisplayName("(2006-02-14)182")
        @Test
        void _182_20060214() {
            final var count = new LongAdder();
            final var dateOfRentalDate = LocalDate.of(2006, FEBRUARY, 14);
            final var maxResults = 128;
            for (var i = 0; ; ) {
                final var rentalIdMinExclusive = i;
                final var list = applyServiceInstance(
                        s -> s.findAllByRentalDateIn(dateOfRentalDate, rentalIdMinExclusive, maxResults)
                );
                Assertions.assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .allSatisfy(e -> {
                            Assertions.assertThat(e.getRentalId()).isGreaterThan(rentalIdMinExclusive);
                            Assertions.assertThat(e.getRentalDate().toLocalDate()).isEqualTo(dateOfRentalDate);
                        })
                        .isSortedAccordingTo(comparing(Rental::getRentalId));
                if (list.isEmpty()) {
                    break;
                }
                i = list.get(list.size() - 1).getRentalId();
                count.add(list.size());
            }
            assertThat(count.intValue()).isEqualTo(182);
        }

        @DisplayName("(2005-07-31)679")
        @Test
        void _679_20050731() {
            final var count = new LongAdder();
            final var dateOfRentalDate = LocalDate.of(2005, JULY, 31);
            final var maxResults = 128;
            for (var i = 0; ; ) {
                final var rentalIdMinExclusive = i;
                break;
                // TODO: implement!
            }
//            assertThat(count.intValue()).isEqualTo(679);
        }
    }

    @DisplayName("countByRentalDateIn(YearMonth)")
    @Nested
    class CountByRentalDateInMonthTest {

        @DisplayName("(2005-07)6709")
        @Test
        void _6709_200507() {
            final var monthOfRentalDate = YearMonth.of(2005, JULY);
            final var count = applyServiceInstance(s -> s.countByRentalDateIn(monthOfRentalDate));
            assertThat(count).isEqualTo(6709);
        }

        @DisplayName("(2005-08)5686")
        @Test
        void _5686_20050731() {
            final var monthOfRentalDate = YearMonth.of(2005, AUGUST);
            // TODO: implement!
        }
    }

    @DisplayName("selectAllByRentalDateIn(YearMonth)")
    @Nested
    class SelectAllByRentalDateInMonthTest {

        @DisplayName("(2005-07)6709")
        @Test
        void _6709_200507() {
            final var total = new LongAdder();
            final var monthOfRentalDate = YearMonth.of(2005, JULY);
            final var maxResults = 1024;
            for (var i = 0; ; ) {
                final var rentalIdMinExclusive = i;
                final var list = applyServiceInstance(
                        s -> s.findAllByRentalDateIn(monthOfRentalDate, rentalIdMinExclusive, maxResults)
                );
                Assertions.assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .allSatisfy(e -> {
                            Assertions.assertThat(e.getRentalId()).isGreaterThan(rentalIdMinExclusive);
                            assertThat(YearMonth.from(e.getRentalDate())).isEqualTo(monthOfRentalDate);
                        })
                        .isSortedAccordingTo(comparing(Rental::getRentalId));
                if (list.isEmpty()) {
                    break;
                }
                i = list.get(list.size() - 1).getRentalId();
                total.add(list.size());
            }
            assertThat(total.intValue()).isEqualTo(6709);
        }

        @DisplayName("(2005-08)5686")
        @Test
        void _5686_200508() {
            final var total = new LongAdder();
            final var monthOfRentalDate = YearMonth.of(2005, AUGUST);
            final var maxResults = 1024;
            for (var i = 0; ; ) {
                final var rentalIdMinExclusive = i;
                break;
                // TODO: implement!
            }
//            assertThat(total.intValue()).isEqualTo(5686);
        }
    }

    @DisplayName("countByRentalDateIn(Year)")
    @Nested
    class CountByRentalDateInYearTest {

        @DisplayName("(2005)15862")
        @Test
        void _15862_2005() {
            final var yearOfRentalDate = Year.of(2005);
            final var count = applyServiceInstance(s -> s.countByRentalDateIn(yearOfRentalDate));
            assertThat(count).isEqualTo(15862);
        }

        @DisplayName("(2005)182")
        @Test
        void _182_2006() {
            final var yearOfRentalDate = Year.of(2006);
            // TODO: implement!
        }
    }

    @DisplayName("selectAllByRentalDateIn(Year)")
    @Nested
    class SelectAllByRentalDateInYearTest {

        @DisplayName("(2005)15862")
        @Test
        void _15862_2005() {
            final var total = new LongAdder();
            final var yearOfRentalDate = Year.of(2005);
            final var maxResults = 8192;
            for (var i = 0; ; ) {
                final var rentalIdMinExclusive = i;
                final var list = applyServiceInstance(
                        s -> s.findAllByRentalDateIn(yearOfRentalDate, rentalIdMinExclusive, maxResults)
                );
                Assertions.assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .allSatisfy(e -> {
                            Assertions.assertThat(e.getRentalId()).isGreaterThan(rentalIdMinExclusive);
                            assertThat(Year.from(e.getRentalDate())).isEqualTo(yearOfRentalDate);
                        })
                        .isSortedAccordingTo(comparing(Rental::getRentalId));
                if (list.isEmpty()) {
                    break;
                }
                i = list.get(list.size() - 1).getRentalId();
                total.add(list.size());
            }
            assertThat(total.intValue()).isEqualTo(15862);
        }

        @DisplayName("(2006)182")
        @Test
        void _182_2006() {
            final var total = new LongAdder();
            final var yearOfRentalDate = Year.of(2006);
            final var maxResults = 8192;
            for (var i = 0; ; ) {
                final var rentalIdMinExclusive = i;
                break;
                // TODO: implement!
            }
//            assertThat(total.intValue()).isEqualTo(182);
        }
    }
}
