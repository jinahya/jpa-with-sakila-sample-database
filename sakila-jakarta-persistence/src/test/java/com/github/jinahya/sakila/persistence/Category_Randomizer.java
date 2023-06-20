package com.github.jinahya.sakila.persistence;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;

import static com.github.jinahya.sakila.persistence.Category_.categoryId;
import static com.github.jinahya.sakila.persistence.Category_.name;
import static org.jeasy.random.FieldPredicates.named;

class Category_Randomizer
        extends _BaseEntityRandomizer<Category> {

    Category_Randomizer() {
        super(Category.class);
    }

    @Override
    EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(categoryId.getName()))
                .randomize(named(name.getName()), new StringRandomizer(25))
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
