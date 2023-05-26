package com.github.jinahya.persistence;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;

@Slf4j
class Film_IT
        extends _BaseEntityIT<Film, Integer> {

    static Film newPersistedInstance(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new Film_Randomizer().getRandomValue();
        instance.setLanguage(Language_IT.newPersistedInstance(entityManager));
        if (ThreadLocalRandom.current().nextBoolean()) {
            instance.setOriginalLanguage(Language_IT.newPersistedInstance(entityManager));
        }
//        assertThatBean(instance).isValid();
        entityManager.persist(instance);
            entityManager.flush();
        return instance;
    }

    Film_IT() {
        super(Film.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Film_IT::newPersistedInstance);
        assertThatBean(instance).isValid();
    }
}
