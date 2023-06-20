package com.github.jinahya.sakila.persistence;

import jakarta.persistence.NoResultException;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Comparator.comparing;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

class Staff_NamedQueries_IT
        extends __PersistenceIT {

    private static final Logger log = getLogger(lookup().lookupClass());

    Staff_NamedQueries_IT() {
        super();
    }

    @DisplayName(StaffConstants.QUERY_FIND_BY_STAFF_ID)
    @Nested
    class FindByStaffIdTest {

        @DisplayName("(0) -> NoResultException")
        @Test
        void _NotResultException_0() {
            final var staffId = 0;
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(StaffConstants.QUERY_FIND_BY_STAFF_ID, Staff.class)
                                    .setParameter(StaffConstants.PARAMETER_STAFF_ID, staffId)
                                    .getSingleResult() // NoResultException
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("(1) -> !null")
        @Test
        void _NotNul_1() {
            final var staffId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(StaffConstants.QUERY_FIND_BY_STAFF_ID, Staff.class)
                            .setParameter(StaffConstants.PARAMETER_STAFF_ID, staffId)
                            .getSingleResult() // NoResultException
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(Staff::getStaffId)
                    .isEqualTo(staffId);
        }
    }

    @DisplayName(StaffConstants.QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var maxResults = current().nextInt(1, 16);
            for (final var i = new AtomicInteger(0); ; ) {
                final var staffIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(StaffConstants.QUERY_FIND_ALL, Staff.class)
                                .setParameter(StaffConstants.PARAMETER_STAFF_ID_MIN_EXCLUSIVE, staffIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(Staff::getStaffId));
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getStaffId());
            }
        }
    }

    @DisplayName(StaffConstants.QUERY_FIND_ALL_BY_CITY)
    @Nested
    class FindAllByCityTest {

        @ExplicitParamInjection
        @ValueSource(ints = {300, 576, 601})
        @ParameterizedTest
        void __(final int cityId) {
            final var city = City.ofCityId(cityId);
            final var maxResults = current().nextInt(1, 16);
            for (final var i = new AtomicInteger(0); ; ) {
                final var staffIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(StaffConstants.QUERY_FIND_ALL_BY_CITY, Staff.class)
                                .setParameter(StaffConstants.PARAMETER_CITY, city)
                                .setParameter(StaffConstants.PARAMETER_STAFF_ID_MIN_EXCLUSIVE, staffIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(Staff::getStaffId))
                        .extracting(Staff::getAddress)
                        .extracting(Address::getCity)
                        .extracting(City::getCityId)
//                        .containsOnly(cityId) // 비어 있으면 실패한다!
                        .allMatch(v -> v == cityId); // 그래서 이걸로 써야 한다.
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getStaffId());
            }
        }
    }
}
