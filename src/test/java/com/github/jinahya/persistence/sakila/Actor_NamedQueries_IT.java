package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * A class for testing named queries defined on {@link Actor} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class Actor_NamedQueries_IT
        extends _BaseEntityIT<Actor, Integer> {

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

    @DisplayName(ActorConstants.QUERY_FIND_ALL_BY_ACTOR_ID_GREATER_THAN)
    @Nested
    class FindAllByActorIdGreaterThanTest {

        @Test
        void __10() {
            final var actorIdMinExclusive = 10;
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(
                                    ActorConstants.QUERY_FIND_ALL_BY_ACTOR_ID_GREATER_THAN,
                                    Actor.class
                            )
                            .setParameter("actorIdMinExclusive", actorIdMinExclusive)
                            .getResultList()
            );
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .isSortedAccordingTo(Comparator.comparing(Actor::getActorId))
                    .extracting(Actor::getActorId)
                    .allMatch(ai -> ai > actorIdMinExclusive);
        }

        @Test
        void __10WithMaxResults() {
            final var actorIdMinExclusive = 10;
            final var maxResults = current().nextInt(8, 16);
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(
                                    ActorConstants.QUERY_FIND_ALL_BY_ACTOR_ID_GREATER_THAN,
                                    Actor.class
                            )
                            .setParameter("actorIdMinExclusive", actorIdMinExclusive)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .isSortedAccordingTo(Comparator.comparing(Actor::getActorId))
                    .extracting(Actor::getActorId)
                    .allMatch(ai -> ai > actorIdMinExclusive);
        }

        @Test
        void __() {
            final var maxResults = current().nextInt(8, 16);
            for (final var i = new AtomicInteger(0); ; ) {
                final var actorIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(
                                        ActorConstants.QUERY_FIND_ALL_BY_ACTOR_ID_GREATER_THAN,
                                        Actor.class
                                )
                                .setParameter("actorIdMinExclusive", actorIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(Comparator.comparing(Actor::getActorId))
                        .extracting(Actor::getActorId)
                        .allMatch(ai -> ai > actorIdMinExclusive);
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

    @DisplayName(ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME_ACTOR_ID_GREATER_THAN)
    @Nested
    class FindAllByLastNameActorIdGreaterThanTest {

        @Test
        void __KILMER() {
            final var lastName = "KILMER";
            final var maxResults = current().nextInt(1, 3);
            for (final var i = new AtomicInteger(0); ; ) {
                final var actorIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(
                                        ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME_ACTOR_ID_GREATER_THAN,
                                        Actor.class
                                )
                                .setParameter("lastName", lastName)
                                .setParameter("actorIdMinExclusive", actorIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                log.debug("lastName(s): {}", list.stream().map(Actor::getLastName).distinct().toList());
                log.debug("actorIds: {} >? {}", list.stream().map(Actor::getActorId).distinct().toList(), actorIdMinExclusive);
                assertThat(list)
                        .doesNotContainNull()
                        .allSatisfy(e -> {
                            assertThat(e.getLastName()).isEqualTo(lastName);
                            assertThat(e.getActorId()).isGreaterThan(actorIdMinExclusive);
                        });
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getActorId());
            }
        }
    }
}
