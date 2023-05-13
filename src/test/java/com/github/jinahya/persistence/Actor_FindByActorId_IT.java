package com.github.jinahya.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Actor_FindByActorId_IT
        extends _BaseEntityIT<Actor, Integer> {

    Actor_FindByActorId_IT() {
        super(Actor.class, Integer.class);
    }

    @DisplayName("Actor_findByActorId(1)")
    @Test
    void findByActorId_NotNull_1() {
        final var actorId = 1;
        final Actor found = applyEntityManager(em -> {
            final var query = em.createNamedQuery("Actor_findByActorId", Actor.class);
            query.setParameter(Actor_.ACTOR_ID, actorId);
            return query.getSingleResult();
        });
        assertThat(found)
                .isNotNull()
                .extracting(Actor::getActorId)
                .isEqualTo(actorId);
    }
}
