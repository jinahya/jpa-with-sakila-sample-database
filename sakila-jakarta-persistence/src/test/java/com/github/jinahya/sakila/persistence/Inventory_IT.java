package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;

import java.util.Objects;

import static com.github.jinahya.sakila.persistence.Film_IT.newPersistedFilm;

class Inventory_IT
        extends _BaseEntityIT<Inventory, Integer> {

    static Inventory newPersistedInventory(final EntityManager entityManager, final Store store) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(store, "store is null");
        final var instance = new Inventory_Randomizer().getRandomValue();
        instance.setFilm(newPersistedFilm(entityManager));
        instance.setStore(store);
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    static Inventory newPersistedInventory(final EntityManager entityManager) {
        return newPersistedInventory(entityManager, Store_IT.newPersistedStore(entityManager));
    }

    Inventory_IT() {
        super(Inventory.class, Integer.class);
    }
}
