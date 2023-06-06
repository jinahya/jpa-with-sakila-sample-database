package com.github.jinahya.persistence.sakila;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import static org.jeasy.random.FieldPredicates.named;

class FilmActor_Randomizer
        extends _BaseEntityRandomizer<FilmActor> {

    FilmActor_Randomizer() {
        super(FilmActor.class);
    }

    @Override
    EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(FilmActor_.filmId.getName()))
                .excludeField(named(FilmActor_.actorId.getName()))
                .excludeField(named(FilmActor_.film.getName()))
                .excludeField(named(FilmActor_.actor.getName()))
                ;
    }

    @Override
    EasyRandom random() {
        return super.random();
    }

    @Override
    public FilmActor getRandomValue() {
        return super.getRandomValue();
    }
}
