package com.github.jinahya.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Objects;

class Rental_IT
        extends _BaseEntityIT<Rental, Integer> {

    public static Rental newPersistedInstance(final EntityManager entityManager, final Customer customer) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(customer, "customer is null");
        final var instance = new Rental_Randomizer().getRandomValue();
        final var store = customer.getStore();
        instance.setInventory(Inventory_IT.newPersistedInstance(entityManager, store));
        instance.setCustomer(customer);
        instance.setStaff(Staff_IT.newPersistedInstance(entityManager, store));
        entityManager.persist(instance);
//        entityManager.flush();
        return instance;
    }

    public static Rental newPersistedInstance(final EntityManager entityManager, final Store store) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(store, "store is null");
        final var instance = new Rental_Randomizer().getRandomValue();
        instance.setInventory(Inventory_IT.newPersistedInstance(entityManager, store));
        instance.setCustomer(Customer_IT.newPersistedInstance(entityManager, store));
        instance.setStaff(Staff_IT.newPersistedInstance(entityManager, store));
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    public static Rental newPersistedInstance(final EntityManager entityManager) {
        return newPersistedInstance(entityManager, Store_IT.newPersistedInstance(entityManager));
    }

    Rental_IT() {
        super(Rental.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Rental_IT::newPersistedInstance);
    }
}
