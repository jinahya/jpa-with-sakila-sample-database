package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;

import java.util.Objects;

class Inventory_IT
        extends _BaseEntityIT<Inventory, Integer> {

    static Inventory newPersistedInstance(final EntityManager entityManager, final Store store) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(store, "store is null");
        final var instance = new Inventory_Randomizer().getRandomValue();
        instance.setFilm(Film_IT.newPersistedInstance(entityManager));
        instance.setStore(store);
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    static Inventory newPersistedInstance(final EntityManager entityManager) {
        return newPersistedInstance(entityManager, Store_IT.newPersistedInstance(entityManager));
    }

    Inventory_IT() {
        super(Inventory.class, Integer.class);
    }
}
