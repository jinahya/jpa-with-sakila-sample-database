package com.github.jinahya.sakila.data.jpa.repository;

import com.github.jinahya.persistence.sakila._BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface _BaseEntityRepository<ENTITY extends _BaseEntity<ID>, ID extends Comparable<? super ID>>
        extends __BaseEntityRepository<ENTITY, ID> {

}
