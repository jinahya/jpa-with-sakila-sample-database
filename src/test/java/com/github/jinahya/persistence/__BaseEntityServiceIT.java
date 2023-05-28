package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;

import java.util.Objects;

@AddPackages({___EntityManagerFactoryProducer.class, __BaseEntityService.class})
@EnableAutoWeld
@Slf4j
abstract class __BaseEntityServiceIT<
        SERVICE extends __BaseEntityService<ENTITY, ID>,
        ENTITY extends __BaseEntity<ID>,
        ID extends Comparable<? super ID>>
        extends ___PersistenceServiceIT<SERVICE> {

    __BaseEntityServiceIT(final Class<SERVICE> serviceClass, final Class<ENTITY> entityClass,
                          final Class<ID> idClass) {
        super(serviceClass);
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
        this.idClass = Objects.requireNonNull(idClass, "idClass is null");
    }

    final Class<ENTITY> entityClass;

    final Class<ID> idClass;
}
