package com.github.jinahya.sakila.stereotype;

import com.github.jinahya.persistence.sakila._BaseEntity;
import com.github.jinahya.sakila.data.jpa.repository._BaseEntityRepository;

abstract class _BaseEntityRepositoryService<
        REPOSITORY extends _BaseEntityRepository<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Comparable<? super ID>
        >
        extends __BaseEntityRepositoryService<REPOSITORY, ENTITY, ID> {

}
