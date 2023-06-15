package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.PARAMETER_FILM;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.PARAMETER_ID;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.PARAMETER_ID_FILM_ID;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_ALL;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_ALL_BY_FILM;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_ALL_BY_ID_FILM_ID;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_BY_ID;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_PARAM_ID_CATEGORY_ID_MIN_EXCLUSIVE;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_PARAM_ID_FILM_ID_MIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FilmCategory_NamedQueries_IT
        extends __PersistenceIT {

    FilmCategory_NamedQueries_IT() {
        super();
    }

    @DisplayName(QUERY_FIND_BY_ID)
    @Nested
    class FindByIdTest {

        @DisplayName("(0, 0)NoResultException")
        @Test
        void _NoResultException_00() {
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(QUERY_FIND_BY_ID, FilmCategory.class)
                                    .setParameter(PARAMETER_ID, FilmCategoryId.of(0, 0))
                                    .getSingleResult()
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("(1, 6)!null")
        @Test
        void _NotNull_16() {
            final var id = FilmCategoryId.of(1, 6);
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_BY_ID, FilmCategory.class)
                            .setParameter(PARAMETER_ID, id)
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(FilmCategory::getId)
                    .isNotNull()
                    .satisfies(v -> {
                        assertThat(v.getFilmId()).isEqualTo(id.getFilmId());
                        assertThat(v.getCategoryId()).isEqualTo(id.getCategoryId());
                    });
        }

        @DisplayName("(1009, 7)!null")
        @Test
        void _NotNull_10097() {
            final var id = FilmCategoryId.of(1009, 7);
            // TODO: implement!
        }
    }

    @DisplayName(QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var filmIdMIn = new AtomicInteger(0);
            final var categoryIdMinExclusive = new AtomicInteger(0);
            final var maxResults = 128;
            while (true) {
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL, FilmCategory.class)
                                .setParameter(QUERY_PARAM_ID_FILM_ID_MIN, filmIdMIn.get())
                                .setParameter(QUERY_PARAM_ID_CATEGORY_ID_MIN_EXCLUSIVE, categoryIdMinExclusive.get())
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .extracting(FilmCategory::getId)
                        .isSorted()
                        .allMatch(v -> {
                            final var filmId = v.getFilmId();
                            final var categoryId = v.getCategoryId();
                            return (filmId == filmIdMIn.get() && categoryId > categoryIdMinExclusive.get())
                                   || filmId > filmIdMIn.get();
                        });
                if (list.isEmpty()) {
                    break;
                }
                final var lastId = list.get(list.size() - 1).getId();
                filmIdMIn.set(lastId.getFilmId());
                categoryIdMinExclusive.set(lastId.getCategoryId());
            }
        }
    }

    @DisplayName(QUERY_FIND_ALL_BY_ID_FILM_ID)
    @Nested
    class FindAllByIdFilmIdTest {

        @DisplayName("(1004)")
        @Test
        void __() {
            final var idFilmId = 1004; // Gone with the Wind
            final var idCategoryIdMinExclusive = new AtomicInteger(0);
            final var maxResults = 2;
            while (true) {
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_ID_FILM_ID, FilmCategory.class)
                                .setParameter(PARAMETER_ID_FILM_ID, idFilmId)
                                .setParameter(QUERY_PARAM_ID_CATEGORY_ID_MIN_EXCLUSIVE, idCategoryIdMinExclusive.get())
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .extracting(FilmCategory::getId)
                        .allMatch(v -> v.getFilmId() == idFilmId)
                        .allMatch(v -> v.getCategoryId() > idCategoryIdMinExclusive.get())
                        .isSorted();
                if (list.isEmpty()) {
                    break;
                }
                idCategoryIdMinExclusive.set(
                        list.get(list.size() - 1).getId().getCategoryId()
                );
            }
        }
    }

    @DisplayName(QUERY_FIND_ALL_BY_FILM)
    @Nested
    class FindAllByIdFilmTest {

        @DisplayName("(1004)")
        @Test
        void __() {
            final var film = Film.ofFilmId(1004); // Gone with the Wind
            final var idCategoryIdMinExclusive = new AtomicInteger(0);
            final var maxResults = 2;
            while (true) {
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_FILM, FilmCategory.class)
                                .setParameter(PARAMETER_FILM, film)
                                .setParameter(QUERY_PARAM_ID_CATEGORY_ID_MIN_EXCLUSIVE, idCategoryIdMinExclusive.get())
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .extracting(FilmCategory::getId)
                        .allMatch(v -> Objects.equals(v.getFilmId(), film.getFilmId()))
                        .allMatch(v -> v.getCategoryId() > idCategoryIdMinExclusive.get())
                        .isSorted();
                if (list.isEmpty()) {
                    break;
                }
                idCategoryIdMinExclusive.set(
                        list.get(list.size() - 1).getId().getCategoryId()
                );
            }
        }
    }
}
