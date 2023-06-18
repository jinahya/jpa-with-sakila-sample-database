package com.github.jinahya.sakila.persistence;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Comparator.comparing;
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

        @DisplayName("(0)NoResultException")
        @Test
        void _NoResultException_0() {
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(ActorConstants.QUERY_FIND_BY_ACTOR_ID, Actor.class)
                                    .setParameter(ActorConstants.PARAMETER_ACTOR_ID, 0)
                                    .getSingleResult() // NoResultException
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("(1)!null")
        @Test
        void _NotNull_1() {
            final var actorId = 1;
            final var result = applyEntityManager(
                    em -> em.createNamedQuery(ActorConstants.QUERY_FIND_BY_ACTOR_ID, Actor.class)
                            .setParameter(ActorConstants.PARAMETER_ACTOR_ID, actorId)
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

        @DisplayName("first page")
        @Test
        void __firstPage() {
            final var actorIdMinExclusive = 0;
            final var maxResults = current().nextInt(32, 64);
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(ActorConstants.QUERY_FIND_ALL, Actor.class)
                            .setParameter(ActorConstants.PARAMETER_ACTOR_ID_MIN_EXCLUSIVE, actorIdMinExclusive)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .extracting(Actor::getActorId)
                    .allMatch(v -> v > actorIdMinExclusive)
                    .isSorted();
        }

        @DisplayName("all pages")
        @Test
        void __() {
            final var maxResults = current().nextInt(32, 64);
            for (final var i = new AtomicInteger(0); ; ) {
                final var actorIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(ActorConstants.QUERY_FIND_ALL, Actor.class)
                                .setParameter(ActorConstants.PARAMETER_ACTOR_ID_MIN_EXCLUSIVE, actorIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        // not asserting the emptiness; the last page may be empty.
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .extracting(Actor::getActorId)
                        .allMatch(v -> v > actorIdMinExclusive)
                        .isSorted();
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getActorId());
            }
        }
    }

    @DisplayName(ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME)
    @Nested
    class FindAllByLastNameTest {

        @DisplayName(("'KILMER'"))
        @Test
        void __KILMER() {
            final var lastName = "KILMER";
            final var maxResults = current().nextInt(2, 4);
            for (final var i = new AtomicInteger(0); ; ) {
                final var actorIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME, Actor.class)
                                .setParameter(ActorConstants.PARAMETER_LAST_NAME, lastName)
                                .setParameter(ActorConstants.PARAMETER_ACTOR_ID_MIN_EXCLUSIVE, actorIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        // not asserting the emptiness; the last page may be empty.
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(Actor::getActorId))
                        .extracting(Actor::getLastName)
                        // not using the containsOnly; doesn't work with an empty list
                        .allMatch(v -> v.equals(lastName));
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getActorId());
            }
        }

        @DisplayName(("'KILMER' fetching films"))
        @Test
        void __KILMERFetchingFilms() {
            final var lastName = "KILMER";
            final var maxResults = current().nextInt(2, 4);
            for (final var i = new AtomicInteger(0); ; ) {
                final var actorIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME, Actor.class)
                                .setParameter(ActorConstants.PARAMETER_LAST_NAME, lastName)
                                .setParameter(ActorConstants.PARAMETER_ACTOR_ID_MIN_EXCLUSIVE, actorIdMinExclusive)
                                .setMaxResults(maxResults)
                                .setHint(_PersistenceConstants.PERSISTENCE_FETCHGRAPH, em.createEntityGraph(ActorConstants.ENTITY_GRAPH_FILMS))
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        // not asserting the emptiness; the last page may be empty.
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(Actor::getActorId))
                        .allSatisfy(e -> {
                            assertThat(e.getFilms())
                                    .isNotNull()
                                    .doesNotContainNull()
                                    .allSatisfy(f -> {
                                        log.debug("film: {}", f);
                                    });
                        })
                        .extracting(Actor::getLastName)
                        // not using the containsOnly; doesn't work with an empty list
                        .allMatch(v -> v.equals(lastName));
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getActorId());
            }
        }
    }
}
