package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.NATIVE_QUERY_SELECT_ALL_KEYSET;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.NATIVE_QUERY_SELECT_ALL_ROWSET;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.NATIVE_QUERY_SELECT_BY_FILM_ID_AND_CATEGORY_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FilmCategory_NamedNativeQueries_IT
        extends __PersistenceIT {

    FilmCategory_NamedNativeQueries_IT() {
        super();
    }

    @DisplayName(NATIVE_QUERY_SELECT_BY_FILM_ID_AND_CATEGORY_ID)
    @Nested
    class SelectByFilmIdAndCategoryIdTest {

        @DisplayName("(0, 0)NoResultException")
        @Test
        void _NoResultException_00() {
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(NATIVE_QUERY_SELECT_BY_FILM_ID_AND_CATEGORY_ID)
                                    .setParameter(1, 0) // film_id
                                    .setParameter(2, 0) // category_id
                                    .getSingleResult() // NoResultException

                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("(1, 6)!null")
        @Test
        void _NotNull_16() {
            final var filmId = 1;
            final var categoryId = 6;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(NATIVE_QUERY_SELECT_BY_FILM_ID_AND_CATEGORY_ID, FilmCategory.class)
                            .setParameter(1, filmId) // film_id
                            .setParameter(2, categoryId) // category_id
                            .getSingleResult()

            );
            assertThat(found)
                    .isNotNull()
                    .extracting(FilmCategory::getId)
                    .isNotNull()
                    .satisfies(v -> {
                        assertThat(v.getFilmId()).isEqualTo(filmId);
                        assertThat(v.getCategoryId()).isEqualTo(categoryId);
                    });
        }
    }

    @DisplayName(NATIVE_QUERY_SELECT_ALL_ROWSET)
    @Nested
    class SelectAllRowsetTest {

        @Test
        void __() {
            final var limit = 128;
            for (final var offset = new LongAdder(); ; ) {
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(NATIVE_QUERY_SELECT_ALL_ROWSET, FilmCategory.class)
                                .setParameter(1, offset.intValue())                                          // OFFSET ?
                                .setParameter(2, limit)                                                            // ,?
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(limit)
                        .extracting(FilmCategory::getId)
                        .isSorted();
                if (list.isEmpty()) {
                    break;
                }
                offset.add(list.size());
            }
        }
    }

    @DisplayName(NATIVE_QUERY_SELECT_ALL_KEYSET)
    @Nested
    class SelectAllKeysetTest {

        @Test
        void __() {
            final var filmIdMin = new AtomicInteger();
            final var categoryIdMinExclusive = new AtomicInteger();
            final var limit = 128;
            while (true) {
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(NATIVE_QUERY_SELECT_ALL_KEYSET, FilmCategory.class)
                                .setParameter(1, filmIdMin.get())                         // WHERE (film_id = ?
                                .setParameter(2, categoryIdMinExclusive.get())            //        AND category_id > ?)
                                .setParameter(3, filmIdMin.get())                         //    OR film_id > ?
                                .setParameter(4, limit)                                   // LIMIT ?
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(limit)
                        .extracting(FilmCategory::getId)
                        .isSorted()
                        .allMatch(id -> {
                            final var filmId = id.getFilmId();
                            final var categoryId = id.getCategoryId();
                            return (filmId > filmIdMin.get() && categoryId > categoryIdMinExclusive.get())
                                   || filmId > filmIdMin.get();
                        });
                if (list.isEmpty()) {
                    break;
                }
                final var last = list.get(list.size() - 1);
                filmIdMin.set(last.getId().getFilmId());
                categoryIdMinExclusive.set(last.getId().getCategoryId());
            }
        }
    }
}
