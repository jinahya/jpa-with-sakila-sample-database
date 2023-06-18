package com.github.jinahya.sakila.stereotype;

import com.github.jinahya.sakila.data.jpa.repository._BaseEntityRepository;
import com.github.jinahya.sakila.persistence._BaseEntity;

abstract class _BaseEntityRepositoryService<
        REPOSITORY extends _BaseEntityRepository<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Comparable<? super ID>
        >
        extends __BaseEntityRepositoryService<REPOSITORY, ENTITY, ID> {

}
