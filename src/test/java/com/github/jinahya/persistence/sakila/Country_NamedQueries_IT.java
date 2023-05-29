package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * A class for testing named queries defined on {@link Country} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class Country_NamedQueries_IT
        extends _BaseEntityIT<Country, Integer> {

    Country_NamedQueries_IT() {
        super(Country.class, Integer.class);
    }

    @DisplayName(CountryConstants.QUERY_FIND_BY_COUNTRY_ID)
    @Nested
    class FindByCountryIdTest {

        @DisplayName("findByCountryId(0) throws NoResultException")
        @Test
        void _NoResultException_0() {
            // GIVEN
            final var countryId = 0;
            // WHEN / THEN
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(CountryConstants.QUERY_FIND_BY_COUNTRY_ID, Country.class)
                                    .setParameter(Country_.countryId.getName(), countryId)
                                    .getSingleResult() // NoResultException
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("findByCountryId(1)")
        @Test
        void __1() {
            // GIVEN
            final var countryId = 1;
            // WHEN
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(CountryConstants.QUERY_FIND_BY_COUNTRY_ID, Country.class)
                            .setParameter(Country_.countryId.getName(), countryId)
                            .getSingleResult() // NoResultException
            );
            // THEN
            assertThat(found)
                    .isNotNull()
                    .extracting(Country::getCountryId)
                    .isEqualTo(countryId);
        }

        @DisplayName("findByCountryId(86)")
        @Test
        void __86() {
            // GIVEN
            final var countryId = 86;
            // TODO: implement!
            // WHEN
            // THEN
        }
    }

    @DisplayName(CountryConstants.QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(CountryConstants.QUERY_FIND_ALL, Country.class)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isNotEmpty();
        }

        @Test
        void __WithMaxResults() {
            // TODO: fix typo, masResults -> maxResults!
            final var masResults = ThreadLocalRandom.current().nextInt(8, 16);
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(CountryConstants.QUERY_FIND_ALL, Country.class)
                            .setMaxResults(masResults)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isNotEmpty()
                    .hasSizeLessThanOrEqualTo(masResults);
        }
    }

    @DisplayName(CountryConstants.QUERY_FIND_ALL_BY_COUNTRY_ID_GREATER_THAN)
    @Nested
    class FindAllIdGreaterThanTest {

        @Test
        void __() {
            final var countryIdMinExclusive = 0;
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(CountryConstants.QUERY_FIND_ALL_BY_COUNTRY_ID_GREATER_THAN, Country.class)
                            .setParameter(CountryConstants.QUERY_PARAM_COUNTRY_ID_MIN_EXCLUSIVE, countryIdMinExclusive)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isNotEmpty()
                    .extracting(Country::getCountryId)
                    .allMatch(v -> v.compareTo(countryIdMinExclusive) > 0);
        }

        @Test
        void __WithMaxResults() {
            final var countryIdMinExclusive = ThreadLocalRandom.current().nextInt(0, 16);
            final var maxResults = ThreadLocalRandom.current().nextInt(8, 16);
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(CountryConstants.QUERY_FIND_ALL_BY_COUNTRY_ID_GREATER_THAN, Country.class)
                            .setParameter(CountryConstants.QUERY_PARAM_COUNTRY_ID_MIN_EXCLUSIVE, countryIdMinExclusive)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isNotEmpty()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .extracting(Country::getCountryId)
                    .allMatch(v -> v.compareTo(countryIdMinExclusive) > 0);
        }
    }

    @DisplayName(CountryConstants.QUERY_FIND_ALL_BY_COUNTRY)
    @Nested
    class FindByCountryTest {

        @DisplayName("findAllByCountry(Wakanda) -> Empty")
        @Test
        void __() {
            // GIVEN
            final var country = "Wakanda";
            // WHEN
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(CountryConstants.QUERY_FIND_ALL_BY_COUNTRY, Country.class)
                            .setParameter(Country_.country.getName(), country)
                            .getResultList()
            );
            // THEN
            assertThat(list).isEmpty();
        }

        @DisplayName("findAllByCountry('United States') -> Not Empty")
        @Test
        void __UnitedStates() {
            // GIVEN
            final var country = "United States";
            // WHEN
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(CountryConstants.QUERY_FIND_ALL_BY_COUNTRY, Country.class)
                            .setParameter(Country_.country.getName(), country)
                            .getResultList()
            );
            // THEN
            assertThat(list)
                    .isNotEmpty()
                    .extracting(Country::getCountry)
                    .containsOnly(country);
        }

        @DisplayName("findAllByCountry('South Korea') -> Not Empty")
        @Test
        void __SouthKorea() {
            final var country = "South Korea";
            // TODO: implement!
            final var list = applyEntityManager(em -> null);
        }
    }
}
