package com.github.jinahya.sakila.persistence;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.jeasy.random.FieldPredicates.named;
import static org.slf4j.LoggerFactory.getLogger;

class Address_Randomizer
        extends _BaseEntityRandomizer<Address> {

    private static final Logger log = getLogger(lookup().lookupClass());

    Address_Randomizer() {
        super(Address.class);
    }

    @Override
    EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(Address_.addressId.getName()))
                .randomize(named(Address_.address.getName()), new StringRandomizer(50))
                .randomize(named(Address_.address2.getName()), new StringRandomizer(50))
                .randomize(named(Address_.district.getName()), new StringRandomizer(20))
                .excludeField(named(Address_.cityId.getName()))
                .randomize(named(Address_.postalCode.getName()), new StringRandomizer(10))
                .randomize(named(Address_.phone.getName()), new StringRandomizer(10))
                .excludeField(named(Address_.location.getName()))
                .excludeField(named(Address_.locationGeometry.getName()))
                .excludeField(named(Address_.city.getName()))
                ;
    }

    @Override
    EasyRandom random() {
        return super.random();
    }

    @Override
    public Address getRandomValue() {
        final var address = super.getRandomValue();
        address.setLocationGeometryAsPoint(current().nextDouble(), current().nextDouble());
        return address;
    }
}
