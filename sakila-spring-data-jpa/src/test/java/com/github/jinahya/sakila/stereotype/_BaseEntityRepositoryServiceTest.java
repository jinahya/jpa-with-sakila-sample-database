package com.github.jinahya.sakila.stereotype;

import com.github.jinahya.persistence.sakila._BaseEntity;
import com.github.jinahya.sakila.data.jpa.repository._BaseEntityRepository;

abstract class _BaseEntityRepositoryServiceTest<
        SERVICE extends _BaseEntityRepositoryService<REPOSITORY, ENTITY, ID>,
        REPOSITORY extends _BaseEntityRepository<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Comparable<? super ID>
        >
        extends __BaseEntityRepositoryServiceTest<SERVICE, REPOSITORY, ENTITY, ID> {

    _BaseEntityRepositoryServiceTest(final Class<SERVICE> serviceClass) {
        super(serviceClass);
    }
}
