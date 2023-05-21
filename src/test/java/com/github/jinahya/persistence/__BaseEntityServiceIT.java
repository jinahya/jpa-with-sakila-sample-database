package com.github.jinahya.persistence;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.testutil.ValidationInvocationHandler;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;

import java.util.Objects;
import java.util.function.Function;

@AddPackages({EntityManagerFactoryProducer.class, __BaseEntityService.class})
@EnableAutoWeld
@Slf4j
abstract class __BaseEntityServiceIT<T extends __BaseEntityService<U, V>, U extends __BaseEntity<V>, V> {

    __BaseEntityServiceIT(final Class<T> queriesClass) {
        super();
        this.serviceClass = Objects.requireNonNull(queriesClass, "queriesClass is null");
    }

    // https://stackoverflow.com/a/57740748/330457
    private void containerInitialize(@Observes final ContainerInitialized event) {
        log.debug("container initialized: " + event);
    }

    @PostConstruct
    private void onPostConstruct() {
        log.debug("constructed...");
        final var executableValidator = validator.forExecutables();
    }

    <R> R applyServiceInstance(final Function<? super T, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return function.apply(getServiceInstance());
    }

    @SuppressWarnings({"unchecked"})
    private T getServiceInstance() {
        final var instance = this.serviceInstance.select(serviceClass).get();
        final var handler = new ValidationInvocationHandler(instance, validator);
        final Proxy proxy;
        {
            final var factory = new ProxyFactory();
            factory.setSuperclass(serviceClass);
            factory.setFilter(m -> !m.getName().equals("finalize"));
            final var c = factory.createClass();
            try {
                proxy = (Proxy) c.getDeclaredConstructor().newInstance();
            } catch (final ReflectiveOperationException roe) {
                throw new RuntimeException("failed to instantiate the proxy class: " + c, roe);
            }
            final MethodHandler mi = (self, thisMethod, proceed, args) -> handler.invoke(instance, thisMethod, args);
            proxy.setHandler(mi);
        }
        return (T) proxy;
    }

    final Class<T> serviceClass;

    @Inject
    private Instance<__BaseEntityService<U, V>> serviceInstance;

    @Inject
    private Validator validator;
}
