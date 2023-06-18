package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;

class Film_IT
        extends _BaseEntityIT<Film, Integer> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
