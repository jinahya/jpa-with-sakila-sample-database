package com.github.jinahya.persistence;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ValidateOnExecution(type = {ExecutableType.ALL})
class CountryService_IT
        extends _BaseEntityServiceIT<CountryService, Country, Integer> {

    CountryService_IT() {
        super(CountryService.class);
    }

    @Nested
    class LocateByNameTest {

        @Test
        void __CountryIsBlank() {
            assertThatThrownBy(() -> applyServiceInstance(s -> s.locateByByCountry(null)))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void __Afghanistan() {
            final var country = "Afghanistan";
            final var located = applyServiceInstance(s -> s.locateByByCountry(country));
            assertThat(located).isNotNull().extracting(Country::getCountry).isEqualTo(country);
        }

        @Test
        void __Wakanda() {
            final var country = "Wakanda";
            final var located = applyServiceInstance(s -> s.locateByByCountry(country));
            assertThat(located)
                    .isNotNull()
                    .extracting(Country::getCountry)
                    .isEqualTo(country);
        }
    }
}
