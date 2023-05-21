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
        void __WithMaxResults() {
            final var maxResults = current().nextInt(1, 8);
            final var found = applyEntityManager(
                    em -> em.createNamedQuery("Address_findAll", Address.class)
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
