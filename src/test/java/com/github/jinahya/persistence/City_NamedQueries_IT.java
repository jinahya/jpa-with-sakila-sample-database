package com.github.jinahya.persistence;

import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class City_NamedQueries_IT
        extends __BaseEntityIT<City, Integer> {

    City_NamedQueries_IT() {
        super(City.class, Integer.class);
    }

    @DisplayName("City_findAll")
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var list = applyEntityManager(
                    em -> em.createNamedQuery("City_findAll", City.class)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isNotEmpty()
                    .doesNotContainNull();
        }

        @Test
        void __WithMasResults() {
            final int maxResults = ThreadLocalRandom.current().nextInt(1, 8);
            final var list = applyEntityManager(
                    em -> em.createNamedQuery("City_findAll", City.class)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @DisplayName("City_findByCityId")
    @Nested
    class FindByCityIdTest {

        @Test
        void __0() {
            final int cityId = 0;
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery("City_findByCityId", City.class)
                                    .setParameter("cityId", cityId)
                                    .getSingleResult() // NoResultException
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @Test
        void __1() {
            final int cityId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery("City_findByCityId", City.class)
                            .setParameter("cityId", cityId)
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(City::getCityId)
                    .isEqualTo(cityId);
        }
    }

    @DisplayName("City_findAllByCountryId")
    @Nested
    class FindAllByCountryIdTest {

        @Test
        void _Empty_0() {
            final int countryId = 0;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery("City_findAllByCountryId", City.class)
                            .setParameter("countryId", countryId)
                            .getResultList()
            );
            assertThat(found).isEmpty();
        }

        @Test
        void _NotEmpty_1() {
            final int countryId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery("City_findAllByCountryId", City.class)
                            .setParameter("countryId", countryId)
                            .getResultList()
            );
            assertThat(found)
                    .isNotEmpty()
                    .extracting(City::getCountryId)
                    .containsOnly(countryId);
        }

        @Test
        void _NotEmpty_1WithMaxResults() {
            final int countryId = 1;
            final int maxResults = ThreadLocalRandom.current().nextInt(1, 8);
            final var found = applyEntityManager(
                    em -> em.createNamedQuery("City_findAllByCountryId", City.class)
                            .setParameter("countryId", countryId)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(found)
                    .isNotEmpty()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .extracting(City::getCountryId)
                    .containsOnly(countryId);
        }
    }

    @DisplayName("City_findAllByCountry")
    @Nested
    class FindAllByCountryTest {

        @Test
        void _Empty_0() {
            final var country = Country.of(0);
            final var found = applyEntityManager(
                    em -> em.createNamedQuery("City_findAllByCountry", City.class)
                            .setParameter("country", country)
                            .getResultList()
            );
            assertThat(found).isEmpty();
        }

        @Test
        void _NotEmpty_1() {
            final var country = Country.of(1);
            final var found = applyEntityManager(
                    em -> em.createNamedQuery("City_findAllByCountry", City.class)
                            .setParameter("country", country)
                            .getResultList()
            );
            assertThat(found)
                    .isNotEmpty()
                    .extracting(City::getCountryId)
                    .containsOnly(country.getCountryId());
        }

        @Test
        void _NotEmpty_1WithMaxResults() {
            final var country = Country.of(1);
            final int maxResults = ThreadLocalRandom.current().nextInt(1, 8);
            final var found = applyEntityManager(
                    em -> em.createNamedQuery("City_findAllByCountry", City.class)
                            .setParameter("country", country)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(found)
                    .isNotEmpty()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .extracting(City::getCountryId)
                    .containsOnly(country.getCountryId());
        }
    }
}
