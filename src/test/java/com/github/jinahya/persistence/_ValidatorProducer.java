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
class _ValidatorProducer {

    @Produces
    @ApplicationScoped
    Validator produceValidator() {
        final var validator = validatorFactory.getValidator();
        log.debug("producing validator: " + validator);
        return validator;
    }

    void disposeValidatorFactory(@Disposes final Validator validator) {
        log.debug("disposing validator factory: {}", validator);
    }

    @Inject
    private ValidatorFactory validatorFactory;
}
