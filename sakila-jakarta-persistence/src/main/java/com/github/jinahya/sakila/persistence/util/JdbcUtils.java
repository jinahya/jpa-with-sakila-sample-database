package com.github.jinahya.sakila.persistence.util;

import jakarta.persistence.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Defines a bunch of utility methods.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class JdbcUtils {

    private static final Logger log = getLogger(lookup().lookupClass());

    private static <T> T bind(final T obj, final ResultSet results) throws SQLException {
        Objects.requireNonNull(obj, "obj is null");
        Objects.requireNonNull(results, "results is null");
        for (final var columnField : ReflectionUtils.getFieldsAnnotatedWithColumn(obj.getClass())) {
            final var columnName = columnField.getAnnotation(Column.class).name();
            final var value = results.getObject(columnName);
            if (!columnField.canAccess(obj)) {
                columnField.setAccessible(true);
            }
            try {
                columnField.set(obj, value);
            } catch (final IllegalAccessException e) {
                throw new RuntimeException("failed to set " + columnField + " with " + value, e);
            }
        }
        return obj;
    }

    public static <T> T bind(final Class<T> cls, final ResultSet results) throws SQLException {
        Objects.requireNonNull(cls, "cls is null");
        return bind(ReflectionUtils.instantiate(cls), results);
    }

    private JdbcUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
