package com.github.jinahya.persistence;

abstract class _BaseEntityServiceIT<T extends _BaseEntityService<U, V>, U extends _BaseEntity<V>, V>
        extends __BaseEntityServiceIT<T, U, V> {

    _BaseEntityServiceIT(final Class<T> queriesClass) {
        super(queriesClass);
    }
}
