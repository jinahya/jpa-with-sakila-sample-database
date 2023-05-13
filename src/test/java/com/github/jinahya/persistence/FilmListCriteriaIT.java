package com.github.jinahya.persistence;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FilmListCriteriaIT
        extends __BaseEntityIT<FilmList, FilmListId> {

    FilmListCriteriaIT() {
        super(FilmList.class, FilmListId.class);
    }

    @DisplayName("findByFid(, 1)")
    @Test
    void findByFid_NotEmpty_1() {
        final var fid = 1;
        final var found = FilmListCriteria.findByFid(entityManager, fid);
        assertThat(found).hasValueSatisfying(v -> {
            assertThat(v.getFid()).isEqualTo(fid);
        });
    }

    @Inject
    private EntityManager entityManager;
}
