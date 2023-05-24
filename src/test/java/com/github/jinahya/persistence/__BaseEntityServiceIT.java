package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;

@AddPackages({EntityManagerFactoryProducer.class, __BaseEntityService.class})
@EnableAutoWeld
@Slf4j
abstract class __BaseEntityServiceIT<
        SERVICE extends __BaseEntityService<ENTITY, ID>,
        ENTITY extends __BaseEntity<ID>,
        ID extends Comparable<? super ID>>
        extends ___PersistenceServiceIT<SERVICE> {

    __BaseEntityServiceIT(final Class<SERVICE> serviceClass) {
        super(serviceClass);
    }
}
