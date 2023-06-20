package com.github.jinahya.sakila.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

class Category_Test
        extends _BaseEntityTest<Category, Integer> {

    private static final Logger log = getLogger(lookup().lookupClass());

    Category_Test() {
        super(Category.class, Integer.class);
    }
}
