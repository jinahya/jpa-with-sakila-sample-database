package com.github.jinahya.persistence;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ValidateOnExecution(type = {ExecutableType.ALL})
class CountryService_IT
        extends _BaseEntityServiceIT<CountryService, Country, Integer> {

    CountryService_IT() {
        super(CountryService.class, Country.class, Integer.class);
    }

    @DisplayName("findByCountryId(countryId)")
    @Nested
    class FindByCountryIdTest {

        @DisplayName("(0)ConstraintViolationException")
        @Test
        void _ConstraintViolationException_0() {
            assertThatThrownBy(() -> applyServiceInstance(s -> s.findByCountryId(0)))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @DisplayName("(1)!empty")
        @Test
        void _Empty_1() {
            final var countryId = 1;
            final var found = applyServiceInstance(s -> s.findByCountryId(countryId));
            assertThat(found).hasValueSatisfying(v -> {
                assertThat(v.getCountryId()).isEqualTo(countryId);
            });
        }
    }

    @DisplayName("findAll(maxResults)")
    @Nested
    class FindAllTest {

        @DisplayName("(null)")
        @Test
        void __WithNullMaxResults() {
            final var list = applyServiceInstance(s -> s.findAll(null));
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull();
        }

        @DisplayName("(!null)")
        @Test
        void __WithNonNullMaxResults() {
            final var maxResults = current().nextInt(8, 16);
            final var list = applyServiceInstance(s -> s.findAll(maxResults));
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @DisplayName("findAllByCountryIdGreaterThan(countryIdMinExclusive, maxResults)")
    @Nested
    class FindAllByCountryIdGreaterThanTest {

        @Test
        void __WithNullMaxResults() {
            final var countryIdMinExclusive = 0;
            final var list = applyServiceInstance(
                    s -> s.findAllByCountryIdGreaterThan(countryIdMinExclusive, null)
            );
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .extracting(Country::getCountryId)
                    .doesNotContainNull()
                    .allMatch(v -> v > countryIdMinExclusive);
        }

        @Test
        void __WithNonNullMaxResults() {
            final var countryIdMinExclusive = current().nextInt(0, 8);
            final var maxResults = current().nextInt(8, 16);
            final var list = applyServiceInstance(
                    s -> s.findAllByCountryIdGreaterThan(countryIdMinExclusive, maxResults)
            );
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .extracting(Country::getCountryId)
                    .doesNotContainNull()
                    .allMatch(v -> v > countryIdMinExclusive);
        }
    }

    @DisplayName("findAllByCountry(country, maxResults)")
    @Nested
    class FindsAllByCountryTest {

        @DisplayName("('Wakanda', )empty")
        @Test
        void _Empty_Wakanda() {
            final var country = "Wakanda";
            final var maxResults = current().nextBoolean() ? null : current().nextInt(1, 8);
            final var list = applyServiceInstance(s -> s.findAllByCountry(country, maxResults));
            assertThat(list)
                    .isEmpty();
        }

        @DisplayName("('United States', )!empty")
        @Test
        void _NotEmpty_UnitedStates() {
            final var country = "United States";
            final var maxResults = current().nextBoolean() ? null : current().nextInt(1, 8);
            final var list = applyServiceInstance(s -> s.findAllByCountry(country, maxResults));
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .extracting(Country::getCountry)
                    .containsOnly(country);
        }

        @DisplayName("('South Korea', )!empty")
        @Test
        void _NotEmpty_SouthKorea() {
            final var country = "South Korea";
            final var maxResults = current().nextBoolean() ? null : current().nextInt(1, 8);
            // TODO: implement!
        }
    }

    @DisplayName("locateByCountry(country)")
    @Nested
    class LocateByNameTest {

        @DisplayName("('Wakanda')!null")
        @Test
        void __Wakanda() {
            final var country = "Wakanda";
            final var located = applyServiceInstance(s -> s.locateByByCountry(country));
            assertThat(located)
                    .isNotNull()
                    .extracting(Country::getCountry)
                    .isEqualTo(country);
        }

        @DisplayName("('Afghanistan')!null")
        @Test
        void __Afghanistan() {
            final var country = "Afghanistan";
            final var located = applyServiceInstance(s -> s.locateByByCountry(country));
            assertThat(located)
                    .isNotNull()
                    .extracting(Country::getCountry)
                    .isEqualTo(country);
        }
    }
}
