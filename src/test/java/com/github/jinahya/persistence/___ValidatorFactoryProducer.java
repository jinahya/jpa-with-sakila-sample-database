package com.github.jinahya.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@Slf4j
class ___ValidatorFactoryProducer {

    private static final Map<ValidatorFactory, ValidatorFactory> PROXIES =
            new ConcurrentHashMap<>(new WeakHashMap<>());

    @___Uncloseable
    @ApplicationScoped
    @Produces
    ValidatorFactory produceValidationFactory() {
        final var bean = Validation.buildDefaultValidatorFactory();
        log.debug("producing validator factory: " + bean);
        final var proxy = ____Utils.createUnCloseableProxy(ValidatorFactory.class, bean);
        final var previous = PROXIES.put(proxy, bean);
        assert previous == null;
        return proxy;
    }

    void disposeValidatorFactory(@Disposes @___Uncloseable final ValidatorFactory proxy) {
        log.debug("disposing validator factory: {}", proxy);
        PROXIES.remove(proxy).close();
    }
}
