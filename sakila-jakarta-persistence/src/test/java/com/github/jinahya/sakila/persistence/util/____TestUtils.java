package com.github.jinahya.sakila.persistence.util;

import jakarta.validation.Validator;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.validator.testutil.ValidationInvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public final class ____TestUtils {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
    public static <T> T newValidationProxy(final Class<T> superclass, final T obj, final Validator validator)
            throws ReflectiveOperationException {
        Objects.requireNonNull(superclass, "superclass is null");
        Objects.requireNonNull(obj, "obj is null");
        Objects.requireNonNull(validator, "validator is null");
        final var handler = new ValidationInvocationHandler(obj, validator) {
            @Override
            public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
                final var x = method.canAccess(obj);
                final var y = method.canAccess(proxy);
                if (!method.canAccess(obj)) {
                    method.setAccessible(true);
                }
                assert method.canAccess(obj);
                return super.invoke(proxy, method, args);
            }
        };
        if (ThreadLocalRandom.current().nextBoolean()) { // Javassist
            // TODO: remove this Javassist-dependent way along with the <dependency/> in the pom.xml
            // https://www.javassist.org/html/javassist/util/proxy/ProxyFactory.html
            final var factory = new ProxyFactory();
            factory.setSuperclass(superclass);
            factory.setFilter(m -> !m.getName().equals("finalize"));
            final var created = factory.createClass();
            final var proxy = (Proxy) created.getDeclaredConstructor().newInstance();
            final MethodHandler mh = (s, tm, p, a) -> handler.invoke(obj, tm, a);
            proxy.setHandler(mh);
            return (T) proxy;
        }
        // https://stackoverflow.com/a/40300963/330457
        // https://stackoverflow.com/a/62120611/330457
        try (var unloaded = new ByteBuddy()
                .subclass(superclass)
                .method(ElementMatchers.any())
                .intercept(InvocationHandlerAdapter.of(handler))
                .make()) {
            final var loaded =
//                    new ByteBuddy()
//                    .subclass(superclass)
//                    .method(ElementMatchers.any())
//                    .intercept(InvocationHandlerAdapter.of(handler))
//                    .make()
                    unloaded.load(superclass.getClassLoader(),
                                  ClassLoadingStrategy.Default.INJECTION.with(PackageDefinitionStrategy.Trivial.INSTANCE))
                            .getLoaded();
            return loaded.getConstructor().newInstance();
        } catch (final IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    ____TestUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
