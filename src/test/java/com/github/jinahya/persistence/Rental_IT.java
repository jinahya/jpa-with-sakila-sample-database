package com.github.jinahya.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Objects;

class Rental_IT
        extends _BaseEntityIT<Rental, Integer> {

    public static Rental newPersistedInstance(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new Rental_Randomizer().getRandomValue();
        final var store = Store_IT.newPersistedInstance(entityManager);
        instance.setInventory(Inventory_IT.newPersistedInstance(entityManager, store));
        instance.setCustomer(Customer_IT.newPersistedInstance(entityManager, store));
        instance.setStaff(Staff_IT.newPersistedInstance(entityManager, store));
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    Rental_IT() {
        super(Rental.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Rental_IT::newPersistedInstance);
    }
}
