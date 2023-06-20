package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.Objects;

import static com.github.jinahya.sakila.persistence.Address_IT.newPersistedAddress;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

class Staff_IT
        extends _BaseEntityIT<Staff, Integer> {

    private static final Logger log = getLogger(lookup().lookupClass());

    static Staff newPersistedStaff(final EntityManager entityManager, final Store store) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(store, "store is null");
        final var instance = new Staff_Randomizer().getRandomValue();
        instance.setAddress(newPersistedAddress(entityManager));
        instance.setStore(store);
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    static Staff newPersistedStaff(final EntityManager entityManager) {
        return newPersistedStaff(entityManager, Store_IT.findTheStore(entityManager));
    }

    Staff_IT() {
        super(Staff.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Staff_IT::newPersistedStaff);
    }
}
