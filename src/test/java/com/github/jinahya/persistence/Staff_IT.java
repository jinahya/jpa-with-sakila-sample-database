package com.github.jinahya.persistence;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Objects;

@Slf4j
class Staff_IT
        extends _BaseEntityIT<Staff, Integer> {

    static Staff newPersistedInstance(final EntityManager entityManager, final Store store) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(store, "store is null");
        final var instance = new Staff_Randomizer().getRandomValue();
        instance.setAddress(Address_IT.newPersistedInstance(entityManager));
        instance.setStore(store);
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    static Staff newPersistedInstance(final EntityManager entityManager) {
        return newPersistedInstance(entityManager, Store_IT.findTheStore(entityManager));
    }

    Staff_IT() {
        super(Staff.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Staff_IT::newPersistedInstance);
    }
}
