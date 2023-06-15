package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.PARAMETER_ID;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_BY_ID;
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
}
