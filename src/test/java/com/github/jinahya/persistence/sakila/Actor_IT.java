package com.github.jinahya.persistence.sakila;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Objects;

class Actor_IT
        extends _BaseEntityIT<Actor, Integer> {

    static Actor newPersistedInstance(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new Actor_Randomizer().getRandomValue();
        entityManager.persist(instance);
        return instance;
    }

    Actor_IT() {
        super(Actor.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Actor_IT::newPersistedInstance);
    }
}
