package com.github.jinahya.persistence;

import jakarta.persistence.Column;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.function.Supplier;

final class _JdbcUtils {

    static <R> R lockTableAndGet(final Connection connection, final String tableName,
                                 final _JdbcConstants.LockType lockType, final Supplier<? extends R> supplier)
            throws SQLException {
        Objects.requireNonNull(connection, "connection is null");
        Objects.requireNonNull(tableName, "tableName is null");
        Objects.requireNonNull(lockType, "lockType is null");
        Objects.requireNonNull(supplier, "supplier is null");
        try (var statement = connection.createStatement()) {
            boolean result = statement.execute("LOCK TABLES " + tableName + " " + lockType.name());
            try {
                return supplier.get();
            } finally {
                result = statement.execute("UNLOCK TABLES ");
            }
        }
    }

    static <T> T bind(final T obj, final ResultSet results) throws ReflectiveOperationException, SQLException {
        Objects.requireNonNull(obj, "obj is null");
        Objects.requireNonNull(results, "results is null");
        for (final var field : _ReflectionUtils.getFieldsAnnotatedWithColumn(obj.getClass())) {
            final var columnName = field.getAnnotation(Column.class).name();
            final var value = results.getObject(columnName);
            if (!field.canAccess(obj)) {
                field.setAccessible(true);
            }
            field.set(obj, value);
        }
        return obj;
    }

    static <T> T bind(final Class<T> cls, final ResultSet results)
            throws ReflectiveOperationException, SQLException {
        Objects.requireNonNull(cls, "cls is null");
        return bind(_LangUtils.instantiate(cls), results);
    }

    private _JdbcUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
