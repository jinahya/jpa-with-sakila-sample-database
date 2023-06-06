package com.github.jinahya.persistence.sakila;

import com.github.jinahya.persistence.sakila.util.ReflectionUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class ___EntityManagerProducer {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final Map<EntityManager, EntityManager> PROXIES =
            new ConcurrentHashMap<>(new WeakHashMap<>());

    @___Uncloseable
    @Produces
    EntityManager produceEntityManager() {
        final var bean = entityManagerFactory.createEntityManager();
        log.debug("producing entity manager: {}", bean);
        final var proxy = ReflectionUtils.createUnCloseableProxy(EntityManager.class, bean);
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
