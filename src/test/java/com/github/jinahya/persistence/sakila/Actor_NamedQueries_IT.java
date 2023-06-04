package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * A class for testing named queries defined on {@link Actor} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class Actor_NamedQueries_IT
        extends _BaseEntityIT<Actor, Integer> {

    private static final Logger log = getLogger(lookup().lookupClass());

    Actor_NamedQueries_IT() {
        super(Actor.class, Integer.class);
    }

    @DisplayName(ActorConstants.QUERY_FIND_BY_ACTOR_ID)
    @Nested
    class FindByActorIdTest {

        @Test
        void _NoResultException_0() {
            final var actorId = 0;
            assertThatThrownBy(() -> applyEntityManager(
                    em -> em.createNamedQuery(
                                    ActorConstants.QUERY_FIND_BY_ACTOR_ID,
                                    Actor.class
                            )
                            .setParameter("actorId", actorId)
                            .getSingleResult() // NoResultException
            )).isInstanceOf(NoResultException.class);
        }

        @Test
        void __1() {
            final var actorId = 1;
            final var result = applyEntityManager(
                    em -> em.createNamedQuery(
                                    ActorConstants.QUERY_FIND_BY_ACTOR_ID,
                                    Actor.class
                            )
                            .setParameter(Actor_.actorId.getName(), actorId)
                            .getSingleResult()
            );
            assertThat(result)
                    .isNotNull()
                    .extracting(Actor::getActorId)
                    .isEqualTo(actorId);
        }
    }

    @DisplayName(ActorConstants.QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(ActorConstants.QUERY_FIND_ALL)
                            .getResultList()
            );
            assertThat(found)
                    .isNotEmpty()
                    .doesNotContainNull();
        }

        @Test
        void __WithMaxResults() {
            final var maxResults = current().nextInt(8, 16);
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(ActorConstants.QUERY_FIND_ALL)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(found)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @DisplayName(ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME)
    @Nested
    class FindAllByLastNameTest {

        @Test
        void __KILMER() {
            final var lastName = "KILMER";
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(
                                    ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME,
                                    Actor.class
                            )
                            .setParameter("lastName", lastName)
                            .getResultList()
            );
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .extracting(Actor::getLastName)
                    .containsOnly(lastName);
        }

        @Test
        void __KILMERWithMaxResults() {
            final var lastName = "KILMER";
            final var maxResults = current().nextInt(1, 5);
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(
                                    ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME,
                                    Actor.class
                            )
                            .setParameter("lastName", lastName)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .extracting(Actor::getLastName)
                    .containsOnly(lastName);
        }
    }
}
