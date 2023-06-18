package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence.__BaseEntity;
import com.github.jinahya.sakila.persistence.___EntityManagerFactoryProducer;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

@AddPackages({___EntityManagerFactoryProducer.class, __BaseEntityService.class})
@EnableAutoWeld
abstract class __BaseEntityServiceIT<
        SERVICE extends __BaseEntityService<ENTITY, ID>,
        ENTITY extends __BaseEntity<ID>,
        ID extends Comparable<? super ID>>
        extends ___PersistenceServiceIT<SERVICE> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    __BaseEntityServiceIT(final Class<SERVICE> serviceClass, final Class<ENTITY> entityClass,
                          final Class<ID> idClass) {
        super(serviceClass);
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
        this.idClass = Objects.requireNonNull(idClass, "idClass is null");
    }

    final Class<ENTITY> entityClass;

    final Class<ID> idClass;
}
