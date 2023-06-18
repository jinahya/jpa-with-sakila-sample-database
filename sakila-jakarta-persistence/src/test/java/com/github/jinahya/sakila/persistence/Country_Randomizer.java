package com.github.jinahya.sakila.persistence;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;

import static org.jeasy.random.FieldPredicates.named;

class Country_Randomizer
        extends _BaseEntityRandomizer<Country> {

    Country_Randomizer() {
        super(Country.class);
    }

    @Override
    EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(Country_.countryId.getName()))
                .randomize(named(Country_.country.getName()), new StringRandomizer(50))
                ;
    }

    @Override
    EasyRandom random() {
        return super.random();
    }

    @Override
    public Country getRandomValue() {
        return super.getRandomValue();
    }
}
