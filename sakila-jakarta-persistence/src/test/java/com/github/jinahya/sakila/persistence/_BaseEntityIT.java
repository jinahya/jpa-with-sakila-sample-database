package com.github.jinahya.sakila.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

abstract class _BaseEntityIT<T extends _BaseEntity<U>, U extends Comparable<? super U>>
        extends __BaseEntityIT<T, U> {

    private static final Logger log = getLogger(lookup().lookupClass());

    _BaseEntityIT(final Class<T> entityClass, final Class<U> idClass) {
        super(entityClass, idClass);
    }
}
