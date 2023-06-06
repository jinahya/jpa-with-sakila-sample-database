package com.github.jinahya.persistence.sakila;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

abstract class _BaseEntityIT<T extends _BaseEntity<U>, U extends Comparable<? super U>>
        extends __BaseEntityIT<T, U> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    _BaseEntityIT(final Class<T> entityClass, final Class<U> idClass) {
        super(entityClass, idClass);
    }
}
