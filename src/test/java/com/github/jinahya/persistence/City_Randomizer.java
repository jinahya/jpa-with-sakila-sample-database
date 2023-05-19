package com.github.jinahya.persistence;

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
    protected EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(City_.cityId.getName()))
                .randomize(named(City_.city.getName()), new StringRandomizer(50))
                ;
    }

    @Override
    protected EasyRandom random() {
        return super.random();
    }

    @Override
    public City getRandomValue() {
        return super.getRandomValue();
    }
}
