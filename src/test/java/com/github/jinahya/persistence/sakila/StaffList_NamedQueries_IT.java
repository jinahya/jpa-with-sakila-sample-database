package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StaffList_NamedQueries_IT
        extends __PersistenceIT {

    @DisplayName(StaffListConstants.QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(StaffListConstants.QUERY_FIND_ALL, StaffList.class)
                            .getResultList()
            );
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull();
        }

        @Test
        void __WithMaxResults() {
            final int maxResults = ThreadLocalRandom.current().nextInt(1, 8);
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(StaffListConstants.QUERY_FIND_ALL, StaffList.class)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @DisplayName(StaffListConstants.QUERY_FIND_BY_ID)
    @Nested
    class FindByIdTest {

        @DisplayName("findById(0)")
        @Test
        void _NoResultException_0() {
            final var id = 0;
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(StaffListConstants.QUERY_FIND_BY_ID, StaffList.class)
                                    .setParameter("id", id)
                                    .getSingleResult()
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("findById(1)")
        @Test
        void __1() {
            final var id = 1;
            final var single = applyEntityManager(
                    em -> em.createNamedQuery(StaffListConstants.QUERY_FIND_BY_ID, StaffList.class)
                            .setParameter("id", id)
                            .getSingleResult()
            );
            assertThat(single)
                    .extracting(StaffList::getId)
                    .isEqualTo(id);
        }

        @DisplayName("findById(*)")
        @Test
        void __() {
            final var all = applyEntityManager(
                    em -> em.createNamedQuery(StaffListConstants.QUERY_FIND_ALL, StaffList.class)
                            .getResultList()
            );
            for (final var each : all) {
                final var single = applyEntityManager(
                        em -> em.createNamedQuery(StaffListConstants.QUERY_FIND_BY_ID, StaffList.class)
                                .setParameter("id", each.getId())
                                .getSingleResult()
                );
                assertThat(single)
                        .isNotNull()
                        .isEqualTo(each);
            }
        }
    }

    @DisplayName(StaffListConstants.QUERY_FIND_ALL_BY_ID_GREATER_THAN)
    @Nested
    class FindAllByIdGreaterThanTest {

        @Test
        void __() {
            final int maxResults = ThreadLocalRandom.current().nextInt(1, 3);
            for (final var i = new AtomicInteger(0); ; ) {
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(
                                        StaffListConstants.QUERY_FIND_ALL_BY_ID_GREATER_THAN,
                                        StaffList.class
                                )
                                .setParameter("idMinExclusive", i.get())
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults);
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getId());
            }
        }
    }
}
