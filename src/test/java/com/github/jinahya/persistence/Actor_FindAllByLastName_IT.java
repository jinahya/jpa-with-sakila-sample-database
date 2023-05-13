package com.github.jinahya.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Actor_FindAllByLastName_IT
        extends _BaseEntityIT<Actor, Integer> {

    Actor_FindAllByLastName_IT() {
        super(Actor.class, Integer.class);
    }

    @DisplayName("Actor_findAllByLastName(GUINESS)")
    @Test
    void findAllByLastName_NotEmpty_GUINESS() {
        final var lastName = "GUINESS";
        final List<Actor> found = applyEntityManager(em -> {
            final var query = em.createNamedQuery("Actor_findAllByLastName", Actor.class);
            query.setParameter(Actor_.LAST_NAME, lastName);
            return query.getResultList();
        });
        assertThat(found)
                .isNotEmpty()
                .extracting(Actor::getLastName)
                .containsOnly(lastName);
    }
}
