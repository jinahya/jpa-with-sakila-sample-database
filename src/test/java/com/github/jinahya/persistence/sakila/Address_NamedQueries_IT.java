package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static com.github.jinahya.persistence.sakila.AddressConstants.GRAPH_CITY;
import static com.github.jinahya.persistence.sakila.AddressConstants.GRAPH_CITY_COUNTRY;
import static com.github.jinahya.persistence.sakila.AddressConstants.NODE_CITY;
import static com.github.jinahya.persistence.sakila.AddressConstants.QUERY_FIND_BY_ADDRESS_ID;
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
            assertThatThrownBy(() -> {
                applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_BY_ADDRESS_ID, Address.class)
                                .setParameter(Address_.addressId.getName(), 0)
                                .getSingleResult()
                );
            }).isInstanceOf(NoResultException.class);
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

        @DisplayName("(1+City)!null")
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
                            graph.addAttributeNodes(NODE_CITY);
                            query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                        }
                        return query.getSingleResult();
                    }
            );
            assertThat(found)
                    .isNotNull()
                    .satisfies(address -> {
                        assertThat(address.getAddressId()).isEqualTo(addressId);
                        assertThat(address.getCity()).isNotNull();
                        assertThat(address.getCity().getCity()).isNotNull();
                    });
        }
    }

    @DisplayName("(1+City+Country)!null")
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
                .satisfies(address -> {
                    assertThat(address.getAddressId()).isEqualTo(addressId);
                    assertThat(address.getCity()).satisfies(city -> {
                        assertThat(city.getCity()).isNotNull();
                        assertThat(city.getCountry()).satisfies(country -> {
                            assertThat(country.getCountry()).isNotNull();
                        });
                    });
                });
    }

    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(AddressConstants.QUERY_FIND_ALL, Address.class)
                            .getResultList()
            );
            assertThat(found)
                    .isNotNull()
                    .isNotEmpty()
                    .doesNotContainNull();
            found.forEach(a -> {
                a.applyLocationGeometryAsPoint((x, y) -> {
                    log.debug("point; x: {}, y: {}", x, y);
                    return null;
                });
            });
        }

        @Test
        void __WithMaxResults() {
            final var maxResults = current().nextInt(1, 8);
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(AddressConstants.QUERY_FIND_ALL, Address.class)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(found)
                    .isNotNull()
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults);
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
