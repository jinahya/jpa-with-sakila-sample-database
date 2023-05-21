package com.github.jinahya.persistence;

import jakarta.validation.Validator;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.validator.testutil.ValidationInvocationHandler;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
final class _ValidationUtils {

    /**
     * Create a new validation proxy of specified object.
     *
     * @param superclass service class.
     * @param obj        service instance.
     * @param validator  a validator.
     * @param <T>        service type parameter
     * @return a new validation proxy of {@code obj}.
     * @throws ReflectiveOperationException if failed to reflect.
     * @see ValidationInvocationHandler
     */
    @SuppressWarnings({"unchecked"})
    static <T> T newValidationProxy(final Class<T> superclass, final T obj, final Validator validator)
            throws ReflectiveOperationException {
        Objects.requireNonNull(superclass, "superclass is null");
        Objects.requireNonNull(obj, "obj is null");
        Objects.requireNonNull(validator, "validator is null");
        final var handler = new ValidationInvocationHandler(obj, validator);
        if (ThreadLocalRandom.current().nextBoolean()) { // Javassist
            // TODO: remove this Javassist-dependent way along with the <dependency/> in the pom.xml
            // https://www.javassist.org/html/javassist/util/proxy/ProxyFactory.html
            final var factory = new ProxyFactory();
            factory.setSuperclass(superclass);
            factory.setFilter(m -> !m.getName().equals("finalize"));
            final var created = factory.createClass();
            final var proxy = (Proxy) created.getDeclaredConstructor().newInstance();
            final MethodHandler mi = (self, thisMethod, proceed, args) -> handler.invoke(obj, thisMethod, args);
            proxy.setHandler(mi);
            return (T) proxy;
        }
        // https://stackoverflow.com/a/40300963/330457
        // https://stackoverflow.com/a/62120611/330457
        final var loaded = new ByteBuddy()
                .subclass(superclass)
                .method(ElementMatchers.any())
                .intercept(InvocationHandlerAdapter.of(handler))
                .make()
                .load(superclass.getClassLoader(),
                      ClassLoadingStrategy.Default.INJECTION.with(PackageDefinitionStrategy.Trivial.INSTANCE))
                .getLoaded();
        return loaded.getConstructor().newInstance();
    }

    _ValidationUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
