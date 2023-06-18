package com.github.jinahya.sakila.persistence;

import com.github.jinahya.sakila.persistence.util.ReflectionUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
class ___ValidatorFactoryProducer {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final Map<ValidatorFactory, ValidatorFactory> PROXIES =
            new ConcurrentHashMap<>(new WeakHashMap<>());

    @___Uncloseable
    @ApplicationScoped
    @Produces
    ValidatorFactory produceValidationFactory() {
        final var bean = Validation.buildDefaultValidatorFactory();
        log.debug("producing validator factory: " + bean);
        final var proxy = ReflectionUtils.createUnCloseableProxy(ValidatorFactory.class, bean);
        final var previous = PROXIES.put(proxy, bean);
        assert previous == null;
        return proxy;
    }

    void disposeValidatorFactory(@Disposes @___Uncloseable final ValidatorFactory proxy) {
        log.debug("disposing validator factory: {}", proxy);
        PROXIES.remove(proxy).close();
    }
}
