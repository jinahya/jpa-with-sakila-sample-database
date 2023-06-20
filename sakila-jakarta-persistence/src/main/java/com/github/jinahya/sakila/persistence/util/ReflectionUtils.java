package com.github.jinahya.sakila.persistence.util;

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

import static java.lang.invoke.MethodHandles.lookup;
import static java.lang.invoke.MethodHandles.privateLookupIn;
import static java.lang.invoke.MethodType.methodType;
import static java.lang.reflect.Proxy.newProxyInstance;
import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Defines a bunch of utility methods.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class ReflectionUtils {

    private static final Logger log = getLogger(lookup().lookupClass());

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
        if (!requireNonNull(cls, "cls is null").isInterface()) {
            throw new IllegalArgumentException("cls is not an interface: " + cls);
        }
        requireNonNull(obj, "obj is null");
        final var method = findCloseMethod(cls);
        return (T) newProxyInstance(
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
        requireNonNull(type, "type is null");
        try {
            final var lookup = privateLookupIn(type, lookup());
            final var constructor = lookup.findConstructor(type, methodType(void.class));
            return type.cast(constructor.invoke());
        } catch (final Throwable t) {
            throw new RuntimeException("failed to instantiate " + type, t);
        }
    }

    static Method findCloseMethod(final Class<?> cls) {
        requireNonNull(cls, "cls is null");
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
        requireNonNull(cls, "cls is null");
        requireNonNull(consumer, "consumer is null");
        for (var c = cls; c != null; c = c.getSuperclass()) {
            stream(c.getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(Column.class))
                    .forEach(consumer);
        }
    }

    public static List<Field> getFieldsAnnotatedWithColumn(final Class<?> cls) {
        final var list = new ArrayList<Field>();
        acceptFieldsAnnotatedWithColumns(cls, list::add);
        return list;
    }

    public static Field findFieldNamed(final Class<?> cls, final String name, final Class<?> type)
            throws NoSuchFieldException {
        requireNonNull(cls, "cls is null");
        if (requireNonNull(name, "name is null").isBlank()) {
            throw new IllegalArgumentException("blank name");
        }
        requireNonNull(type, "type is null");
        for (Class<?> c = cls; c != null; c = c.getSuperclass()) {
            for (final var field : c.getDeclaredFields()) {
                if (field.getName().equals(name) && type.isAssignableFrom(field.getType())) {
                    return field;
                }
            }
        }
        throw new NoSuchFieldException("no field named as '" + name + "' found in " + cls + " and its superclasses");
    }

    private ReflectionUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
