package com.github.jinahya.sakila.persistence;

import com.github.jinahya.sakila.persistence.util.ReflectionUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class ___EntityManagerFactoryProducer {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final Map<EntityManagerFactory, EntityManagerFactory> PROXIES =
            new ConcurrentHashMap<>(new WeakHashMap<>());

    @___Uncloseable
    @ApplicationScoped
    @Produces
    EntityManagerFactory produceEntityManagerFactory() {
        final var bean = Persistence.createEntityManagerFactory(
                _DomainConstants.PERSISTENCE_UNIT_NAME,
                new HashMap<>()
        );
        log.debug("producing entity manager factory: {}", bean);
        final var proxy = ReflectionUtils.createUnCloseableProxy(EntityManagerFactory.class, bean);
        PROXIES.put(proxy, bean);
        return proxy;
    }

    void disposeEntityManagerFactory(@Disposes @___Uncloseable final EntityManagerFactory proxy) {
        log.debug("disposing entity manager factory: {}", proxy);
        final var bean = PROXIES.remove(proxy);
        bean.close();
    }
}
