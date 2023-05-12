package com.github.jinahya.persistence;

abstract class _BaseEntityTest<T extends _BaseEntity<U>, U>
        extends __BaseEntityTest<T, U> {

    protected _BaseEntityTest(final Class<T> entityClass, final Class<U> idClass) {
        super(entityClass, idClass);
    }
}
