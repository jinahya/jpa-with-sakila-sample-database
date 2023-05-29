package com.github.jinahya.persistence.sakila;

import com.github.jinahya.persistence.sakila.util.____Utils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@Slf4j
public class ___EntityManagerProducer {

    private static final Map<EntityManager, EntityManager> PROXIES =
            new ConcurrentHashMap<>(new WeakHashMap<>());

    @___Uncloseable
    @Produces
    EntityManager produceEntityManager() {
        final var bean = entityManagerFactory.createEntityManager();
        log.debug("producing entity manager: {}", bean);
        final var proxy = ____Utils.createUnCloseableProxy(EntityManager.class, bean);
        PROXIES.put(proxy, bean);
        return proxy;
    }

    void disposeEntityManager(@Disposes @___Uncloseable final EntityManager proxy) {
        log.debug("disposing entity manager: {}", proxy);
        PROXIES.remove(proxy).close();
    }

    @___Uncloseable
    @Inject
    private EntityManagerFactory entityManagerFactory;
}
