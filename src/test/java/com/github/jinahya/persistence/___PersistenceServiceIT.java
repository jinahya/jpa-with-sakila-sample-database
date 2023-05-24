package com.github.jinahya.persistence;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;

import java.util.Objects;
import java.util.function.Function;

@AddPackages({EntityManagerProducer.class})
@EnableAutoWeld
@Slf4j
abstract class ___PersistenceServiceIT<SERVICE extends ___PersistenceService> {

    /**
     * Create a new instance for testing specified service class.
     *
     * @param serviceClass the service class to test.
     */
    ___PersistenceServiceIT(final Class<SERVICE> serviceClass) {
        super();
        this.serviceClass = Objects.requireNonNull(serviceClass, "serviceClass is null");
    }

    void containerInitialize(@Observes final ContainerInitialized event) {
        // https://stackoverflow.com/a/57740748/330457
        // onPostConstruct() won't be invoked without this method.
    }

    @PostConstruct
    void onPostConstruct() {
        final var instance = serviceInstance_.select(serviceClass).get();
        try {
            serviceInstance = ____TestUtils.newValidationProxy(serviceClass, instance, validator);
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException("failed to create a validation proxy of " + instance, roe);
        }
    }

    /**
     * Applies an instance of {@link SERVICE} to specified function, and returns the result.
     *
     * @param function the function to be applied.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    <R> R applyServiceInstance(final Function<? super SERVICE, ? extends R> function) {
        return Objects.requireNonNull(function, "function is null").apply(serviceInstance);
    }

    final Class<SERVICE> serviceClass;

    @Inject
    private Instance<___PersistenceService> serviceInstance_;

    @Inject
    private Validator validator;

    private SERVICE serviceInstance;
}
