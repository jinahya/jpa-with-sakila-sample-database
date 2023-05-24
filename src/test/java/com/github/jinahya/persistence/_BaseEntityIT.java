package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract class _BaseEntityIT<T extends _BaseEntity<U>, U>
        extends __BaseEntityIT<T, U> {

    _BaseEntityIT(final Class<T> entityClass, final Class<U> idClass) {
        super(entityClass, idClass);
    }
}
