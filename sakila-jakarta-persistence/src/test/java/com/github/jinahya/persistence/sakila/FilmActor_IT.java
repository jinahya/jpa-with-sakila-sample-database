package com.github.jinahya.persistence.sakila;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Objects;

class FilmActor_IT
        extends _BaseEntityIT<FilmActor, FilmActorId> {

    static FilmActor newPersistedInstance(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new FilmActor_Randomizer().getRandomValue();
        instance.setActor(Actor_IT.newPersistedInstance(entityManager));
        instance.setFilm(Film_IT.newPersistedInstance(entityManager));
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    FilmActor_IT() {
        super(FilmActor.class, FilmActorId.class);
    }

    @Test
    void __() {
        final var instance = applyEntityManager(FilmActor_IT::newPersistedInstance);
    }
}
