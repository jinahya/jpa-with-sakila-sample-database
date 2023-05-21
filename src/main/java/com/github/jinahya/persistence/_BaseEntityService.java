package com.github.jinahya.persistence;

abstract class _BaseEntityService<T extends _BaseEntity<U>, U>
        extends __BaseEntityService<T, U> {

    _BaseEntityService(final Class<T> entityClass) {
        super(entityClass);
    }
}
