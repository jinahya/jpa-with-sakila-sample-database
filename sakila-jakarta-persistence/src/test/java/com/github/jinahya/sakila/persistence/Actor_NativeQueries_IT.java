package com.github.jinahya.sakila.persistence;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Actor_NativeQueries_IT
        extends _BaseEntityIT<Actor, Integer> {

    Actor_NativeQueries_IT() {
        super(Actor.class, Integer.class);
    }

    @Nested
    class SelectByActorIdTest {

        @Test
        void _NoResultException_0() {
            final var sqlString = """
                    SELECT *
                    FROM actor
                    WHERE actor_id = ?""";
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNativeQuery(sqlString, Actor.class)
                                    .setParameter(1, 0)
                                    .getSingleResult()
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @Test
        void _NotNull_1() {
            final var sqlString = """
                    SELECT *
                    FROM actor
                    WHERE actor_id = ?""";
            final var actorId = 1;
            final var found = (Actor) applyEntityManager(
                    em -> em.createNativeQuery(sqlString, Actor.class)
                            .setParameter(1, actorId)
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(Actor::getActorId)
                    .isEqualTo(actorId);
        }
    }

    @Nested
    class SelectAllClass {

        @Test
        void __() {
            final var sqlString = """
                    SELECT *
                    FROM actor
                    ORDER BY actor_id ASC
                    LIMIT ?,?""";
            final var limit = 64;
            for (var i = 0; ; ) {
                final var offset = i;
                @SuppressWarnings({"unchecked"})
                final var list = (List<Actor>) applyEntityManager(
                        em -> em.createNativeQuery(sqlString, Actor.class)
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
