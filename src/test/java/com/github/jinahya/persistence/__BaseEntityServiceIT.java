package com.github.jinahya.persistence;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;

import java.util.Objects;
import java.util.function.Function;

@AddPackages({EntityManagerFactoryProducer.class, __BaseEntityService.class})
@EnableAutoWeld
@Slf4j
abstract class __BaseEntityServiceIT<T extends __BaseEntityService<U, V>, U extends __BaseEntity<V>, V> {

    static {
        Assertions.setMaxStackTraceElementsDisplayed(1024);
    }

    __BaseEntityServiceIT(final Class<T> queriesClass) {
        super();
        this.serviceClass = Objects.requireNonNull(queriesClass, "queriesClass is null");
    }

    void containerInitialize(@Observes final ContainerInitialized event) {
        // https://stackoverflow.com/a/57740748/330457
        // onPostConstruct() won't be invoked without this method.
    }

    @PostConstruct
    void onPostConstruct() {
        final var instance = serviceInstance_.select(serviceClass).get();
        try {
            serviceInstance = _ValidationUtils.newValidationProxy(serviceClass, instance, validator);
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException("failed to create a validation proxy of " + instance, roe);
        }
    }

    <R> R applyServiceInstance(final Function<? super T, ? extends R> function) {
        return Objects.requireNonNull(function, "function is null")
                .apply(serviceInstance);
    }

    final Class<T> serviceClass;

    @Inject
    private Instance<__BaseEntityService<U, V>> serviceInstance_;

    private T serviceInstance;

    @Inject
    private Validator validator;
}
