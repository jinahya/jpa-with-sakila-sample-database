package com.github.jinahya.persistence;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

final class _LangUtils {

    private static Method find_close_method(final Class<?> clazz) {
        Objects.requireNonNull(clazz, "clazz is null");
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            try {
                return c.getMethod("close");
            } catch (final NoSuchMethodException nsme) {
                // ok.
            }
        }
        throw new IllegalArgumentException("unable to find the 'close()` method onward " + clazz);
    }

    @SuppressWarnings({"unchecked"})
    static <T> T uncloseableProxy(final Class<T> cls, final T obj) {
        Objects.requireNonNull(cls, "cls is null");
        Objects.requireNonNull(obj, "obj is null");
        final Method close = find_close_method(cls);
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

    private _LangUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
