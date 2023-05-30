package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * A class for testing named queries defined on {@link Inventory} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class Inventory_NamedQueries_IT
        extends __PersistenceIT {

    Inventory_NamedQueries_IT() {
        super();
    }

    @DisplayName(InventoryConstants.QUERY_FIND_BY_INVENTORY_ID)
    @Nested
    class FindByInventoryIdTest {

        @DisplayName("(0)NoResultException")
        @Test
        void _NoResultException_0() {
            final var inventoryId = 0;
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(InventoryConstants.QUERY_FIND_BY_INVENTORY_ID, Inventory.class)
                                    .setParameter(InventoryConstants.QUERY_PARAM_INVENTORY_ID, inventoryId)
                                    .getSingleResult() // NoResultException
                    )
            ).isInstanceOf(NoResultException.class);
        }
    }
}
