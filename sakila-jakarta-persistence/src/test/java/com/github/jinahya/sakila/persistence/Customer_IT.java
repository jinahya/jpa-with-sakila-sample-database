package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static com.github.jinahya.sakila.persistence.Address_IT.newPersistedAddress;
import static com.github.jinahya.sakila.persistence.Store_IT.newPersistedStore;
import static org.assertj.core.api.Assertions.assertThat;

class Customer_IT
        extends _BaseEntityIT<Customer, Integer> {

    static Customer newPersistedCustomer(final EntityManager entityManager, final Store store) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(store, "store is null");
        final var instance = new Customer_Randomizer().getRandomValue();
        instance.setStore(store);
        instance.setAddress(newPersistedAddress(entityManager));
        assertThatBean(instance).isValid();
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    static Customer newPersistedCustomer(final EntityManager entityManager) {
        return newPersistedCustomer(entityManager, newPersistedStore(entityManager));
    }

    Customer_IT() {
        super(Customer.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Customer_IT::newPersistedCustomer);
        assertThat(instance).isNotNull().satisfies(e -> {
            assertThat(e.getCustomerId()).isNotNull();
        });
        assertThatBean(instance).isValid();
    }
}
