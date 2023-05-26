package com.github.jinahya.persistence;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Language_IT
        extends _BaseEntityIT<Language, Integer> {

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
