package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.github.jinahya.sakila.persistence.Actor_IT.newPersistedActor;
import static com.github.jinahya.sakila.persistence.Film_IT.newPersistedFilm;

class FilmActor_IT
        extends _BaseEntityIT<FilmActor, FilmActorId> {

    static FilmActor newPersistedFilmActor(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new FilmActor_Randomizer().getRandomValue();
        instance.setActor(newPersistedActor(entityManager));
        instance.setFilm(newPersistedFilm(entityManager));
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    FilmActor_IT() {
        super(FilmActor.class, FilmActorId.class);
    }

    @Test
    void __() {
        final var instance = applyEntityManager(FilmActor_IT::newPersistedFilmActor);
    }
}
