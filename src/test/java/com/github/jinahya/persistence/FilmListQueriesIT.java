package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class FilmListQueriesIT
        extends __BaseEntityIT<FilmList, FilmListId> {

    FilmListQueriesIT() {
        super(FilmList.class, FilmListId.class);
    }

    @Disabled
    @DisplayName("findByFid")
    @Test
    void findByFid__() {
        final var fidList = applyEntityManager(
                em -> em.createQuery(
                        "SELECT DISTINCT e.fid FROM FilmList AS e", Integer.class
                ).getResultList());
        for (final var fid : fidList) {
            log.debug("fid: {}", fid);
            final var found = applyEntityManager(em -> FilmListQueries.findByFid(em, fid));
            assertThat(found).hasValueSatisfying(v -> {
                assertThat(v.getFid()).isEqualTo(fid);
            });
        }
    }
}
