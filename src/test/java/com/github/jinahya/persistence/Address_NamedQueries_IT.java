package com.github.jinahya.persistence;

import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class Address_NamedQueries_IT
        extends _BaseEntityIT<Address, Integer> {

    Address_NamedQueries_IT() {
        super(Address.class, Integer.class);
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
                a.getLocationGeometryAsPoint((x, y) -> {
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

        @Test
        void __AddressAndLocation() {
            final var found = applyEntityManager(
                    em -> em.createQuery("""
                                                 SELECT a
                                                 FROM Address AS a
                                                 JOIN FETCH a.city AS c
                                                 JOIN FETCH c.country AS c2""",
                                         Address.class)
                            .getResultList()
            );
            found.forEach(a -> {
                a.getLocationGeometryAsPoint((x, y) -> {
                    log.debug("{}, {}; location: {}, {}",
                              a.getCity().getCity(), a.getCity().getCountry().getCountry(),
                              y, x
                    );
                    return null;
                });
            });
        }
    }

    @Nested
    class FindAllByIdTest {

        @Test
        void _NoResultException_0() {
            final var addressId = 0;
            assertThatThrownBy(() -> {
                applyEntityManager(
                        em -> em.createNamedQuery("Address_findByAddressId", Address.class)
                                .setParameter("addressId", addressId)
                                .getSingleResult()
                );
            }).isInstanceOf(NoResultException.class);
        }

        @Test
        void __1() {
            final var addressId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery("Address_findByAddressId", Address.class)
                            .setParameter("addressId", addressId)
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(Address::getAddressId)
                    .isEqualTo(addressId);
        }
    }
}
