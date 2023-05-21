package com.github.jinahya.persistence;

import jakarta.persistence.Table;

import java.util.Objects;

public final class __BaseEntityUtils {

    public static <T extends __BaseEntity<?>> String findTableName(final Class<T> cls) {
        Objects.requireNonNull(cls, "cls is null");
        for (Class<?> c = cls; c != null; c = c.getSuperclass()) {
            final var table = c.getAnnotation(Table.class);
            if (table != null) {
                return table.name();
            }
        }
        throw new RuntimeException("unable to find Table#name from " + cls + " and upwards");
    }

    private __BaseEntityUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
