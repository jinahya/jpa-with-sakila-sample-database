package com.github.jinahya.persistence;

abstract class _BaseEntityServiceIT<
        SERVICE extends _BaseEntityService<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Comparable<? super ID>>
        extends __BaseEntityServiceIT<SERVICE, ENTITY, ID> {

    _BaseEntityServiceIT(final Class<SERVICE> queriesClass) {
        super(queriesClass);
    }
}
