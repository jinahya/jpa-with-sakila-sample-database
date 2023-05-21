package com.github.jinahya.persistence;

import jakarta.persistence.Column;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class _ReflectionUtils {

    static void acceptFieldsAnnotatedWithColumns(final Class<?> cls, final Consumer<? super Field> consumer) {
        Objects.requireNonNull(cls, "cls is null");
        Objects.requireNonNull(consumer, "consumer is null");
        for (var c = cls; c != null; c = c.getSuperclass()) {
            Arrays.stream(c.getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(Column.class))
                    .forEach(consumer::accept);
        }
    }

    static List<Field> getFieldsAnnotatedWithColumn(final Class<?> cls) {
        Objects.requireNonNull(cls, "cls is null");
        final var list = new ArrayList<Field>();
        acceptFieldsAnnotatedWithColumns(cls, list::add);
        return list;
    }

    private _ReflectionUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
