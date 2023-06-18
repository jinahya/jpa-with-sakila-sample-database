package com.github.jinahya.sakila.persistence;

import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.WeakHashMap;

import static com.github.jinahya.sakila.persistence.util.InvokeUtils.findVarHandle;
import static com.github.jinahya.sakila.persistence.util.ReflectionUtils.findFieldNamed;
import static java.util.Collections.synchronizedMap;
import static java.util.Objects.requireNonNull;

class __ActivatableHelper {

    private static final Map<String, Field> ACTIVE_FIELDS = synchronizedMap(new WeakHashMap<>());

    @SuppressWarnings({
            "java:S2129" // new String(String)
    })
    static Field getActiveField(final Class<?> cls) {
        requireNonNull(cls, "cls is null");
        return ACTIVE_FIELDS.computeIfAbsent(
                new String(cls.getName()),
                k -> {
                    try {
                        return findFieldNamed(Class.forName(k), __Activatable.ATTRIBUTE_NAME_ACTIVE, Integer.class);
                    } catch (final ReflectiveOperationException roe) {
                        throw new RuntimeException(roe);
                    }
                }
        );
    }

    private static final Map<String, VarHandle> ACTIVE_VAR_HANDLES = synchronizedMap(new WeakHashMap<>());

    @SuppressWarnings({
            "java:S2129" // new String(String)
    })
    static VarHandle getActiveVarHandle(final Class<?> cls) {
        requireNonNull(cls, "cls is null");
        return ACTIVE_VAR_HANDLES.computeIfAbsent(
                new String(cls.getName()),
                k -> {
                    try {
                        return findVarHandle(Class.forName(k), __Activatable.ATTRIBUTE_NAME_ACTIVE, Integer.class);
                    } catch (final ReflectiveOperationException roe) {
                        throw new RuntimeException(roe);
                    }
                }
        );
    }

    private __ActivatableHelper() {
        throw new AssertionError("instantiation is not allowed");
    }
}
