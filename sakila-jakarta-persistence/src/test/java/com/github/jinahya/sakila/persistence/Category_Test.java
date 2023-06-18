package com.github.jinahya.sakila.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

class Category_Test
        extends _BaseEntityTest<Category, Integer> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    Category_Test() {
        super(Category.class, Integer.class);
    }
}
