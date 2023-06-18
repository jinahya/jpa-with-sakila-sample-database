package com.github.jinahya.sakila.persistence.util;

import org.slf4j.Logger;

import java.lang.invoke.VarHandle;
import java.util.Objects;

import static java.lang.invoke.MethodHandles.lookup;
import static java.lang.invoke.MethodHandles.privateLookupIn;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Utilities related to the {@link java.lang.invoke} package.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class InvokeUtils {

    private static final Logger log = getLogger(lookup().lookupClass());

    public static VarHandle findVarHandle(final Class<?> cls, final String name, final Class<?> type)
            throws IllegalAccessException, NoSuchFieldException {
        Objects.requireNonNull(cls, "cls is null");
        if (Objects.requireNonNull(name, "name is null").isBlank()) {
            throw new IllegalArgumentException("blank name");
        }
        Objects.requireNonNull(type, "type is null");
        for (Class<?> recv = cls; recv != null; recv = recv.getSuperclass()) {
            try {
                return privateLookupIn(recv, lookup()).findVarHandle(recv, name, type);
            } catch (final NoSuchFieldException nsfe) {
                // empty
            }
        }
        throw new NoSuchFieldException("no field named as '" + name + "' found in " + cls + " and its superclasses");
    }

    private InvokeUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
