package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.jinahya.persistence.sakila.AddressConstants.GRAPH_CITY;
import static com.github.jinahya.persistence.sakila.AddressConstants.GRAPH_CITY_COUNTRY;
import static com.github.jinahya.persistence.sakila.AddressConstants.GRAPH_NODE_CITY;
import static com.github.jinahya.persistence.sakila.AddressConstants.QUERY_FIND_ALL;
import static com.github.jinahya.persistence.sakila.AddressConstants.QUERY_FIND_BY_ADDRESS_ID;
import static com.github.jinahya.persistence.sakila.AddressConstants.QUERY_PARAM_ADDRESS_MIN_EXCLUSIVE;
import static com.github.jinahya.persistence.sakila._PersistenceConstants.PERSISTENCE_FETCHGRAPH;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

class Address_NamedQueries_IT
        extends __PersistenceIT {

    private static final Logger log = getLogger(lookup().lookupClass());

    @DisplayName(QUERY_FIND_BY_ADDRESS_ID)
    @Nested
    class FindByAddressIdTest {

        @DisplayName("(0)NoResultException")
        @Test
        void _NoResultException_0() {
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(QUERY_FIND_BY_ADDRESS_ID, Address.class)
                                    .setParameter(Address_.addressId.getName(), 0)
                                    .getSingleResult()
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("(1)!null")
        @Test
        void _NotNull_1() {
            final var addressId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_BY_ADDRESS_ID, Address.class)
                            .setParameter(Address_.addressId.getName(), addressId)
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(Address::getAddressId)
                    .isEqualTo(addressId);
        }

        @DisplayName("(1)!null fetching city")
        @Test
        void _NotNull_1FetchCity() {
            final var addressId = 1;
            final var found = applyEntityManager(
                    em -> {
                        final var query = em.createNamedQuery(QUERY_FIND_BY_ADDRESS_ID, Address.class)
                                .setParameter(Address_.addressId.getName(), addressId);
                        if (current().nextBoolean()) {
                            final var graph = em.createEntityGraph(GRAPH_CITY);
                            query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                        } else {
                            final var graph = em.createEntityGraph(Address.class);
                            graph.addAttributeNodes(GRAPH_NODE_CITY);
                            query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                        }
                        return query.getSingleResult();
                    }
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(Address::getCity)
                    .satisfies(c -> {
                        assertThat(c.getCity()).isNotNull();
                    });
        }

        @DisplayName("(1)!null fetching city and city.country")
        @Test
        void _NotNull_1FetchCityCountry() {
            final var addressId = 1;
            final var found = applyEntityManager(
                    em -> {
                        final var query = em.createNamedQuery(QUERY_FIND_BY_ADDRESS_ID, Address.class)
                                .setParameter(Address_.addressId.getName(), addressId);
                        if (current().nextBoolean()) {
                            final var graph = em.createEntityGraph(GRAPH_CITY_COUNTRY);
                            query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                        } else {
                            final var graph = em.createEntityGraph(Address.class);
                            graph.addAttributeNodes(Address_.city);
                            final var subgraph = graph.addSubgraph(Address_.city);
                            subgraph.addAttributeNodes(City_.country);
                            query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                        }
                        return query.getSingleResult();
                    }
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(Address::getCity)
                    .extracting(City::getCountry)
                    .satisfies(c -> {
                        assertThat(c.getCountry()).isNotNull();
                    });
        }
    }

    @Nested
    class FindAllTest {

        @DisplayName("all pages")
        @Test
        void __() {
            final var maxResults = current().nextInt(16, 32);
            for (final var i = new AtomicInteger(0); ; ) {
                final var addressIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL, Address.class)
                                .setParameter(QUERY_PARAM_ADDRESS_MIN_EXCLUSIVE, addressIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(Comparator.comparing(Address::getAddressId));
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getAddressId());
            }
        }

        @DisplayName("all pages fetching city")
        @Test
        void __FetchCity() {
            final var maxResults = current().nextInt(32, 64);
            for (final var i = new AtomicInteger(0); ; ) {
                final var addressIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> {
                            final var query = em.createNamedQuery(QUERY_FIND_ALL, Address.class)
                                    .setParameter(QUERY_PARAM_ADDRESS_MIN_EXCLUSIVE, addressIdMinExclusive)
                                    .setMaxResults(maxResults);
                            if (current().nextBoolean()) {
                                final var graph = em.createEntityGraph(GRAPH_CITY);
                                query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                            } else {
                                final var graph = em.createEntityGraph(Address.class);
                                graph.addAttributeNodes(Address_.city);
                                query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                            }
                            return query.getResultList();
                        }
                );
                assertThat(list)
                        .extracting(Address::getCity)
                        .allSatisfy(c -> {
                            assertThat(c.getCity()).isNotNull();
                        });
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getAddressId());
            }
        }

        @DisplayName("all pages fetching city and city.country")
        @Test
        void __FetchCityCountry() {
            final var maxResults = current().nextInt(32, 64);
            for (final var i = new AtomicInteger(0); ; ) {
                final var addressIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> {
                            final var query = em.createNamedQuery(QUERY_FIND_ALL, Address.class)
                                    .setParameter(QUERY_PARAM_ADDRESS_MIN_EXCLUSIVE, addressIdMinExclusive)
                                    .setMaxResults(maxResults);
                            if (current().nextBoolean()) {
                                final var graph = em.createEntityGraph(GRAPH_CITY_COUNTRY);
                                query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                            } else {
                                final var graph = em.createEntityGraph(Address.class);
                                graph.addAttributeNodes(Address_.city);
                                final var subgraph = graph.addSubgraph(Address_.city);
                                subgraph.addAttributeNodes(City_.country);
                                query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                            }
                            return query.getResultList();
                        }
                );
                assertThat(list)
                        .extracting(Address::getCity)
                        .extracting(City::getCountry)
                        .allSatisfy(c -> {
                            assertThat(c.getCountry()).isNotNull();
                        });
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getAddressId());
            }
        }
    }

    @Test
    void __AddressAndLocation() {
        final var list = applyEntityManager(
                em -> em.createQuery(
                        """
                                SELECT a
                                FROM Address AS a
                                JOIN FETCH a.city AS c
                                JOIN FETCH c.country AS c2""",
                        Address.class
                ).getResultList()
        );
        list.forEach(a -> {
            a.applyLatitudeLongitude((latitude, longitude) -> {
                log.debug("{}, {}; google-maps-location: {}, {}",
                          a.getCity().getCity(), a.getCity().getCountry().getCountry(),
                          latitude, longitude
                );
                return null;
            });
        });
    }
}
