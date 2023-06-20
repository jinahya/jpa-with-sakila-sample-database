package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

class Film_IT
        extends _BaseEntityIT<Film, Integer> {

    private static final Logger log = getLogger(lookup().lookupClass());

    static Film newPersistedFilm(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new Film_Randomizer().getRandomValue();
        instance.setLanguage(Language_IT.newPersistedLanguage(entityManager));
        if (ThreadLocalRandom.current().nextBoolean()) {
            instance.setOriginalLanguage(Language_IT.newPersistedLanguage(entityManager));
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
        final var instance = applyEntityManager(Film_IT::newPersistedFilm);
        assertThatBean(instance).isValid();
    }
}
