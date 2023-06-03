package com.github.jinahya.persistence.sakila;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

@ApplicationScoped
class ___ValidatorProducer {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
