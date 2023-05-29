package com.github.jinahya.persistence.sakila;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static org.assertj.core.api.Assertions.assertThat;

class FilmCategory_IT
        extends _BaseEntityIT<FilmCategory, FilmCategoryId> {

    static FilmCategory newPersistedInstance(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new FilmCategory_Randomizer().getRandomValue();
        instance.setFilm(Film_IT.newPersistedInstance(entityManager));
        instance.setCategory(Category_IT.newPersistedInstance(entityManager));
        assertThatBean(instance).isValid();
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    FilmCategory_IT() {
        super(FilmCategory.class, FilmCategoryId.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(FilmCategory_IT::newPersistedInstance);
        assertThat(instance).isNotNull().satisfies(e -> {
            assertThat(e.getCategoryId()).isNotNull();
        });
        assertThatBean(instance).isValid();
    }
}
