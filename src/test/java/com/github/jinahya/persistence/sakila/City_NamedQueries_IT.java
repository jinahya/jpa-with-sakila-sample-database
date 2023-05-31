package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class City_NamedQueries_IT
        extends __PersistenceIT {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @DisplayName(CityConstants.QUERY_FIND_BY_CITY_ID)
    @Nested
    class FindByCityIdTest {

        @DisplayName("(0)NoResultException")
        @Test
        void _NoResultException_0() {
            final var cityId = 0;
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(CityConstants.QUERY_FIND_BY_CITY_ID, City.class)
                                    .setParameter(City_.cityId.getName(), cityId)
                                    .getSingleResult() // NoResultException
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("(1)!null")
        @Test
        void _NotNull_1() {
            final var cityId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(CityConstants.QUERY_FIND_BY_CITY_ID, City.class)
                            .setParameter(City_.cityId.getName(), cityId)
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(City::getCityId)
                    .isEqualTo(cityId);
        }
    }

    @DisplayName(CityConstants.QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __WithoutMaxResults() {
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(CityConstants.QUERY_FIND_ALL, City.class)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isNotEmpty()
                    .doesNotContainNull();
        }

        @Test
        void __WithMasResults() {
            final var maxResults = current().nextInt(1, 8);
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(CityConstants.QUERY_FIND_ALL, City.class)
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

    @DisplayName(CityConstants.QUERY_FIND_ALL_BY_CITY_ID_GREATER_THAN)
    @Nested
    class FindAllByCityIdGreaterThanTest {

        @DisplayName("0-maxResults")
        @Test
        void __WithoutMaxResults() {
            final var cityIdMinExclusive = 0;
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(CityConstants.QUERY_FIND_ALL_BY_CITY_ID_GREATER_THAN, City.class)
                            .setParameter(CityConstants.QUERY_PARAM_CITY_ID_MIN_EXCLUSIVE, cityIdMinExclusive)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isNotEmpty()
                    .doesNotContainNull()
                    .extracting(City::getCityId)
                    .isSorted()
                    .allSatisfy(v -> {
                        assertThat(v).isGreaterThan(cityIdMinExclusive);
                    });
        }

        @DisplayName("0+maxResults")
        @Test
        void __WithMaxResults() {
            final var cityIdMinExclusive = current().nextInt(0, 16);
            final var maxResults = current().nextInt(1, 8);
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(CityConstants.QUERY_FIND_ALL_BY_CITY_ID_GREATER_THAN, City.class)
                            .setParameter(CityConstants.QUERY_PARAM_CITY_ID_MIN_EXCLUSIVE, cityIdMinExclusive)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .extracting(City::getCityId)
                    .isSorted()
                    .allSatisfy(v -> {
                        assertThat(v).isGreaterThan(cityIdMinExclusive);
                    });
        }

        @DisplayName("pagination")
        @Test
        void __() {
            final var maxResults = current().nextInt(32, 64);
            for (final var i = new AtomicInteger(0); ; ) {
                final var cityIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(CityConstants.QUERY_FIND_ALL_BY_CITY_ID_GREATER_THAN, City.class)
                                .setParameter(CityConstants.QUERY_PARAM_CITY_ID_MIN_EXCLUSIVE, cityIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .extracting(City::getCityId)
                        .isSorted()
                        .allSatisfy(v -> {
                            assertThat(v).isGreaterThan(cityIdMinExclusive);
                        });
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getCityId());
            }
        }
    }

    @DisplayName(CityConstants.QUERY_FIND_ALL_BY_COUNTRY_ID)
    @Nested
    class FindAllByCountryIdTest {

        @DisplayName("(0)empty")
        @Test
        void _Empty_0() {
            final var countryId = 0;
            final var cityIdMinExclusive = 0;
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(
                                    CityConstants.QUERY_FIND_ALL_BY_COUNTRY_ID,
                                    City.class)
                            .setParameter(CityConstants.QUERY_PARAM_COUNTRY_ID, countryId)
                            .setParameter(CityConstants.QUERY_PARAM_CITY_ID_MIN_EXCLUSIVE, cityIdMinExclusive)
                            .getResultList()
            );
            assertThat(list).isEmpty();
        }

        @DisplayName("(1-maxResults)!empty")
        @Test
        void _NotEmpty_1WithoutMaxResults() {
            final var countryId = 1;
            final var cityIdMinExclusive = 0;
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(CityConstants.QUERY_FIND_ALL_BY_COUNTRY_ID, City.class)
                            .setParameter(CityConstants.QUERY_PARAM_COUNTRY_ID, countryId)
                            .setParameter(CityConstants.QUERY_PARAM_CITY_ID_MIN_EXCLUSIVE, cityIdMinExclusive)
                            .getResultList()
            );
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .isSortedAccordingTo(Comparator.comparing(City::getCityId))
                    .extracting(City::getCountryId)
                    .containsOnly(countryId);
        }

        @DisplayName("(1+maxResults)!empty")
        @Test
        void _NotEmpty_1WithMaxResults() {
            final var countryId = 1;
            final var cityIdMinExclusive = 0;
            final var maxResults = current().nextInt(1, 8);
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(CityConstants.QUERY_FIND_ALL_BY_COUNTRY_ID, City.class)
                            .setParameter(CityConstants.QUERY_PARAM_COUNTRY_ID, countryId)
                            .setParameter(CityConstants.QUERY_PARAM_CITY_ID_MIN_EXCLUSIVE, cityIdMinExclusive)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .isSortedAccordingTo(Comparator.comparing(City::getCityId))
                    .extracting(City::getCountryId)
                    .containsOnly(countryId);
        }

        @DisplayName("pagination for 44(India)")
        @Test
        void __44() {
            final var countryId = 44; // India
            final var maxResults = current().nextInt(8, 16);
            for (final var i = new AtomicInteger(0); ; ) {
                final var cityIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(CityConstants.QUERY_FIND_ALL_BY_COUNTRY_ID, City.class)
                                .setParameter(CityConstants.QUERY_PARAM_COUNTRY_ID, countryId)
                                .setParameter(CityConstants.QUERY_PARAM_CITY_ID_MIN_EXCLUSIVE, cityIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(Comparator.comparing(City::getCityId))
                        .extracting(City::getCountryId)
                        .allMatch(v -> v == countryId);
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getCityId());
            }
        }
    }

    @DisplayName(CityConstants.QUERY_FIND_ALL_BY_COUNTRY)
    @Nested
    class FindAllByCountryIdGreaterThanTest {

        @DisplayName("(Country(44))+")
        @Test
        void __44WithMaxResults() {
            final var country = Country.ofCountryId(44); // India
            final var maxResults = current().nextInt(8, 16);
            for (final var i = new AtomicInteger(0); ; ) {
                final var cityIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(CityConstants.QUERY_FIND_ALL_BY_COUNTRY, City.class)
                                .setParameter(CityConstants.QUERY_PARAM_COUNTRY, country)
                                .setParameter(CityConstants.QUERY_PARAM_CITY_ID_MIN_EXCLUSIVE, cityIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(Comparator.comparing(City::getCityId))
                        .extracting(City::getCountryId)
                        .allMatch(v -> Objects.equals(v, country.getCountryId()));
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getCityId());
            }
        }
    }
}
