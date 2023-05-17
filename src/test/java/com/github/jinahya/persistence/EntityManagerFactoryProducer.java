package com.github.jinahya.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@ApplicationScoped
@Slf4j
class EntityManagerFactoryProducer {

    @Produces
    @ApplicationScoped
    EntityManagerFactory produceEntityManagerFactory() {
        final var entityManagerFactory = Persistence.createEntityManagerFactory(
                _PersistenceConstants.PERSISTENCE_UNIT_NAME,
                new HashMap<>()
        );
        log.debug("producing entity manager factory: {}", entityManagerFactory);
        return entityManagerFactory;
    }

    void disposeEntityManagerFactory(@Disposes final EntityManagerFactory entityManagerFactory) {
        log.debug("disposing entity manager factory: {}", entityManagerFactory);
        entityManagerFactory.close();
    }
}
