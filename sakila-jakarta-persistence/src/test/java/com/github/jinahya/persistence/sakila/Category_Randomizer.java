package com.github.jinahya.persistence.sakila;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;

import static org.jeasy.random.FieldPredicates.named;

class Category_Randomizer
        extends _BaseEntityRandomizer<Category> {

    Category_Randomizer() {
        super(Category.class);
    }

    @Override
    EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(Category_.categoryId.getName()))
                .randomize(named(Category_.name.getName()), new StringRandomizer(25))
                ;
    }

    @Override
    EasyRandom random() {
        return super.random();
    }

    @Override
    public Category getRandomValue() {
        return super.getRandomValue();
    }
}
