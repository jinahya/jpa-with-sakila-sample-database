package com.github.jinahya.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Objects;

class Customer_IT
        extends _BaseEntityIT<Customer, Integer> {

    static Customer newPersistedInstance(final EntityManager entityManager, final Store store) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(store, "store is null");
        final var instance = new Customer_Randomizer().getRandomValue();
        instance.setStore(store);
        instance.setAddress(Address_IT.newPersistedInstance(entityManager));
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
    }
}
