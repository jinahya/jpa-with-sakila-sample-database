package com.github.jinahya.persistence.sakila;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;

import static org.jeasy.random.FieldPredicates.named;

class Actor_Randomizer
        extends _BaseEntityRandomizer<Actor> {

    Actor_Randomizer() {
        super(Actor.class);
    }

    @Override
    EasyRandomParameters parameters() {
        return super.parameters()
                .randomize(named(Actor_.firstName.getName()), new StringRandomizer(45))
                .randomize(named(Actor_.lastName.getName()), new StringRandomizer(45))
                ;
    }

    @Override
    EasyRandom random() {
        return super.random();
    }

    @Override
    public Actor getRandomValue() {
        return super.getRandomValue();
    }
}
