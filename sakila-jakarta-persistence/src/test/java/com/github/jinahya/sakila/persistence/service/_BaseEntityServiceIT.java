package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence._BaseEntity;

abstract class _BaseEntityServiceIT<
        SERVICE extends _BaseEntityService<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Comparable<? super ID>>
        extends __BaseEntityServiceIT<SERVICE, ENTITY, ID> {

    _BaseEntityServiceIT(final Class<SERVICE> queriesClass, final Class<ENTITY> entityClass, final Class<ID> idClass) {
        super(queriesClass, entityClass, idClass);
    }
}
