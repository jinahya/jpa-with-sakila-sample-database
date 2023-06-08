package com.github.jinahya.sakila.stereotype;

import com.github.jinahya.persistence.sakila.__BaseEntity;
import com.github.jinahya.sakila.data.jpa.repository.__BaseEntityRepository;

abstract class __BaseEntityRepositoryServiceIT<
        SERVICE extends __BaseEntityRepositoryService<REPOSITORY, ENTITY, ID>,
        REPOSITORY extends __BaseEntityRepository<ENTITY, ID>,
        ENTITY extends __BaseEntity<ID>,
        ID extends Comparable<? super ID>
        >
        extends ___BaseEntityRepositoryServiceTestBase<SERVICE, REPOSITORY, ENTITY, ID> {

    __BaseEntityRepositoryServiceIT(final Class<SERVICE> serviceClass) {
        super(serviceClass);
    }
}
