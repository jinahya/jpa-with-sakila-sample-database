package com.github.jinahya.sakila.persistence;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.jinahya.sakila.persistence.CityConstants.GRAPH_COUNTRY;
import static com.github.jinahya.sakila.persistence.CityConstants.PARAM_CITY_ID_MIN_EXCLUSIVE;
import static com.github.jinahya.sakila.persistence.CityConstants.PARAM_COUNTRY_ID;
import static com.github.jinahya.sakila.persistence.CityConstants.QUERY_FIND_ALL;
import static com.github.jinahya.sakila.persistence.CityConstants.QUERY_FIND_ALL_BY_COUNTRY_ID;
import static com.github.jinahya.sakila.persistence.CityConstants.QUERY_FIND_BY_CITY_ID;
import static com.github.jinahya.sakila.persistence._PersistenceConstants.PERSISTENCE_FETCHGRAPH;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Comparator.comparing;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Tests named queries defined on {@link City} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class City_NamedQueries_IT
        extends __PersistenceIT {

    private static final Logger log = getLogger(lookup().lookupClass());

    @DisplayName(QUERY_FIND_BY_CITY_ID)
    @Nested
    class FindByCityIdTest {

        @DisplayName("(0)NoResultException")
        @Test
        void _NoResultException_0() {
            final var cityId = 0;
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(QUERY_FIND_BY_CITY_ID, City.class)
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
                    em -> em.createNamedQuery(QUERY_FIND_BY_CITY_ID, City.class)
                            .setParameter(City_.cityId.getName(), cityId)
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(City::getCityId)
                    .isEqualTo(cityId);
        }

        @DisplayName("(1fetch)!null")
        @Test
        void _NotNull_1FetchCountry() {
            final var cityId = 1;
            final var found = applyEntityManager(em -> {
                final var query = em.createNamedQuery(QUERY_FIND_BY_CITY_ID, City.class)
                        .setParameter(City_.cityId.getName(), cityId);
                if (current().nextBoolean()) {
                    final var graph = em.getEntityGraph(GRAPH_COUNTRY);
                    query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                } else {
                    final var graph = em.createEntityGraph(City.class);
                    graph.addAttributeNodes(City_.country.getName());
                    query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                }
                return query.getSingleResult();
            });
            assertThat(found)
                    .isNotNull()
                    .satisfies(city -> {
                        assertThat(city.getCityId()).isEqualTo(cityId);
                        assertThat(city.getCountry()).satisfies(country -> {
                            assertThat(city.getCountry().getCountry()).isNotNull();
                        });
                    });
        }
    }

    @DisplayName(QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @DisplayName("first page")
        @Test
        void __first() {
            final var maxResults = current().nextInt(1, 8);
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_ALL, City.class)
                            .setParameter(PARAM_CITY_ID_MIN_EXCLUSIVE, 0)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .isSortedAccordingTo(comparing(City::getCityId));
        }

        @DisplayName("all pages")
        @Test
        void __all() {
            final var maxResults = current().nextInt(1, 8);
            for (final var i = new AtomicInteger(0); ; ) {
                final var cityIdMinExclusive = i.get();
                // TODO: implement!
                break;
            }
        }
    }

    @DisplayName(QUERY_FIND_ALL_BY_COUNTRY_ID)
    @Nested
    class FindAllByCountryIdTest {

        @DisplayName("(0)empty")
        @Test
        void _Empty_0() {
            final var countryId = 0;
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_ALL_BY_COUNTRY_ID, City.class)
                            .setParameter(PARAM_COUNTRY_ID, countryId)
                            .setParameter(PARAM_CITY_ID_MIN_EXCLUSIVE, 0)
                            .getResultList()
            );
            assertThat(list).isEmpty();
        }

        @DisplayName("(1)!empty")
        @Test
        void _NotEmpty_1() {
            final var countryId = 1;
            final var cityIdMinExclusive = 0;
            final var maxResults = current().nextInt(8, 16);
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_ALL_BY_COUNTRY_ID, City.class)
                            .setParameter(PARAM_COUNTRY_ID, countryId)
                            .setParameter(PARAM_CITY_ID_MIN_EXCLUSIVE, cityIdMinExclusive)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .isSortedAccordingTo(comparing(City::getCityId))
                    .extracting(City::getCountryId)
                    .containsOnly(countryId);
        }

        @DisplayName("(44; India) first page")
        @Test
        void __44First() {
            final var countryId = 44; // India
            final var maxResults = current().nextInt(8, 16);
            for (final var i = new AtomicInteger(0); ; ) {
                final var cityIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_COUNTRY_ID, City.class)
                                .setParameter(PARAM_COUNTRY_ID, countryId)
                                .setParameter(PARAM_CITY_ID_MIN_EXCLUSIVE, cityIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(City::getCityId))
                        .extracting(City::getCountryId)
                        .allMatch(v -> v == countryId);
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getCityId());
            }
        }

        @DisplayName("(44; India) first page fetching country")
        @Test
        void __44FirstFetchCountry() {
            final var countryId = 44; // India
            final var maxResults = current().nextInt(8, 16);
            final var list = applyEntityManager(em -> {
                final var query = em.createNamedQuery(QUERY_FIND_ALL_BY_COUNTRY_ID, City.class)
                        .setParameter(PARAM_COUNTRY_ID, countryId)
                        .setParameter(PARAM_CITY_ID_MIN_EXCLUSIVE, 0)
                        .setMaxResults(maxResults);
                if (current().nextBoolean()) {
                    final var graph = em.getEntityGraph(GRAPH_COUNTRY);
                    query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                } else {
                    final var graph = em.createEntityGraph(City.class);
                    graph.addAttributeNodes(City_.country.getName());
                    query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                }
                return query.getResultList();
            });
            assertThat(list)
                    .isNotNull()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .isSortedAccordingTo(comparing(City::getCityId))
                    .allSatisfy(city -> {
                        assertThat(city.getCountryId()).isEqualTo(countryId);
                    })
                    .extracting(City::getCountry)
                    .doesNotContainNull()
                    .allSatisfy(country -> {
                        assertThat(country.getCountryId()).isEqualTo(countryId);
                    })
                    .extracting(Country::getCountry)
                    .doesNotContainNull();
        }

        @DisplayName("(44; India) all pages without fetching country")
        @Test
        void __44All() {
            final var countryId = 44; // India
            final var maxResults = current().nextInt(8, 16);
            for (final var i = new AtomicInteger(0); ; ) {
                final var cityIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_COUNTRY_ID, City.class)
                                .setParameter(PARAM_COUNTRY_ID, countryId)
                                .setParameter(PARAM_CITY_ID_MIN_EXCLUSIVE, cityIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(City::getCityId))
                        .extracting(City::getCountryId)
                        .allMatch(v -> v == countryId);
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getCityId());
            }
        }

        @DisplayName("(44; India) all pages with fetching country")
        @Test
        void __44AllFetchCountry() {
            final var countryId = 44; // India
            final var maxResults = current().nextInt(8, 16);
            for (final var i = new AtomicInteger(0); ; ) {
                final var cityIdMinExclusive = i.get();
                final var list = applyEntityManager(em -> {
                    final var query = em.createNamedQuery(QUERY_FIND_ALL_BY_COUNTRY_ID, City.class)
                            .setParameter(PARAM_COUNTRY_ID, countryId)
                            .setParameter(PARAM_CITY_ID_MIN_EXCLUSIVE, cityIdMinExclusive)
                            .setMaxResults(maxResults);
                    if (current().nextBoolean()) {
                        final var graph = em.getEntityGraph(GRAPH_COUNTRY);
                        query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                    } else {
                        final var graph = em.createEntityGraph(City.class);
                        graph.addAttributeNodes(City_.country.getName());
                        query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                    }
                    return query.getResultList();
                });
                assertThat(list)
                        .isNotNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(City::getCityId))
                        .allSatisfy(city -> {
                            assertThat(city.getCountryId()).isEqualTo(countryId);
                        })
                        .extracting(City::getCountry)
                        .doesNotContainNull()
                        .allSatisfy(country -> {
                            assertThat(country.getCountryId()).isEqualTo(countryId);
                        })
                        .extracting(Country::getCountry)
                        .doesNotContainNull();
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getCityId());
            }
        }
    }

    @DisplayName(CityConstants.QUERY_FIND_ALL_BY_COUNTRY)
    @Nested
    class FindAllByCountryTest {

        // TODO: add testcases!
    }
}
