package com.github.jinahya.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Actor_NamedQueries_IT
        extends _BaseEntityIT<Actor, Integer> {

    Actor_NamedQueries_IT() {
        super(Actor.class, Integer.class);
    }

    @DisplayName("Actor_findAll")
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var found = applyEntityManager(
                    em -> em.createNamedQuery("Actor_findAll").getResultList()
            );
            assertThat(found).isNotEmpty().doesNotContainNull();
        }
    }

    @DisplayName("Actor_findByActorId")
    @Nested
    class FindByActorIdTest {

        @DisplayName("Actor_findByActorId(1)")
        @Test
        void _NotNull_1() {
            final var actorId = 1;
            final var found = applyEntityManager(em -> {
                final var query = em.createNamedQuery("Actor_findByActorId", Actor.class);
                query.setParameter(Actor_.actorId.getName(), actorId);
                return query.getSingleResult();
            });
            assertThat(found)
                    .isNotNull()
                    .extracting(Actor::getActorId)
                    .isEqualTo(actorId);
        }

        @DisplayName("Actor_findByActorId(234)")
        @Test
        void _NotNull_234() {
            final var actorId = 233;
            final var expectedFirstName = "敏郎";
            final var expectedLastName = "三船";
            // TODO: Implement!
        }
    }

    @DisplayName("Actor_findAllByLastName")
    @Nested
    class FindAllByLastNameTest {

        @DisplayName("Actor_findAllByLastName(GUINESS)")
        @Test
        void findAllByLastName_NotEmpty_GUINESS() {
            final var lastName = "GUINESS";
            final var found = applyEntityManager(em -> {
                final var query = em.createNamedQuery("Actor_findAllByLastName", Actor.class);
                query.setParameter(Actor_.lastName.getName(), lastName);
                return query.getResultList();
            });
            assertThat(found)
                    .isNotEmpty()
                    .extracting(Actor::getLastName)
                    .containsOnly(lastName);
        }

        @DisplayName("Actor_findAllByLastName(안)")
        @Test
        void findAllByLastName_NotEmpty_안() {
            final var lastName = "안";
            final var oneOfExpectedFirstName = "성기";
            // TODO: implement!
        }
    }
}
