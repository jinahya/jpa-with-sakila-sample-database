package com.github.jinahya.sakila.stereotype;

import com.github.jinahya.sakila.data.jpa.repository._BaseEntityRepository;
import com.github.jinahya.sakila.persistence._BaseEntity;

abstract class _BaseEntityRepositoryServiceIT<
        SERVICE extends _BaseEntityRepositoryService<REPOSITORY, ENTITY, ID>,
        REPOSITORY extends _BaseEntityRepository<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Comparable<? super ID>
        >
        extends __BaseEntityRepositoryServiceIT<SERVICE, REPOSITORY, ENTITY, ID> {

    _BaseEntityRepositoryServiceIT(final Class<SERVICE> serviceClass) {
        super(serviceClass);
    }
}
