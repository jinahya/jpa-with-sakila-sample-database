package com.github.jinahya.persistence.sakila;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import static org.jeasy.random.FieldPredicates.named;

class FilmCategory_Randomizer
        extends _BaseEntityRandomizer<FilmCategory> {

    FilmCategory_Randomizer() {
        super(FilmCategory.class);
    }

    @Override
    EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(FilmCategory_.filmId.getName()))
                .excludeField(named(FilmCategory_.categoryId.getName()))
                .excludeField(named(FilmCategory_.film.getName()))
                .excludeField(named(FilmCategory_.category.getName()))
                ;
    }

    @Override
    EasyRandom random() {
        return super.random();
    }

    @Override
    public FilmCategory getRandomValue() {
        return super.getRandomValue();
    }
}
