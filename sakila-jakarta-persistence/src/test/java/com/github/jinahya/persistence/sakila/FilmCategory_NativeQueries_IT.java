package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.jinahya.persistence.sakila.Category.ofCategoryId;
import static com.github.jinahya.persistence.sakila.Film.ofFilmId;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_ALL;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_ALL_BY_CATEGORY;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_ALL_BY_FILM;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_ALL_BY_ID_CATEGORY_ID;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_ALL_BY_ID_FILM_ID;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.NATIVE_QUERY_SELECT_BY_FILM_ID_AND_CATEGORY_ID;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_PARAM_CATEGORY;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_PARAM_FILM;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.PARAMETER_ID;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.PARAMETER_ID_CATEGORY_ID;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_PARAM_ID_CATEGORY_ID_MIN_EXCLUSIVE;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.PARAMETER_ID_FILM_ID;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_PARAM_ID_FILM_ID_MIN;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_PARAM_ID_FILM_ID_MIN_EXCLUSIVE;
import static java.util.Comparator.comparing;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FilmCategory_NativeQueries_IT
        extends __PersistenceIT {

    FilmCategory_NativeQueries_IT() {
        super();
    }

    @DisplayName(NATIVE_QUERY_SELECT_BY_FILM_ID_AND_CATEGORY_ID)
    @Nested
    class FindByIdTest {

        @Test
        void __00() {
            final var id = current().nextBoolean()
                    ? FilmCategoryId.of(0, current().nextInt())
                    : FilmCategoryId.of(current().nextInt(), 0);
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(NATIVE_QUERY_SELECT_BY_FILM_ID_AND_CATEGORY_ID, FilmCategory.class)
                                    .setParameter(PARAMETER_ID, id)
                                    .getSingleResult() // NoResultException

                    )
            ).isInstanceOf(NoResultException.class);
        }

        @Test
        void __16() {
            final var id = FilmCategoryId.of(1, 6);
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(NATIVE_QUERY_SELECT_BY_FILM_ID_AND_CATEGORY_ID, FilmCategory.class)
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
    }

    @DisplayName(QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final int maxResults = current().nextInt(32, 64);
            for (final var id = FilmCategoryId.of(0, 0); ; ) {
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL, FilmCategory.class)
                                .setParameter(QUERY_PARAM_ID_FILM_ID_MIN, id.getFilmId())
                                .setParameter(QUERY_PARAM_ID_CATEGORY_ID_MIN_EXCLUSIVE, id.getCategoryId())
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(FilmCategory::getId));
                if (list.isEmpty()) {
                    break;
                }
                final var lastId = list.get(list.size() - 1).getId();
                id.setFilmId(lastId.getFilmId());
                id.setCategoryId(lastId.getCategoryId());
            }
        }
    }

    @DisplayName(QUERY_FIND_ALL_BY_ID_FILM_ID)
    @Nested
    class FindAllByIdFilmIdTest {

        @DisplayName("(0)empty")
        @Test
        void _Empty_0() {
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_ALL_BY_ID_FILM_ID, FilmCategory.class)
                            .setParameter(PARAMETER_ID_FILM_ID, 0)
                            .setParameter(QUERY_PARAM_ID_CATEGORY_ID_MIN_EXCLUSIVE, 0)
                            .setMaxResults(1)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isEmpty();
        }

        @DisplayName("(1003)!empty")
        @Test
        void _NotEmpty_1003() {
            final var idFilmId = 1003; // Thor: Ragnarok
            final var maxResults = current().nextInt(1, 3);
            for (final var i = new AtomicInteger(0); ; ) {
                final var idCategoryIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_ID_FILM_ID, FilmCategory.class)
                                .setParameter(PARAMETER_ID_FILM_ID, idFilmId)
                                .setParameter(QUERY_PARAM_ID_CATEGORY_ID_MIN_EXCLUSIVE, idCategoryIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .extracting(FilmCategory::getId)
                        .isSortedAccordingTo(comparing(FilmCategoryId::getCategoryId))
                        .extracting(FilmCategoryId::getFilmId)
                        .allMatch(v -> v == idFilmId);
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getId().getCategoryId());
            }
        }
    }

    @DisplayName(QUERY_FIND_ALL_BY_ID_CATEGORY_ID)
    @Nested
    class FindAllByIdCategoryIdTest {

        @DisplayName("(0)empty")
        @Test
        void _Empty_0() {
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_ALL_BY_ID_CATEGORY_ID, FilmCategory.class)
                            .setParameter(PARAMETER_ID_CATEGORY_ID, 0)
                            .setParameter(QUERY_PARAM_ID_FILM_ID_MIN_EXCLUSIVE, 0)
                            .setMaxResults(1)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isEmpty();
        }

        @DisplayName("(15)!empty")
        @Test
        void _NotEmpty_15() {
            final var idCategoryId = 15; // Sports
            final var maxResults = current().nextInt(16, 32);
            for (final var i = new AtomicInteger(0); ; ) {
                final var idFilmIdMinExclusive = i.get();
                break;
            }
        }
    }

    @DisplayName(QUERY_FIND_ALL_BY_FILM)
    @Nested
    class FindAllByFilmTest {

        @DisplayName("(0)empty")
        @Test
        void _Empty_0() {
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_ALL_BY_FILM, FilmCategory.class)
                            .setParameter(QUERY_PARAM_FILM, ofFilmId(0))
                            .setParameter(QUERY_PARAM_ID_CATEGORY_ID_MIN_EXCLUSIVE, 0)
                            .setMaxResults(1)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isEmpty();
        }

        @DisplayName("(1003)!empty")
        @Test
        void _NotEmpty_1003() {
            final var film = ofFilmId(1003); // Thor: Ragnarok
            final var maxResults = current().nextInt(1, 3);
            for (final var i = new AtomicInteger(0); ; ) {
                final var idCategoryIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_FILM, FilmCategory.class)
                                .setParameter(QUERY_PARAM_FILM, film)
                                .setParameter(QUERY_PARAM_ID_CATEGORY_ID_MIN_EXCLUSIVE, idCategoryIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(v -> v.getId().getCategoryId()))
                        .extracting(FilmCategory::getFilm)
                        .extracting(Film::getFilmId)
                        .allMatch(v -> Objects.equals(v, film.getFilmId()));
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getId().getCategoryId());
            }
        }
    }

    @DisplayName(QUERY_FIND_ALL_BY_CATEGORY)
    @Nested
    class FindAllByCategoryTest {

        @DisplayName("(0)empty")
        @Test
        void _Empty_0() {
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_ALL_BY_CATEGORY, FilmCategory.class)
                            .setParameter(QUERY_PARAM_CATEGORY, ofCategoryId(0))
                            .setParameter(QUERY_PARAM_ID_FILM_ID_MIN_EXCLUSIVE, 0)
                            .setMaxResults(1)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isEmpty();
        }

        @DisplayName("(15)!empty")
        @Test
        void _NotEmpty_15() {
            final var category = ofCategoryId(15); // Sports
            final var maxResults = current().nextInt(16, 32);
            for (final var i = new AtomicInteger(0); ; ) {
                final var idFilmIdMinExclusive = i.get();
                break;
            }
        }
    }
}
