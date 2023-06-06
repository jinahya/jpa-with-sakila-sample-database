package com.github.jinahya.persistence.sakila;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static org.assertj.core.api.Assertions.assertThat;

class Language_IT
        extends _BaseEntityIT<Language, Integer> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    static Language newPersistedInstance(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new Language_Randomizer().getRandomValue();
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    Language_IT() {
        super(Language.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Language_IT::newPersistedInstance);
        assertThat(instance).isNotNull().satisfies(i -> {
            assertThat(i.getLanguageId()).isNotNull();
        });
        assertThatBean(instance).isValid();
    }
}
