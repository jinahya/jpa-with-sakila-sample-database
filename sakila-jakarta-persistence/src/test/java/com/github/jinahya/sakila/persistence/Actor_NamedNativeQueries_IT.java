package com.github.jinahya.sakila.persistence;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Actor_NamedNativeQueries_IT
        extends _BaseEntityIT<Actor, Integer> {

    Actor_NamedNativeQueries_IT() {
        super(Actor.class, Integer.class);
    }

    @DisplayName(ActorConstants.NATIVE_QUERY_SELECT_BY_ACTOR_ID)
    @Nested
    class SelectByActorIdTest {

        @Test
        void _NoResultException_0() {
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(ActorConstants.NATIVE_QUERY_SELECT_BY_ACTOR_ID, Actor.class)
                                    .setParameter(1, 0)
                                    .getSingleResult()
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @Test
        void _NotNull_1() {
            final var actorId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(ActorConstants.NATIVE_QUERY_SELECT_BY_ACTOR_ID, Actor.class)
                            .setParameter(1, actorId)
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(Actor::getActorId)
                    .isEqualTo(actorId);
        }
    }

    @DisplayName(ActorConstants.NATIVE_QUERY_SELECT_ALL)
    @Nested
    class SelectAllClass {

        @Test
        void __() {
            final var limit = 64;
            for (var i = 0; ; ) {
                final var offset = i;
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(ActorConstants.NATIVE_QUERY_SELECT_ALL, Actor.class)
                                .setParameter(1, offset)
                                .setParameter(2, limit)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(limit)
                        .isSortedAccordingTo(comparing(Actor::getActorId));
                if (list.isEmpty()) {
                    break;
                }
                i += list.size();
            }
        }
    }
}
