package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.Actor;
import com.github.jinahya.persistence.sakila.ActorConstants;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ActorService_IT
        extends _BaseEntityServiceIT<ActorService, Actor, Integer> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    ActorService_IT() {
        super(ActorService.class, Actor.class, Integer.class);
    }

    @DisplayName(ActorConstants.QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @DisplayName("findAll(null)")
        @Test
        void __() {
            final var list = applyServiceInstance(s -> s.findAll(null));
            assertThat(list)
                    .isNotNull()
                    .isNotEmpty();
        }

        @DisplayName("findAll(!null)")
        @Test
        void __WithMaxResults() {
            final var maxResults = ThreadLocalRandom.current().nextInt(1, 8);
            final var list = applyServiceInstance(s -> s.findAll(maxResults));
            assertThat(list)
                    .isNotNull()
                    .isNotEmpty()
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @DisplayName(ActorConstants.QUERY_FIND_BY_ACTOR_ID)
    @Nested
    class FindByActorIdTest {

        @DisplayName("findByActorId(0) -> ConstraintViolation; @Positive actorId")
        @Test
        void _ConstraintViolationException_0() {
            assertThatThrownBy(() -> applyServiceInstance(s -> s.findByActorId(0)))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @DisplayName("findByActorId(1)")
        @Test
        void _NotEmpty_1() {
            // GIVEN
            final var actorId = 1;
            // WHEN
            final var found = applyServiceInstance(s -> s.findByActorId(1));
            // THEN
            assertThat(found)
                    .isNotNull()
                    .hasValueSatisfying(v -> {
                        assertThat(v.getActorId()).isEqualTo(actorId);
                    });
        }
    }

    @DisplayName(ActorConstants.QUERY_FIND_ALL_BY_ACTOR_ID_GREATER_THAN)
    @Nested
    class FindAllByActorIdGreaterThanTest {

        @DisplayName("findAllByActorIdGreaterThan(0, null)")
        @Test
        void __0() {
            // GIVEN
            final var actorIdMinExclusive = 0;
            // WHEN
            final var list = applyServiceInstance(s -> s.findAllByActorIdGreaterThan(actorIdMinExclusive, null));
            // THEN
            assertThat(list)
                    .isNotEmpty()
                    .extracting(Actor::getActorId)
                    .allSatisfy(ai -> {
                        assertThat(ai).isPositive();
                    });
        }

        @DisplayName("findAllByActorIdGreaterThan(positive, !null)")
        @Test
        void __WithMaxResults() {
            // GIVEN
            final var actorIdMinExclusive = ThreadLocalRandom.current().nextInt(1, 8);
            final var maxResults = ThreadLocalRandom.current().nextInt(1, 8);
            // WHEN
            final var list = applyServiceInstance(s -> s.findAllByActorIdGreaterThan(actorIdMinExclusive, maxResults));
            // THEN
            assertThat(list)
                    .isNotEmpty()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .extracting(Actor::getActorId)
                    .allSatisfy(ai -> {
                        assertThat(ai).isGreaterThan(actorIdMinExclusive);
                    });
        }
    }

    @DisplayName(ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME)
    @Nested
    class FindAllByLastNameTest {

        @DisplayName("findAllByLastName(\"KILMER\", null)")
        @Test
        void __KILMER() {
            // GIVEN
            final var lastName = "KILMER";
            // WHEN
            final var list = applyServiceInstance(s -> s.findAllByLastName(lastName, null));
            // THEN
            assertThat(list)
                    .isNotEmpty()
                    .extracting(Actor::getLastName)
                    .containsOnly(lastName);
        }

        @DisplayName("findAllByLastName(\"KILMER\", !null)")
        @Test
        void __KILMERWithMaxResults() {
            // GIVEN
            final var lastName = "KILMER";
            final var maxResults = ThreadLocalRandom.current().nextInt(1, 3);
            // WHEN
            final var list = applyServiceInstance(s -> s.findAllByLastName(lastName, maxResults));
            // THEN
            assertThat(list)
                    .isNotEmpty()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .extracting(Actor::getLastName)
                    .containsOnly(lastName);
        }
    }

    @DisplayName(ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME_ACTOR_ID_GREATER_THAN)
    @Nested
    class FindAllByLastNameActorIdGreaterThanTest {

        @DisplayName("findAllByLastName(\"KILMER\", null)")
        @Test
        void __KILMER() {
            // GIVEN
            final var lastName = "KILMER";
            final var actorIdMinExclusive = ThreadLocalRandom.current().nextInt(23, 163);
            // WHEN
            final var list = applyServiceInstance(
                    s -> s.findAllByLastNameActorIdGreaterThan(lastName, actorIdMinExclusive, null)
            );
            // THEN
            assertThat(list)
                    .isNotEmpty()
                    .allSatisfy(a -> {
                        assertThat(a.getLastName()).isEqualTo(lastName);
                        assertThat(a.getActorId()).isGreaterThan(actorIdMinExclusive);
                    });
        }

        @DisplayName("findAllByLastName(\"KILMER\", !null)")
        @Test
        void __KILMERWithMaxResults() {
            // GIVEN
            // GIVEN
            final var lastName = "KILMER";
            final var actorIdMinExclusive = ThreadLocalRandom.current().nextInt(23, 163);
            final var maxResults = ThreadLocalRandom.current().nextInt(1, 3);
            // WHEN
            final var list = applyServiceInstance(
                    s -> s.findAllByLastNameActorIdGreaterThan(lastName, actorIdMinExclusive, maxResults)
            );
            // THEN
            assertThat(list)
                    .isNotEmpty()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .allSatisfy(a -> {
                        assertThat(a.getLastName()).isEqualTo(lastName);
                        assertThat(a.getActorId()).isGreaterThan(actorIdMinExclusive);
                    });
        }
    }
}
