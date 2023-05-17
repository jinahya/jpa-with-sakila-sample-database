package com.github.jinahya.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
class EntityManagerProducer {

    @Produces
    EntityManager produceEntityManager() {
        final var entityManager = entityManagerFactory.createEntityManager();
        log.debug("producing entity manager: {}", entityManager);
        return entityManager;
    }

    void disposeEntityManager(@Disposes final EntityManager entityManager) {
        log.debug("disposing entity manager: {}", entityManager);
        entityManager.close();
    }

    @Inject
    private EntityManagerFactory entityManagerFactory;
}
