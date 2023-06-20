package com.github.jinahya.sakila.persistence;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.PARAMETER_CATEGORY;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.PARAMETER_CATEGORY_CATEGORY_ID;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.PARAMETER_FILM;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.PARAMETER_FILM_FILM_ID;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.PARAMETER_ID;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.PARAMETER_ID_CATEGORY_ID;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.PARAMETER_ID_CATEGORY_ID_MIN_EXCLUSIVE;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.PARAMETER_ID_FILM_ID;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.PARAMETER_ID_FILM_ID_MIN;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.PARAMETER_ID_FILM_ID_MIN_EXCLUSIVE;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.QUERY_FIND_ALL;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.QUERY_FIND_ALL_BY_CATEGORY;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.QUERY_FIND_ALL_BY_CATEGORY_CATEGORY_ID;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.QUERY_FIND_ALL_BY_FILM;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.QUERY_FIND_ALL_BY_FILM_FILM_ID;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.QUERY_FIND_ALL_BY_ID_CATEGORY_ID;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.QUERY_FIND_ALL_BY_ID_FILM_ID;
import static com.github.jinahya.sakila.persistence.FilmCategoryConstants.QUERY_FIND_BY_ID;
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
                                .setParameter(PARAMETER_ID_FILM_ID_MIN, filmIdMIn.get())
                                .setParameter(PARAMETER_ID_CATEGORY_ID_MIN_EXCLUSIVE, categoryIdMinExclusive.get())
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
                                .setParameter(PARAMETER_ID_CATEGORY_ID_MIN_EXCLUSIVE, idCategoryIdMinExclusive.get())
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

    @DisplayName(QUERY_FIND_ALL_BY_FILM_FILM_ID)
    @Nested
    class FindAllByFilmFilmIdTest {

        @DisplayName("(1004)")
        @Test
        void __() {
            final var filmFilmId = 1004; // Gone with the Wind
            final var idCategoryIdMinExclusive = new AtomicInteger(0);
            final var maxResults = 2;
            while (true) {
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_FILM_FILM_ID, FilmCategory.class)
                                .setParameter(PARAMETER_FILM_FILM_ID, filmFilmId)
                                .setParameter(PARAMETER_ID_CATEGORY_ID_MIN_EXCLUSIVE, idCategoryIdMinExclusive.get())
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .extracting(FilmCategory::getId)
                        .allMatch(v -> v.getFilmId() == filmFilmId)
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
                                .setParameter(PARAMETER_ID_CATEGORY_ID_MIN_EXCLUSIVE, idCategoryIdMinExclusive.get())
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

    @DisplayName(QUERY_FIND_ALL_BY_ID_CATEGORY_ID)
    @Nested
    class FindAllByIdCategoryIdTest {

        @DisplayName("(15)")
        @Test
        void __() {
            final var idCategoryId = 15; // Sports
            final var idFilmIdMinExclusive = new AtomicInteger(0);
            final var maxResults = 2;
            while (true) {
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_ID_CATEGORY_ID, FilmCategory.class)
                                .setParameter(PARAMETER_ID_CATEGORY_ID, idCategoryId)
                                .setParameter(PARAMETER_ID_FILM_ID_MIN_EXCLUSIVE, idFilmIdMinExclusive.get())
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .extracting(FilmCategory::getId)
                        .allMatch(v -> v.getCategoryId() == idCategoryId)
                        .allMatch(v -> v.getFilmId() > idFilmIdMinExclusive.get())
                        .isSorted();
                if (list.isEmpty()) {
                    break;
                }
                idFilmIdMinExclusive.set(
                        list.get(list.size() - 1).getId().getFilmId()
                );
            }
        }
    }

    @DisplayName(QUERY_FIND_ALL_BY_CATEGORY_CATEGORY_ID)
    @Nested
    class FindAllByCategoryCategoryIdTest {

        @DisplayName("(15)")
        @Test
        void __() {
            final var categoryCategoryId = 15; // Sports
            final var idFilmIdMinExclusive = new AtomicInteger(0);
            final var maxResults = 2;
            while (true) {
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_CATEGORY_CATEGORY_ID, FilmCategory.class)
                                .setParameter(PARAMETER_CATEGORY_CATEGORY_ID, categoryCategoryId)
                                .setParameter(PARAMETER_ID_FILM_ID_MIN_EXCLUSIVE, idFilmIdMinExclusive.get())
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .extracting(FilmCategory::getId)
                        .allMatch(v -> v.getCategoryId() == categoryCategoryId)
                        .allMatch(v -> v.getFilmId() > idFilmIdMinExclusive.get())
                        .isSorted();
                if (list.isEmpty()) {
                    break;
                }
                idFilmIdMinExclusive.set(
                        list.get(list.size() - 1).getId().getFilmId()
                );
            }
        }
    }

    @DisplayName(QUERY_FIND_ALL_BY_CATEGORY)
    @Nested
    class FindAllByIdCategoryTest {

        @DisplayName("(15)")
        @Test
        void __() {
            final var category = Category.ofCategoryId(15); // Sports
            final var idFilmIdMinExclusive = new AtomicInteger(0);
            final var maxResults = 2;
            while (true) {
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_CATEGORY, FilmCategory.class)
                                .setParameter(PARAMETER_CATEGORY, category)
                                .setParameter(PARAMETER_ID_FILM_ID_MIN_EXCLUSIVE, idFilmIdMinExclusive.get())
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .extracting(FilmCategory::getId)
                        .allMatch(v -> Objects.equals(v.getCategoryId(), category.getCategoryId()))
                        .allMatch(v -> v.getFilmId() > idFilmIdMinExclusive.get())
                        .isSorted();
                if (list.isEmpty()) {
                    break;
                }
                idFilmIdMinExclusive.set(
                        list.get(list.size() - 1).getId().getFilmId()
                );
            }
        }
    }
}
