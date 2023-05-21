package com.github.jinahya.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
class _ValidatorFactoryProducer {

    @Produces
    @ApplicationScoped
    ValidatorFactory produceValidationFactory() {
        final var validatorFactory = Validation.buildDefaultValidatorFactory();
        log.debug("producing validator factory: " + validatorFactory);
        return validatorFactory;
    }

    void disposeValidatorFactory(@Disposes final ValidatorFactory validatorFactory) {
        log.debug("disposing validator factory: {}", validatorFactory);
        validatorFactory.close();
    }
}
