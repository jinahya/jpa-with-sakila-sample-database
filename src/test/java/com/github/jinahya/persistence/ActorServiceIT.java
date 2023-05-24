package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static com.github.jinahya.persistence.ActorService.findAllByLastName;
import static com.github.jinahya.persistence.ActorService.findAllByLastNameLimit;
import static com.github.jinahya.persistence.ActorService.findAllByLastNamePage;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ActorServiceIT
        extends __BaseEntityIT<Actor, Integer> {

    ActorServiceIT() {
        super(Actor.class, Integer.class);
    }

    @DisplayName("findByActorId")
    @Nested
    class FindAyActorIdTest {

        @DisplayName("findByActorId(1)")
        @Test
        void __1() {
            // GIVEN
            final var actorId = 1;
            final var expectedFirstName = "PENELOPE";
            final var expectedLastName = "GUINESS";
            // WHEN
            final var found = applyEntityManager(em -> ActorService.findByActorId(em, actorId));
            // THEN
            assertThat(found).hasValueSatisfying(v -> {
                assertThat(v.getActorId()).isEqualTo(actorId);
                assertThat(v.getFirstName()).isEqualTo(expectedFirstName);
                assertThat(v.getLastName()).isEqualTo(expectedLastName);
            });
        }

        @DisplayName("findByActorId(2)")
        @Test
        void __2() {
            // GIVEN
            final var actorId = 2;
            final var expectedFirstName = "NICK";
            final var expectedLastName = "WAHLBERG";
            // TODO: implement!
        }
    }

    @DisplayName("findAllByLastName")
    @Nested
    class FindAllByLastNameTest {

        @DisplayName("findAllByLastName(KILMER)")
        @Test
        void __GUINESS() {
            // GIVEN
            final var lastName = "KILMER";
            final var maxResults = current().nextInt() & Integer.MAX_VALUE;
            // WHEN
            final var found = applyEntityManager(em -> findAllByLastName(em, lastName, maxResults));
            // THEN
            assertThat(found)
                    .isNotEmpty()
                    .hasSizeLessThanOrEqualTo(maxResults)
                    .extracting(Actor::getLastName)
                    .containsOnly(lastName);
        }

        @DisplayName("findAllByLastName(TEMPLE)")
        @Test
        void __WAHLBERG() {
            // GIVEN
            final var lastName = "TEMPLE";
            final var maxResults = current().nextInt() & Integer.MAX_VALUE;
            // TODO: implement!
        }
    }

    @DisplayName("findAllByLastNameLimit")
    @Nested
    class FindAllByLastNameLimitTest {

        @DisplayName("findAllByLastNameLimit(KILMER)")
        @Test
        void __KILMER() {
            // GIVEN
            final var lastName = "KILMER";
            final var offset = current().nextInt(3);
            final var rowCount = current().nextInt() & Integer.MAX_VALUE;
            // WHEN
            final var found = applyEntityManager(
                    em -> findAllByLastNameLimit(em, lastName, offset, rowCount)
            );
            // THEN
            assertThat(found)
                    .isNotEmpty()
                    .hasSizeLessThanOrEqualTo(rowCount)
                    .extracting(Actor::getLastName)
                    .containsOnly(lastName);
        }

        @DisplayName("findAllByLastNameLimit(TEMPLE)")
        @Test
        void __TEMPLE() {
            // GIVEN
            final var lastName = "TEMPLE";
            final var offset = current().nextInt(2);
            final var rowCount = (current().nextInt() >>> 1) | 0x01;
            // TODO: implement!
        }
    }

    @DisplayName("findAllByLastNamePage")
    @Nested
    class FindAllByLastNamePageTest {

        @DisplayName("findAllByLastNamePage(KILMER)")
        @Test
        void __KILMER() {
            // GIVEN
            final var lastName = "KILMER";
            final var size = current().nextInt(1, 4);
            IntStream.iterate(0, p -> p + 1)
                    .mapToObj(p -> {
                        // WHEN
                        return applyEntityManager(em -> findAllByLastNamePage(em, lastName, p, size));
                    })
                    .takeWhile(f -> !f.isEmpty())
                    .forEach(f -> {
                        // THEN
                        assertThat(f)
                                .hasSizeLessThanOrEqualTo(size)
                                .extracting(Actor::getLastName)
                                .allMatch(lastName::equals);
                    });
        }

        @DisplayName("findAllByLastNamePage(TEMPLE)")
        @Test
        void __TEMPLE() {
            // GIVEN
            final var lastName = "TEMPLE";
            final var size = current().nextInt(1, 3);
            // TODO: implement!
        }
    }
}
