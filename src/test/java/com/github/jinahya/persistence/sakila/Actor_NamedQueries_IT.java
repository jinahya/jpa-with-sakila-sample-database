package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.jinahya.persistence.sakila.ActorConstants.QUERY_FIND_ALL;
import static com.github.jinahya.persistence.sakila.ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME;
import static com.github.jinahya.persistence.sakila.ActorConstants.QUERY_FIND_BY_ACTOR_ID;
import static com.github.jinahya.persistence.sakila.ActorConstants.QUERY_PARAM_ACTOR_ID;
import static com.github.jinahya.persistence.sakila.ActorConstants.QUERY_PARAM_ACTOR_ID_MIN_EXCLUSIVE;
import static com.github.jinahya.persistence.sakila.ActorConstants.QUERY_PARAM_LAST_NAME;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Comparator.comparing;
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

    @DisplayName(QUERY_FIND_BY_ACTOR_ID)
    @Nested
    class FindByActorIdTest {

        @Test
        void _NoResultException_0() {
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(QUERY_FIND_BY_ACTOR_ID, Actor.class)
                                    .setParameter(QUERY_PARAM_ACTOR_ID, 0)
                                    .getSingleResult() // NoResultException
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @Test
        void __1() {
            final var actorId = 1;
            final var result = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_BY_ACTOR_ID, Actor.class)
                            .setParameter(QUERY_PARAM_ACTOR_ID, actorId)
                            .getSingleResult()
            );
            assertThat(result)
                    .isNotNull()
                    .extracting(Actor::getActorId)
                    .isEqualTo(actorId);
        }
    }

    @DisplayName(QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var maxResults = ThreadLocalRandom.current().nextInt(32, 64);
            for (final var i = new AtomicInteger(0); ; ) {
                final var actorIdMinExclusive = i.get();
                final List<Actor> list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL)
                                .setParameter(QUERY_PARAM_ACTOR_ID_MIN_EXCLUSIVE, actorIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(Actor::getActorId))
                ;
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getActorId());
            }
        }
    }

    @DisplayName(QUERY_FIND_ALL_BY_LAST_NAME)
    @Nested
    class FindAllByLastNameTest {

        @Test
        void __KILMER() {
            final var lastName = "KILMER";
            final var maxResults = ThreadLocalRandom.current().nextInt(1, 3);
            for (final var i = new AtomicInteger(0); ; ) {
                final var actorIdMinExclusive = i.get();
                final List<Actor> list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_LAST_NAME)
                                .setParameter(QUERY_PARAM_LAST_NAME, lastName)
                                .setParameter(QUERY_PARAM_ACTOR_ID_MIN_EXCLUSIVE, actorIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(Actor::getActorId))
                        .extracting(Actor::getLastName)
                        .allMatch(v -> v.equals(lastName));
                ;
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getActorId());
            }
        }
    }
}
