package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Language_NamedQueries_IT
        extends _BaseEntityIT<Language, Integer> {

    Language_NamedQueries_IT() {
        super(Language.class, Integer.class);
    }

    @DisplayName("Language_findAll")
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

    @DisplayName("Language_findByName")
    @Test
    void findByName__() {
        final var name = "English";
        final List<Language> list = applyEntityManager(em -> {
            final var query = em.createNamedQuery("Language_findAllByName", Language.class);
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
