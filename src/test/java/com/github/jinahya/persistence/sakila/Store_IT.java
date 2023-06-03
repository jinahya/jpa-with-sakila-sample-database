package com.github.jinahya.persistence.sakila;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static org.assertj.core.api.Assertions.assertThat;

class Store_IT
        extends _BaseEntityIT<Store, Integer> {

    static Store findTheStore(final EntityManager entityManager) {
        final var found = entityManager.find(Store.class, Store.COLUMN_VALUE_STORE_ID_THE);
        assertThat(found).isNotNull();
        assertThat(found.getStoreId()).isEqualTo(Store.COLUMN_VALUE_STORE_ID_THE);
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
        final var store = applyEntityManager(Store_IT::newPersistedInstance);
        assertThat(store).isNotNull().satisfies(s -> {
            assertThat(store.getStoreId()).isNotNull();
            assertThatBean(store).isValid();
        });
    }
}
