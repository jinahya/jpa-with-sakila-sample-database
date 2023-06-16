package com.github.jinahya.persistence.sakila;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class FilmCategory_Test
        extends _BaseEntityTest<FilmCategory, FilmCategoryId> {

    FilmCategory_Test() {
        super(FilmCategory.class, FilmCategoryId.class);
    }

    @DisplayName("getId()FilmCategoryId")
    @Nested
    class GetIdTest {

        @Test
        void _Null_New() {
            final var instance = newEntityInstance();
            assertThat(instance.getId()).isNull();
        }
    }

    @DisplayName("setId(FilmCategoryId)")
    @Nested
    class SetIdTest {

    }

    @DisplayName("getFilm()Film")
    @Nested
    class GetFilmTest {

        @Test
        void _Null_New() {
            final var instance = newEntityInstance();
            assertThat(instance.getFilm()).isNull();
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
            assertThat(instance.getId()).isNotNull();
            assertThat(instance.getId().getFilmId()).isNull();
        }

        @Test
        void _Null_NotNullWithNullFilmId() {
            // GIVEN
            final var instance = newEntitySpy();
            final var film = new Film();
            assert film.getFilmId() == null;
            // WHEN
            instance.setFilm(film);
            // THEN
            assertThat(instance.getId()).isNotNull();
            assertThat(instance.getId().getFilmId()).isNull();
        }

        @Test
        void _NotNull_NotNull() {
            // GIVEN
            final var instance = newEntitySpy();
            final var film = spy(new Film());
            when(film.getFilmId()).thenReturn(0);
            // WHEN
            instance.setFilm(film);
            // THEN
            assertThat(instance.getId()).isNotNull();
            assertThat(instance.getId().getFilmId()).isEqualTo(film.getFilmId());
        }
    }

    @DisplayName("getCategory()Category")
    @Nested
    class GetCategoryTest {

        @Test
        void _Null_New() {
            final var instance = newEntityInstance();
            assertThat(instance.getCategory()).isNull();
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
            assertThat(instance.getId()).isNotNull();
            assertThat(instance.getId().getCategoryId()).isNull();
        }

        @Test
        void _Null_NotNullWithNullCategoryId() {
            // GIVEN
            final var instance = newEntitySpy();
            final var category = new Category();
            assert category.getCategoryId() == null;
            // WHEN
            instance.setCategory(category);
            // THEN
            assertThat(instance.getId()).isNotNull();
            assertThat(instance.getId().getCategoryId()).isNull();
        }

        @Test
        void _NotNull_NotNull() {
            // GIVEN
            final var instance = newEntitySpy();
            final var category = spy(new Category());
            when(category.getCategoryId()).thenReturn(0);
            // WHEN
            instance.setCategory(category);
            // THEN
            assertThat(instance.getId()).isNotNull();
            assertThat(instance.getId().getCategoryId()).isEqualTo(category.getCategoryId());
        }
    }
}
