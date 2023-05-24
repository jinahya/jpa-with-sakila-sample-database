package com.github.jinahya.persistence;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import static org.jeasy.random.FieldPredicates.named;

class Rental_Randomizer
        extends _BaseEntityRandomizer<Rental> {

    Rental_Randomizer() {
        super(Rental.class);
    }

    @Override
    EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(Rental_.rentalId.getName()))
                .excludeField(named(Rental_.rentalDate.getName()))
                .excludeField(named(Rental_.inventoryId.getName()))
                .excludeField(named(Rental_.customerId.getName()))
                .excludeField(named(Rental_.rentalDate.getName()))
                .excludeField(named(Rental_.staffId.getName()))
                .excludeField(named(Rental_.inventory.getName()))
                .excludeField(named(Rental_.customer.getName()))
                .excludeField(named(Rental_.staff.getName()))
                ;
    }

    @Override
    EasyRandom random() {
        return super.random();
    }

    @Override
    public Rental getRandomValue() {
        return super.getRandomValue();
    }
}
