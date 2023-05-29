package com.github.jinahya.persistence.sakila;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Category_Test
        extends _BaseEntityTest<Category, Integer> {

    Category_Test() {
        super(Category.class, Integer.class);
    }
}
