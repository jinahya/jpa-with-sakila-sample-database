package com.github.jinahya.sakila.stereotype;

import com.github.jinahya.persistence.sakila.Actor;
import com.github.jinahya.sakila.data.jpa.repository.ActorRepository;
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
