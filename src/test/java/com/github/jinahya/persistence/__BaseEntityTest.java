package com.github.jinahya.persistence;

abstract class __BaseEntityTest<T extends __BaseEntity<U>, U>
        extends ___BaseEntityTestBase<T, U> {

    protected __BaseEntityTest(final Class<T> entityClass, final Class<U> idClass) {
        super(entityClass, idClass);
    }
}
