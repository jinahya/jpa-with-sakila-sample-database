package com.github.jinahya.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
class ___ValidatorProducer {

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
