package com.github.jinahya.sakila.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@ApplicationScoped
class ___ValidatorProducer {

    private static final Logger log = getLogger(lookup().lookupClass());

    @ApplicationScoped
    @Produces
    Validator produceValidator() {
        final var bean = validatorFactory.getValidator();
        log.debug("producing validator: " + bean);
        return bean;
    }

    void disposeValidator(@Disposes final Validator bean) {
        log.debug("disposing validator: {}", bean);
    }

    @___Uncloseable
    @Inject
    private ValidatorFactory validatorFactory;
}
