package com.github.jinahya.persistence.sakila;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static com.github.jinahya.persistence.sakila.Store.COLUMN_VALUE_STORE_ID_THE;
import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(found).isNotNull();
        assertThat(found.getStoreId())
                .isEqualTo(COLUMN_VALUE_STORE_ID_THE);
        return found;
    }

    static Store newPersistedInstance(final EntityManager entityManager) {
        final var instance = new Store_Randomizer().getRandomValue();
        final var staff = Staff_IT.newPersistedInstance(entityManager);
        entityManager.detach(staff);
        instance.setManagerStaffId(staff.getStaffId());
        instance.setAddress(Address_IT.newPersistedInstance(entityManager));
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    Store_IT() {
        super(Store.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Store_IT::newPersistedInstance);
        assertThat(instance)
                .isNotNull()
                .satisfies(e -> {
                    assertThat(e.getStoreId()).isNotNull();
                });
        assertThatBean(instance).isValid();
    }
}
