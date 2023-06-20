package com.github.jinahya.sakila.persistence;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;

import static com.github.jinahya.sakila.persistence.City_.city;
import static com.github.jinahya.sakila.persistence.City_.cityId;
import static com.github.jinahya.sakila.persistence.City_.country;
import static com.github.jinahya.sakila.persistence.City_.countryId;
import static org.jeasy.random.FieldPredicates.named;

class City_Randomizer
        extends _BaseEntityRandomizer<City> {

    City_Randomizer() {
        super(City.class);
    }

    @Override
    EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(cityId.getName()))
                .randomize(named(city.getName()), new StringRandomizer(50))
                .excludeField(named(countryId.getName()))
                .excludeField(named(country.getName()))
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
