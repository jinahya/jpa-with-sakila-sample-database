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
}
