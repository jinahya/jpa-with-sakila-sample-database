package com.github.jinahya.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FilmListQueriesIT
        extends __BaseEntityIT<FilmList, FilmListId> {

    FilmListQueriesIT() {
        super(FilmList.class, FilmListId.class);
    }

    @DisplayName("findByFid")
    @Test
    void findByFid__() {
        final var fidList = applyEntityManager(
                em -> em.createQuery(
                        "SELECT DISTINCT e.fid FROM FilmList AS e", Integer.class
                ).getResultList());
        for (final var fid : fidList) {
            final var found = applyEntityManager(em -> FilmListQueries.findByFid(em, fid));
            assertThat(found).hasValueSatisfying(v -> {
                assertThat(v.getFid()).isEqualTo(fid);
            });
        }
    }
}
