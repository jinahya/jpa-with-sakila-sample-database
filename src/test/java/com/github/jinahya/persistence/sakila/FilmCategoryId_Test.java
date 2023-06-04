package com.github.jinahya.persistence.sakila;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;

class FilmCategoryId_Test
        extends _BaseEntityTest<FilmCategory, FilmCategoryId> {

    FilmCategoryId_Test() {
        super(FilmCategory.class, FilmCategoryId.class);
    }

    @DisplayName("getFilm()Film")
    @Nested
    class GetFilmTest {

        @Test
        void __() {
            final var instance = newEntityInstance();
            final var film = instance.getFilm();
            assertThat(film).isNull();
        }
    }

    @DisplayName("setFilm(Film)")
    @Nested
    class SetFilmTest {

        @Test
        void _Null_Null() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setFilm(null);
            // THEN
            assertThat(instance.getId())
                    .isNotNull()
                    .extracting(FilmCategoryId::getFilmId)
                    .isNull();
            ;
        }

        @Test
        void _NotNull_NotNull() {
            // GIVEN
            final var instance = newEntitySpy();
            final var filmId = current().nextInt();
            // WHEN
            instance.setFilm(Film.ofFilmId(filmId));
            // THEN
            assertThat(instance.getId())
                    .isNotNull()
                    .extracting(FilmCategoryId::getFilmId)
                    .isEqualTo(filmId);
            ;
        }
    }

    @DisplayName("getCategory()Category")
    @Nested
    class GetCategoryTest {

        @Test
        void __() {
            final var instance = newEntityInstance();
            final var category = instance.getCategory();
            assertThat(category).isNull();
        }
    }

    @DisplayName("setCategory(Category)")
    @Nested
    class SetCategoryTest {

        @Test
        void _Null_Null() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setCategory(null);
            // THEN
            assertThat(instance.getId())
                    .isNotNull()
                    .extracting(FilmCategoryId::getCategoryId)
                    .isNull();
            ;
        }

        @Test
        void _NotNull_NotNull() {
            // GIVEN
            final var instance = newEntitySpy();
            final var categoryId = current().nextInt();
            // WHEN
            instance.setCategory(Category.ofCategoryId(categoryId));
            // THEN
            assertThat(instance.getId())
                    .isNotNull()
                    .extracting(FilmCategoryId::getCategoryId)
                    .isEqualTo(categoryId);
            ;
        }
    }
}
