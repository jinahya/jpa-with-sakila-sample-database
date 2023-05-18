package com.github.jinahya.persistence;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import static org.jeasy.random.FieldPredicates.named;

class Inventory_Randomizer
        extends _BaseEntityRandomizer<Inventory> {

    Inventory_Randomizer() {
        super(Inventory.class);
    }

    @Override
    protected EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(Inventory_.inventoryId.getName()))
                .excludeField(named(Inventory_.filmId.getName()))
                .excludeField(named(Inventory_.storeId.getName()))
                .excludeField(named(Inventory_.film.getName()))
                .excludeField(named(Inventory_.store.getName()))
                ;
    }

    @Override
    protected EasyRandom random() {
        return super.random();
    }

    @Override
    public Inventory getRandomValue() {
        return super.getRandomValue();
    }
}
