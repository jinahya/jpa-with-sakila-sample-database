package com.github.jinahya.sakila.persistence;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import static org.jeasy.random.FieldPredicates.named;

class Store_Randomizer
        extends _BaseEntityRandomizer<Store> {

    Store_Randomizer() {
        super(Store.class);
    }

    @Override
    EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(Store_.storeId.getName()))
                .excludeField(named(Store_.managerStaffId.getName()))
                .excludeField(named(Store_.addressId.getName()))
                .excludeField(named(Store_.managerStaff.getName()))
                .excludeField(named(Store_.address.getName()))
//                .excludeField(named(Store_.staffs.getName()))
                ;
    }

    @Override
    EasyRandom random() {
        return super.random();
    }

    @Override
    public Store getRandomValue() {
        return super.getRandomValue();
    }
}
