package com.github.jinahya.sakila.data.jpa.repository;

import com.github.jinahya.sakila.persistence.__BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface __BaseEntityRepository<ENTITY extends __BaseEntity<ID>, ID extends Comparable<? super ID>>
        extends JpaRepository<ENTITY, ID>,
                JpaSpecificationExecutor<ENTITY> {

}
