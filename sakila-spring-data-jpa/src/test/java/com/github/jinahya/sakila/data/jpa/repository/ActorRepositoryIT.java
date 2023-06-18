package com.github.jinahya.sakila.data.jpa.repository;

import com.github.jinahya.sakila.persistence.Actor;
import com.github.jinahya.sakila.persistence.Actor_;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Objects;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Comparator.comparing;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

class ActorRepositoryIT
        extends _BaseEntityRepositoryIT<ActorRepository, Actor, Integer> {

    private static final Logger log = getLogger(lookup().lookupClass());

    ActorRepositoryIT() {
        super(ActorRepository.class);
    }

    @DisplayName("findById")
    @Nested
    class FindByIdTest {

        @Test
        void _Empty_0() {
            final var found = applyRepositoryInstance(r -> r.findById(0));
            assertThat(found).isEmpty();
        }

        @Test
        void _NotEmpty_1() {
            final var actorId = 1;
            final var found = applyRepositoryInstance(r -> r.findById(actorId));
            assertThat(found).hasValueSatisfying(v -> {
                assertThat(v.getActorId()).isEqualTo(actorId);
            });
        }
    }

    @DisplayName("findAll")
    @Nested
    class FindAllTest {

        @DisplayName("findAll()")
        @Test
        void __() {
            final var list = applyRepositoryInstance(ListCrudRepository::findAll);
            assertThat(list).isNotEmpty();
        }

        @DisplayName("findAll(Sort)")
        @Test
        void __WithSort() {
            final var sort = Sort.by(Sort.Order.asc(Actor_.actorId.getName()));
            final var list = applyRepositoryInstance(r -> r.findAll(sort));
            assertThat(list)
                    .isNotEmpty()
                    .isSortedAccordingTo(comparing(Actor::getActorId));
        }

        @DisplayName("findAll(Pageable)")
        @Test
        void __WithPageable() {
            final var size = 64;
            final var sort = Sort.by(Sort.Order.asc(Actor_.actorId.getName()));
            for (var p = 0; ; p++) {
                final var pageable = PageRequest.of(p, size, sort);
                final var page = applyRepositoryInstance(r -> r.findAll(pageable));
                assertThat(page.getSize()).isLessThanOrEqualTo(size);
                assertThat(page.getNumber()).isEqualTo(pageable.getPageNumber());
                assertThat(page.getContent()).isSortedAccordingTo(comparing(Actor::getActorId));
                if (page.isEmpty()) {
                    break;
                }
            }
        }

        @DisplayName("findAll(Example)")
        @Test
        void __WithExample() {
            final var probe = Actor.of(null, "KILMER");
            final var example = Example.of(probe);
            final var size = 2;
            final var sort = Sort.by(Sort.Order.asc(Actor_.actorId.getName()));
            for (var p = 0; ; p++) {
                final var pageable = PageRequest.of(p, size, sort);
                final var page = applyRepositoryInstance(r -> r.findAll(example, pageable));
                assertThat(page.getSize()).isLessThanOrEqualTo(size);
                assertThat(page.getNumber()).isEqualTo(pageable.getPageNumber());
                assertThat(page.getContent())
                        .isSortedAccordingTo(comparing(Actor::getActorId))
                        .extracting(Actor::getLastName)
                        .allMatch(v -> Objects.equals(v, probe.getLastName()));
                if (page.isEmpty()) {
                    break;
                }
            }
        }
    }

    @DisplayName("findAllByActorIdGreaterThan")
    @Nested
    class FindAllByActorIdGreaterThanTest {

        @Test
        void __() {
        }
    }

    @DisplayName("findAllByActorIdGreaterThanOrderByActorIdAsc")
    @Nested
    class FindAllByActorIdGreaterThanOrderByActorIdAscTest {

        @Test
        void __() {
        }
    }

    @DisplayName("findAllByLastName")
    @Nested
    class FindAllByLastNameTest {

        @ValueSource(strings = {"프라이인드로테쭈젠덴", "알렉산더클라이브대한"})
        @ParameterizedTest
        void _Empty_(final String lastName) {
            final var found = applyRepositoryInstance(r -> r.findAllByLastName(lastName, Pageable.ofSize(8)));
            assertThat(found).isEmpty();
        }

        @Test
        void __KILMER() {
            final var lastName = "KILMER";
            final var size = current().nextInt(2, 6);
            final var page = applyRepositoryInstance(r -> r.findAllByLastName(lastName, Pageable.ofSize(size)));
            assertThat(page)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(size)
                    .extracting(Actor::getLastName)
                    .containsOnly(lastName);
        }
    }
}
