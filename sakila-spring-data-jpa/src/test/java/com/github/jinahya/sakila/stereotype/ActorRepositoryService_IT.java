package com.github.jinahya.sakila.stereotype;

import com.github.jinahya.persistence.sakila.Actor;
import com.github.jinahya.sakila.data.jpa.repository.ActorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

//@ContextConfiguration(classes = {
//        ActorRepositoryService.class
//        ,
//        ActorRepository.class
////        ,
////        JpaRepositoriesAutoConfiguration.class
//})
//@EnableJpaRepositories
class ActorRepositoryService_IT
        extends _BaseEntityRepositoryServiceIT<ActorRepositoryService, ActorRepository, Actor, Integer> {

    private static final Logger log = getLogger(lookup().lookupClass());

    ActorRepositoryService_IT() {
        super(ActorRepositoryService.class);
    }

    @DisplayName("testEachPage")
    @Nested
    class TestEachPageTest {

        @Test
        void __() {
            final var size = ThreadLocalRandom.current().nextInt(16, 32);
            applyServiceInstance(s -> {
                s.testEachPage(
                        size,
                        p -> {
                            assertThat(p).hasSizeLessThanOrEqualTo(size)
                                    .extracting(Actor::getActorId)
                                    .isSorted();
                            log.debug("page: {}", p);
                            p.getContent().forEach(e -> {
                                log.debug("\t -> e: {}", e);
                            });
                            return true;
                        }
                );
                return null;
            });
        }
    }
}
