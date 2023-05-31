package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class Language_NamedQueries_IT
        extends __PersistenceIT {

    @DisplayName("Language_findAll")
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var found = applyEntityManager(
                    em -> em.createNamedQuery("Language_findAll", Language.class)
                            .getResultList()
            );
            assertThat(found)
                    .isNotEmpty()
                    .doesNotContainNull();
        }
    }

    @DisplayName("Language_findByLanguageId")
    @Nested
    class FindByLanguageIdTest {

        @DisplayName("Language_findByLanguageId(0)")
        @Test
        void __0() {
            final var languageId = 0;
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery("Language_findByLanguageId", Language.class)
                                    .setParameter("languageId", languageId)
                                    .getSingleResult()
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("Language_findByLanguageId(1)")
        @Test
        void __1() {
            final var languageId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery("Language_findByLanguageId", Language.class)
                            .setParameter("languageId", languageId)
                            .getSingleResult()
            );
            assertThat(found)
                    .extracting(Language::getLanguageId)
                    .isEqualTo(languageId);
        }
    }

    @DisplayName("Language_findAllByName")
    @Nested
    class FindByNameTest {

        @Test
        void __English() {
            final var name = "English";
            final var found = applyEntityManager(
                    em -> em.createNamedQuery("Language_findAllByName", Language.class)
                            .setParameter("name", name)
                            .getResultList()
            );
            assertThat(found)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .extracting(Language::getName)
                    .containsOnly(name);
        }
    }
}
