package com.github.jinahya.sakila.stereotype;

import com.github.jinahya.sakila.data.jpa.repository.ActorRepository;
import com.github.jinahya.sakila.persistence.Actor;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {
        ActorRepositoryService.class
})
class ActorRepositoryService_Test
        extends _BaseEntityRepositoryServiceTest<ActorRepositoryService, ActorRepository, Actor, Integer> {

    ActorRepositoryService_Test() {
        super(ActorRepositoryService.class);
    }
}
