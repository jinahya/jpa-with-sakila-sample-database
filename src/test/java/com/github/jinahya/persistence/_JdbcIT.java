package com.github.jinahya.persistence;

import java.sql.Connection;
import java.util.Objects;
import java.util.function.Function;

class _JdbcIT
        extends _PersistenceIT {

    <R> R applyConnection(final Function<? super Connection, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return applyEntityManager(em -> function.apply(
                _LangUtils.uncloseableProxy(Connection.class, em.unwrap(Connection.class))
        ));
    }
}
