package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static org.assertj.core.api.Assertions.assertThat;

class Customer_IT
        extends _BaseEntityIT<Customer, Integer> {

    static Customer newPersistedInstance(final EntityManager entityManager, final Store store) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(store, "store is null");
        final var instance = new Customer_Randomizer().getRandomValue();
        instance.setStore(store);
        instance.setAddress(Address_IT.newPersistedInstance(entityManager));
        assertThatBean(instance).isValid();
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    static Customer newPersistedInstance(final EntityManager entityManager) {
        return newPersistedInstance(entityManager, Store_IT.newPersistedInstance(entityManager));
    }

    Customer_IT() {
        super(Customer.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Customer_IT::newPersistedInstance);
        assertThat(instance).isNotNull().satisfies(e -> {
            assertThat(e.getCustomerId()).isNotNull();
        });
        assertThatBean(instance).isValid();
    }
}
