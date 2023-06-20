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
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import static com.github.jinahya.sakila.persistence.util.ReflectionUtils.createUnCloseableProxy;
import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Collections.synchronizedMap;
import static org.slf4j.LoggerFactory.getLogger;

@ApplicationScoped
class ___ValidatorFactoryProducer {

    private static final Logger log = getLogger(lookup().lookupClass());

    private static final Map<ValidatorFactory, ValidatorFactory> PROXIES =
            synchronizedMap(new WeakHashMap<>());

    @___Uncloseable
    @ApplicationScoped
    @Produces
    ValidatorFactory produceValidationFactory() {
        final var bean = buildDefaultValidatorFactory();
        log.debug("producing validator factory: " + bean);
        final var proxy = createUnCloseableProxy(ValidatorFactory.class, bean);
        final var previous = PROXIES.put(proxy, bean);
        assert previous == null;
        return proxy;
    }

    void disposeValidatorFactory(@Disposes @___Uncloseable final ValidatorFactory proxy) {
        log.debug("disposing validator factory: {}", proxy);
        PROXIES.remove(proxy).close();
    }
}
