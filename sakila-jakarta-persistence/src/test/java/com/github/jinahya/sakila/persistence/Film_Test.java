package com.github.jinahya.sakila.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Period;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class Film_Test
        extends _BaseEntityTest<Film, Integer> {

    Film_Test() {
        super(Film.class, Integer.class);
    }

    @Nested
    class RentalDurationAsPeriodTest {

        @Test
        void _3_() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            final var rentalDurationAsPeriod = instance.getRentalDurationAsPeriod();
            // THEN
            assertThat(rentalDurationAsPeriod)
                    .isEqualTo(Period.ofDays(Film.COLUMN_VALUE_RENTAL_DURATION_3));
            verify(instance, times(1)).getRentalDuration();
        }

        @Test
        void __() {
            // GIVEN
            final var instance = newEntitySpy();
            final var rentalDuration = current().nextInt(365);
            // WHEN
            when(instance.getRentalDuration()).thenReturn(rentalDuration);
            // THEN
            final var rentalDurationAsPeriod = instance.getRentalDurationAsPeriod();
            assertThat(rentalDurationAsPeriod)
                    .isEqualTo(Period.ofDays(rentalDuration));
        }

        @DisplayName("setRentalDurationAsPeriod(null) -> setRentalDuration(null)")
        @Test
        void setRentalDurationAsPeriod_Null_Null() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setRentalDurationAsPeriod(null);
            // THEN
            verify(instance, times(1)).setRentalDuration(null);
        }

        @DisplayName("setRentalDurationAsPeriod(!null) -> setRentalDuration(!null)")
        @Test
        void setRentalDurationAsPeriod_NonNull_NonNull() {
            // GIVEN
            final var instance = newEntitySpy();
            final var days = current().nextInt(2);
            // WHEN
            instance.setRentalDurationAsPeriod(Period.ofDays(days));
            // THEN
            verify(instance, times(1)).setRentalDuration(days);
        }
    }

    @Nested
    class LengthAsDurationTest {

        @Test
        void _Null_() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            final var lengthAsDuration = instance.getLengthAsDuration();
            // THEN
            assertThat(lengthAsDuration).isNull();
        }

        @Test
        void __() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            final var length = current().nextInt(420);
            when(instance.getLength()).thenReturn(length);
            // THEN
            final var lengthAsDuration = instance.getLengthAsDuration();
            assertThat(lengthAsDuration).isEqualTo(Duration.ofMinutes(length));
        }

        @DisplayName("setLengthAsDuration(null) -> setLength(null)")
        @Test
        void setLengthAsDuration_Null_Null() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setLengthAsDuration(null);
            // THEN
            verify(instance, times(1)).setLength(null);
        }

        @DisplayName("setLengthAsDuration(!null) -> setLength(!null)")
        @Test
        void setLengthAsDuration_NonNull_NonNull() {
            // GIVEN
            final var instance = newEntitySpy();
            final var minutes = current().nextInt(720);
            // WHEN
            instance.setLengthAsDuration(Duration.ofMinutes(minutes));
            // THEN
            verify(instance, times(1)).setLength(minutes);
        }
    }
}
