package com.github.jinahya.persistence;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

import static java.lang.invoke.MethodHandles.lookup;
import static java.lang.invoke.MethodHandles.privateLookupIn;
import static java.lang.invoke.MethodType.methodType;

final class _LangUtils {

    static <T> T instantiate(final Class<T> type) throws ReflectiveOperationException {
        Objects.requireNonNull(type, "type is null");
        final var lookup = privateLookupIn(type, lookup());
        final var constructor = lookup.findConstructor(type, methodType(void.class));
        try {
            return type.cast(constructor.invoke());
        } catch (final Throwable t) {
            throw new RuntimeException("failed to instantiate " + type);
        }
    }

    private static Method findCloseMethod(final Class<?> cls) {
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

    @SuppressWarnings({"unchecked"})
    static <T> T uncloseableProxy(final Class<T> cls, final T obj) {
        if (!Objects.requireNonNull(cls, "cls is null").isInterface()) {
            throw new IllegalArgumentException("cls is not an interface: " + cls);
        }
        Objects.requireNonNull(obj, "obj is null");
        final Method close = findCloseMethod(cls);
        return (T) Proxy.newProxyInstance(
                cls.getClassLoader(),
                new Class<?>[]{cls},
                (p, m, a) -> {
                    if (m.equals(close)) {
                        throw new UnsupportedOperationException("you're not allowed to close " + obj);
                    }
                    return m.invoke(obj, a);
                }
        );
    }

//    private static <T> T uncloseableProxyHelper(final Class<T> cls, final Object obj) {
//        Objects.requireNonNull(cls, "cls is null");
//        Objects.requireNonNull(obj, "obj is null");
//        return uncloseableProxy(cls, cls.cast(obj)); // ClassCastException
//    }
//
//    static <T> T uncloseableProxy(final T obj) {
//        @SuppressWarnings({"unchecked"})
//        final Class<T> cls = (Class<T>) Objects.requireNonNull(obj, "obj is null").getClass();
//        return uncloseableProxyHelper(cls, obj);
//    }

    private _LangUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
