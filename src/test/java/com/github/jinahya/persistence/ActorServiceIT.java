package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Slf4j
class ActorServiceIT
        extends _BaseEntityServiceIT<ActorService, Actor, Integer> {

    ActorServiceIT() {
        super(ActorService.class, Actor.class, Integer.class);
    }

    @DisplayName(ActorConstants.NAMED_QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __() {
        }
    }
}
