package com.github.jinahya.persistence;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import static org.jeasy.random.FieldPredicates.named;

abstract class _BaseEntityRandomizer<T extends _BaseEntity<?>>
        extends __BaseEntityRandomizer<T> {

    _BaseEntityRandomizer(final Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    protected EasyRandomParameters parameters() {
        return super.parameters()
//                .excludeField(named(_BaseEntity_.lastUpdate.getName()))
                .excludeField(named("lastUpdate"))
                ;
    }

    @Override
    protected EasyRandom random() {
        return super.random();
    }

    @Override
    public T getRandomValue() {
        return super.getRandomValue();
    }
}
