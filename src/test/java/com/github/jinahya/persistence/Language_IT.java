package com.github.jinahya.persistence;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Language_IT
        extends _BaseEntityIT<Language, Integer> {

    static Language newPersistedInstance(final EntityManager entityManager) {
        final var instance = new Language_Randomizer().getRandomValue();
        return entityManager.createNamedQuery("Language_findByName", Language.class)
                .setParameter("name", instance.getName())
                .getResultList()
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    entityManager.persist(instance);
                    entityManager.flush();
                    return instance;
                });
    }

    Language_IT() {
        super(Language.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Language_IT::newPersistedInstance);
        assertThat(instance).isNotNull();
        assertThat(instance.getLanguageId()).isNotNull();
        assertThatBean(instance).isValid();
    }

    @Nested
    class NamedQueryTest {

        @Test
        void findAll__() {
            final List<Language> list = applyEntityManager(em -> {
                final var query = em.createNamedQuery("Language_findAll", Language.class);
                return query.getResultList();
            });
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull();
            list.forEach(e -> {
                log.debug("language: {}", e);
            });
        }

        @Test
        void findByName__() {
            final var name = "English";
            final List<Language> list = applyEntityManager(em -> {
                final var query = em.createNamedQuery("Language_findByName", Language.class);
                query.setParameter("name", name);
                return query.getResultList();
            });
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .extracting(Language::getName)
                    .containsOnly(name);
        }
    }
}
