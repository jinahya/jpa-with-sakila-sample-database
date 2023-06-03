package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila._BaseEntity;

abstract class _BaseEntityServiceIT<
        SERVICE extends _BaseEntityService<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Comparable<? super ID>>
        extends __BaseEntityServiceIT<SERVICE, ENTITY, ID> {

    _BaseEntityServiceIT(final Class<SERVICE> queriesClass, final Class<ENTITY> entityClass, final Class<ID> idClass) {
        super(queriesClass, entityClass, idClass);
    }
}
