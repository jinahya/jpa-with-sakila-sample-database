package com.github.jinahya.persistence.sakila.util;

import jakarta.persistence.Column;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Defines a bunch of utility methods.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class ReflectionUtils {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Creates a proxy of specified object whose {@code close()} method is prohibited.
     *
     * @param cls type of the object.
     * @param obj the object to be proxied.
     * @param <T> proxy type parameter
     * @return a proxy instance of {@code obj}.
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T createUnCloseableProxy(final Class<T> cls, final T obj) {
        if (!Objects.requireNonNull(cls, "cls is null").isInterface()) {
            throw new IllegalArgumentException("cls is not an interface: " + cls);
        }
        Objects.requireNonNull(obj, "obj is null");
        final var method = ReflectionUtils.findCloseMethod(cls);
        return (T) Proxy.newProxyInstance(
                cls.getClassLoader(),
                new Class<?>[]{cls},
                (p, m, a) -> {
                    if (m.equals(method)) {
                        throw new UnsupportedOperationException(
                                "you're not allowed to close " + obj
                                + ". I'll find you and you ought to know what I'm going to do to you.");
                    }
                    try {
                        return m.invoke(obj, a);
                    } catch (final InvocationTargetException ite) {
                        // just for debugging
                        if (ite.getTargetException() instanceof ConstraintViolationException cve) {
                            cve.getConstraintViolations().forEach(cv -> {
                                log.error("constraint violation: {}", cv);
                            });
                        }
                        throw ite;
                    }
                }
        );
    }

    static <T> T instantiate(final Class<T> type) {
        Objects.requireNonNull(type, "type is null");
        try {
            final var lookup = MethodHandles.privateLookupIn(type, MethodHandles.lookup());
            final var constructor = lookup.findConstructor(type, MethodType.methodType(void.class));
            return type.cast(constructor.invoke());
        } catch (final Throwable t) {
            throw new RuntimeException("failed to instantiate " + type, t);
        }
    }

    static Method findCloseMethod(final Class<?> cls) {
        Objects.requireNonNull(cls, "cls is null");
        for (Class<?> c = cls; c != null; c = c.getSuperclass()) {
            try {
                return c.getMethod("close");
            } catch (final NoSuchMethodException nsme) {
                // ok.
            }
        }
        throw new IllegalArgumentException("unable to find the 'close()` method onward " + cls);
    }

    static void acceptFieldsAnnotatedWithColumns(final Class<?> cls, final Consumer<? super Field> consumer) {
        Objects.requireNonNull(cls, "cls is null");
        Objects.requireNonNull(consumer, "consumer is null");
        for (var c = cls; c != null; c = c.getSuperclass()) {
            Arrays.stream(c.getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(Column.class))
                    .forEach(consumer);
        }
    }

    public static List<Field> getFieldsAnnotatedWithColumn(final Class<?> cls) {
        final var list = new ArrayList<Field>();
        acceptFieldsAnnotatedWithColumns(cls, list::add);
        return list;
    }

    private ReflectionUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
