package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence.Actor;
import jakarta.validation.ConstraintViolationException;
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

class ActorService_IT
        extends _BaseEntityServiceIT<ActorService, Actor, Integer> {

    private static final Logger log = getLogger(lookup().lookupClass());

    ActorService_IT() {
        super(ActorService.class, Actor.class, Integer.class);
    }

    @DisplayName("findByActorId")
    @Nested
    class FindByActorIdTest {

        @DisplayName("(0) -> ConstraintViolation; @Positive")
        @Test
        void _ConstraintViolationException_0() {
            assertThatThrownBy(
                    () -> applyServiceInstance(s -> s.findByActorId(0))
            ).isInstanceOf(ConstraintViolationException.class);
        }

        @DisplayName("(1)!empty")
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

    @DisplayName("findAll")
    @Nested
    class FindAllTest {

        @DisplayName("first page")
        @Test
        void __FirstPage() {
            final var maxResults = current().nextInt(1, 8);
            final var list = applyServiceInstance(s -> s.findAll(maxResults));
            assertThat(list)
                    .isNotNull()
                    .isNotEmpty()
                    .hasSizeLessThanOrEqualTo(maxResults);
        }

        @DisplayName("all pages")
        @Test
        void __AllPages() {
            final var maxResults = current().nextInt(1, 8);
            for (final var i = new AtomicInteger(0); ; ) {
                final var actorIdMinExclusive = i.get();
                final var list = applyServiceInstance(s -> s.findAll(actorIdMinExclusive, maxResults));
                assertThat(list)
                        .isNotNull()
                        // not asserting the emptiness; the last page may be empty.
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(Actor::getActorId));
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getActorId());
            }
        }
    }

    @DisplayName("findAllByLastName")
    @Nested
    class FindAllByLastNameTest {

        @DisplayName("(, 'KILMER') first page")
        @Test
        void __KILMERFirstPage() {
            // GIVEN
            final var maxResults = current().nextInt(2, 4);
            final var lastName = "KILMER";
            // WHEN
            final var list = applyServiceInstance(s -> s.findAllByLastName(maxResults, lastName));
            // THEN
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .extracting(Actor::getLastName)
                    .containsOnly(lastName);
        }

        @DisplayName("(, 'KILMER') all pages")
        @Test
        void __KILMERAllPages() {
            // GIVEN
            final var maxResults = current().nextInt(1, 3);
            final var lastName = "KILMER";
            for (final var i = new AtomicInteger(0); ; ) {
                final var actorIdMinExclusive = i.get();
                // WHEN
                final var list = applyServiceInstance(
                        s -> s.findAllByLastName(actorIdMinExclusive, maxResults, lastName)
                );
                // THEN
                assertThat(list)
                        .isNotNull()
                        // not asserting the emptiness; the last page may be empty.
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .extracting(Actor::getLastName)
                        .doesNotContainNull()
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
