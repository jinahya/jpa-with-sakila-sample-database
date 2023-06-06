package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.Country;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;

@ValidateOnExecution(type = {ExecutableType.ALL})
class CountryService_IT
        extends _BaseEntityServiceIT<CountryService, Country, Integer> {

    CountryService_IT() {
        super(CountryService.class, Country.class, Integer.class);
    }

    @DisplayName("findByCountryId")
    @Nested
    class FindByCountryIdTest {

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

    @DisplayName("findAll")
    @Nested
    class FindAllTest {

        @DisplayName("(!null)")
        @Test
        void __WithNonNullMaxResults() {
            final var maxResults = current().nextInt(8, 16);
            for (final var i = new AtomicInteger(0); ; ) {
                final var countryIdMinExclusive = i.get();
                final var list = applyServiceInstance(s -> s.findAll(countryIdMinExclusive, maxResults));
                assertThat(list)
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .extracting(Country::getCountryId)
                        .allMatch(v -> v > countryIdMinExclusive)
                        .isSorted();
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getCountryId());
            }
        }
    }

    @DisplayName("locateByCountry(country)")
    @Nested
    class LocateByNameTest {

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
    }
}
