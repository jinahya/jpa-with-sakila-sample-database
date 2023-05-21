package com.github.jinahya.persistence;

final class _JdbcConstants {

    enum LockType {
        READ,
        WRITE
    }

    private _JdbcConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
