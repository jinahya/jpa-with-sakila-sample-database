package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static com.github.jinahya.sakila.persistence.Store.COLUMN_VALUE_STORE_ID_THE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;

/**
 * A class unit-testing {@link Store} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see Store_Test
 */
class Store_IT
        extends _BaseEntityIT<Store, Integer> {

    /**
     * Finds the store identified by the {@link Store#COLUMN_VALUE_STORE_ID_THE}.
     *
     * @param entityManager an entity manager.
     * @return the store.
     */
    static Store findTheStore(final EntityManager entityManager) {
        final var found = entityManager.find(Store.class, COLUMN_VALUE_STORE_ID_THE);
        assertThat(found)
                .isNotNull()
                .extracting(Store::getStoreId)
                .isEqualTo(COLUMN_VALUE_STORE_ID_THE);
        return found;
    }

    static Store newPersistedStore(final EntityManager entityManager) {
        final var instance = new Store_Randomizer().getRandomValue();
        final var staff = Staff_IT.newPersistedStaff(entityManager);
        entityManager.detach(staff);
        instance.setManagerStaffId(staff.getStaffId());
        instance.setAddress(Address_IT.newPersistedAddress(entityManager));
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    Store_IT() {
        super(Store.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Store_IT::newPersistedStore);
        assertThat(instance)
                .isNotNull()
                .satisfies(e -> {
                    assertThat(e.getStoreId()).isNotNull();
                });
        assertThatBean(instance).isValid();
    }
}
