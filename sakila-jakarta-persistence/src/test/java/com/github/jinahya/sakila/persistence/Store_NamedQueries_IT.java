package com.github.jinahya.sakila.persistence;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.jinahya.sakila.persistence.StoreConstants.ENTITY_GRAPH_ADDRESS;
import static com.github.jinahya.sakila.persistence.StoreConstants.ENTITY_GRAPH_MANAGER_STAFF;
import static com.github.jinahya.sakila.persistence.StoreConstants.ENTITY_GRAPH_MANAGER_STAFF_AND_ADDRESS;
import static com.github.jinahya.sakila.persistence.StoreConstants.PARAMETER_MANAGER_STAFF;
import static com.github.jinahya.sakila.persistence.StoreConstants.PARAMETER_MANAGER_STAFF_ID;
import static com.github.jinahya.sakila.persistence.StoreConstants.PARAMETER_STORE_ID;
import static com.github.jinahya.sakila.persistence.StoreConstants.PARAMETER_STORE_ID_MIN_EXCLUSIVE;
import static com.github.jinahya.sakila.persistence.StoreConstants.QUERY_FIND_ALL;
import static com.github.jinahya.sakila.persistence.StoreConstants.QUERY_FIND_BY_MANAGER_STAFF;
import static com.github.jinahya.sakila.persistence.StoreConstants.QUERY_FIND_BY_MANAGER_STAFF_ID;
import static com.github.jinahya.sakila.persistence.StoreConstants.QUERY_FIND_BY_STORE_ID;
import static com.github.jinahya.sakila.persistence._PersistenceConstants.PERSISTENCE_FETCHGRAPH;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Store_NamedQueries_IT
        extends __PersistenceIT {

    Store_NamedQueries_IT() {
        super();
    }

    @DisplayName(QUERY_FIND_BY_STORE_ID)
    @Nested
    class FindByStoreIdTest {

        @DisplayName("(0)NoResultException")
        @Test
        void _NoResultException_0() {
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(QUERY_FIND_BY_STORE_ID, Store.class)
                                    .setParameter(PARAMETER_STORE_ID, 0)
                                    .getSingleResult() // NoResultException
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("(1)!null")
        @Test
        void __1() {
            final var storeId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_BY_STORE_ID, Store.class)
                            .setParameter(PARAMETER_STORE_ID, 1)
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(Store::getStoreId)
                    .isEqualTo(storeId);
        }

        @DisplayName("(1FetchManagerStaff)!null")
        @Test
        void __1FetchManagerStaff() {
            final var storeId = 1;
            final var found = applyEntityManager(em -> {
                final var query = em
                        .createNamedQuery(QUERY_FIND_BY_STORE_ID, Store.class)
                        .setParameter(PARAMETER_STORE_ID, 1);
                if (current().nextBoolean()) {
                    final var graph = em.createEntityGraph(ENTITY_GRAPH_MANAGER_STAFF);
                    query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                } else {
                    final var graph = em.createEntityGraph(Store.class);
                    graph.addAttributeNodes(Store_.managerStaff);
                    query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                }
                return query.getSingleResult();
            });
            assertThat(found)
                    .isNotNull()
                    .satisfies(e -> {
                        assertThat(e.getStoreId()).isEqualTo(storeId);
                        assertThat(e.getManagerStaff()).isNotNull();
                        assertThat(e.getManagerStaff().getLastName()).isNotNull();
                    });
        }

        @DisplayName("(1FetchAddress)!null")
        @Test
        void __1FetchAddress() {
            final var storeId = 1;
            final var found = applyEntityManager(em -> {
                final var query = em
                        .createNamedQuery(QUERY_FIND_BY_STORE_ID, Store.class)
                        .setParameter(PARAMETER_STORE_ID, 1);
                if (current().nextBoolean()) {
                    final var graph = em.createEntityGraph(ENTITY_GRAPH_ADDRESS);
                    query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                } else {
                    final var graph = em.createEntityGraph(Store.class);
                    graph.addAttributeNodes(Store_.address);
                    query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                }
                return query.getSingleResult();
            });
            assertThat(found)
                    .isNotNull()
                    .satisfies(e -> {
                        assertThat(e.getStoreId()).isEqualTo(storeId);
                        assertThat(e.getAddress()).isNotNull();
                        assertThat(e.getAddress().getDistrict()).isNotNull();
                    });
        }

        @DisplayName("(1FetchManagerStaffAndAddress)!null")
        @Test
        void __1FetchManagerStaffAndAddress() {
            final var storeId = 1;
            final var found = applyEntityManager(em -> {
                final var query = em
                        .createNamedQuery(QUERY_FIND_BY_STORE_ID, Store.class)
                        .setParameter(PARAMETER_STORE_ID, 1);
                if (current().nextBoolean()) {
                    final var graph = em.createEntityGraph(ENTITY_GRAPH_MANAGER_STAFF_AND_ADDRESS);
                    query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                } else {
                    final var graph = em.createEntityGraph(Store.class);
                    graph.addAttributeNodes(Store_.managerStaff, Store_.address);
                    query.setHint(PERSISTENCE_FETCHGRAPH, graph);
                }
                return query.getSingleResult();
            });
            assertThat(found)
                    .isNotNull()
                    .satisfies(e -> {
                        assertThat(e.getStoreId()).isEqualTo(storeId);
                        assertThat(e.getManagerStaff()).isNotNull();
                        assertThat(e.getManagerStaff().getLastName()).isNotNull();
                        assertThat(e.getAddress()).isNotNull();
                        assertThat(e.getAddress().getDistrict()).isNotNull();
                    });
        }
    }

    @DisplayName(QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var maxResults = current().nextInt(1, 3);
            for (final var i = new AtomicInteger(0); ; ) {
                final var storeIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL, Store.class)
                                .setParameter(PARAMETER_STORE_ID_MIN_EXCLUSIVE, storeIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .extracting(Store::getStoreId)
                        .allMatch(v -> v > storeIdMinExclusive)
                        .isSorted();
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getStoreId());
            }
        }
    }

    @DisplayName(QUERY_FIND_BY_MANAGER_STAFF_ID)
    @Nested
    class FindByManagerStaffId {

        @Test
        void _NoResultException_0() {
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(QUERY_FIND_BY_MANAGER_STAFF_ID, Store.class)
                                    .setParameter(PARAMETER_MANAGER_STAFF_ID, 0)
                                    .getSingleResult() // NoResultException
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @Test
        void _NotNull_1() {
            final var managerStaffId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_BY_MANAGER_STAFF_ID, Store.class)
                            .setParameter(PARAMETER_MANAGER_STAFF_ID, managerStaffId)
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(Store::getManagerStaffId)
                    .isEqualTo(managerStaffId);
        }
    }

    @DisplayName(QUERY_FIND_BY_MANAGER_STAFF)
    @Nested
    class FindByManagerStaff {

        @Test
        void _NoResultException_0() {
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(QUERY_FIND_BY_MANAGER_STAFF, Store.class)
                                    .setParameter(PARAMETER_MANAGER_STAFF, Staff.ofStaffId(0))
                                    .getSingleResult() // NoResultException
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @Test
        void _NotNull_1() {
            final var managerStaff = Staff.ofStaffId(1);
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_BY_MANAGER_STAFF, Store.class)
                            .setParameter(PARAMETER_MANAGER_STAFF, managerStaff)
                            .setHint(PERSISTENCE_FETCHGRAPH, em.createEntityGraph(ENTITY_GRAPH_MANAGER_STAFF))
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(Store::getManagerStaff)
                    .isEqualTo(managerStaff);
        }
    }
}
