package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class LanguageIT
        extends _BaseEntityIT<Language, Integer> {

    LanguageIT() {
        super(Language.class, Integer.class);
    }

    @Test
    void persist__() {
        final var entity = new Language();
        entity.setName("Jamaican Patois");
        acceptEntityManager(em -> {
            em.persist(entity);
        });
        assertThat(entity.getLanguageId()).isNotNull();
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
