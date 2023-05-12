package com.github.jinahya.persistence;

import java.util.Objects;

abstract class __BaseEntityTest<T extends __BaseEntity<U>, U> {

    protected __BaseEntityTest(final Class<T> entityClass, final Class<U> idClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
        this.idClass = Objects.requireNonNull(idClass, "idClass is null");
    }

    protected final Class<T> entityClass;

    protected final Class<U> idClass;
}
