package com.github.jinahya.sakila.persistence;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;

import static org.jeasy.random.FieldPredicates.named;

class City_Randomizer
        extends _BaseEntityRandomizer<City> {

    City_Randomizer() {
        super(City.class);
    }

    @Override
    EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(City_.cityId.getName()))
                .randomize(named(City_.city.getName()), new StringRandomizer(50))
                .excludeField(named(City_.countryId.getName()))
                .excludeField(named(City_.country.getName()))
                ;
    }

    @Override
    EasyRandom random() {
        return super.random();
    }

    @Override
    public City getRandomValue() {
        return super.getRandomValue();
    }
}
