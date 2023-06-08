package com.github.jinahya.sakila.data.jpa.repository;

import com.github.jinahya.persistence.sakila._BaseEntity;

abstract class _BaseEntityRepositoryIT<
        REPOSITORY extends _BaseEntityRepository<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Comparable<? super ID>>
        extends __BaseEntityRepositoryIT<REPOSITORY, ENTITY, ID> {

    @SuppressWarnings({"unchecked"})
    _BaseEntityRepositoryIT(final Class<REPOSITORY> repositoryClass) {
        super(repositoryClass);
    }
}
