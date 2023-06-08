package com.github.jinahya.sakila.data.jpa.repository;

import com.github.jinahya.persistence.sakila.Actor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

class ActorRepositoryIT
        extends _BaseEntityRepositoryIT<ActorRepository, Actor, Integer> {

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

    @DisplayName("findAllByLastName")
    @Nested
    class FindAllByLastNameTest {

        @Test
        void _Empty_爨邯汕寺武穆云籍鞲() {
            final var found = applyRepositoryInstance(r -> r.findAllByLastName("爨邯汕寺武穆云籍鞲", Pageable.ofSize(8)));
            assertThat(found).isEmpty();
        }

        @Test
        void __KILMER() {
            final var lastName = "KILMER";
            final var size = ThreadLocalRandom.current().nextInt(2, 6);
            final var page = applyRepositoryInstance(r -> r.findAllByLastName(lastName, Pageable.ofSize(size)));
            assertThat(page)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(size)
                    .extracting(Actor::getLastName)
                    .containsOnly(lastName);
        }
    }

    @DisplayName("findAllByActorIdGreaterThan")
    @Nested
    class FindAllByActorIdGreaterThanTest {

        @Test
        void __() {
        }
    }
}
