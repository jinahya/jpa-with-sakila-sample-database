package com.github.jinahya.persistence;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FilmListCriteriaIT
        extends __BaseEntityIT<FilmList, FilmListId> {

    FilmListCriteriaIT() {
        super(FilmList.class, FilmListId.class);
    }

    @DisplayName("findByFid")
    @Test
    void findByFid__() {
        List<Integer> fidList = applyEntityManager(
                em -> em.createQuery(
                        "SELECT DISTINCT e.fid FROM FilmList AS e", Integer.class
                ).getResultList());
        for (var fid : fidList) {
            final var found = FilmListCriteria.findByFid(entityManager, fid);
            assertThat(found).hasValueSatisfying(v -> {
                assertThat(v.getFid()).isEqualTo(fid);
            });
        }
    }

    @Inject
    private EntityManager entityManager;
}
