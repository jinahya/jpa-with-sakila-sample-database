package com.github.jinahya.persistence;

import java.sql.SQLException;
import java.util.Objects;
import java.util.function.Supplier;

final class _JdbcUtils {

    static <R> R lockTableAndGet(final java.sql.Connection connection, final String tableName,
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

    private _JdbcUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
