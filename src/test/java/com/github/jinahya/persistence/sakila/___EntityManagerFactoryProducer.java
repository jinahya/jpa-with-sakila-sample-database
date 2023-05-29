package com.github.jinahya.persistence.sakila;

import com.github.jinahya.persistence.sakila.util.____Utils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@Slf4j
public class ___EntityManagerFactoryProducer {

    private static final Map<EntityManagerFactory, EntityManagerFactory> PROXIES =
            new ConcurrentHashMap<>(new WeakHashMap<>());

    @___Uncloseable
    @ApplicationScoped
    @Produces
    EntityManagerFactory produceEntityManagerFactory() {
        final var bean = Persistence.createEntityManagerFactory(
                _PersistenceConstants.PERSISTENCE_UNIT_NAME,
                new HashMap<>()
        );
        log.debug("producing entity manager factory: {}", bean);
        final var proxy = ____Utils.createUnCloseableProxy(EntityManagerFactory.class, bean);
        PROXIES.put(proxy, bean);
        return proxy;
    }

    void disposeEntityManagerFactory(@Disposes @___Uncloseable final EntityManagerFactory proxy) {
        log.debug("disposing entity manager factory: {}", proxy);
        final var bean = PROXIES.remove(proxy);
        bean.close();
    }
}
