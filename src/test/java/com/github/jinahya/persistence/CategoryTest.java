package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class CategoryTest
        extends _BaseEntityTest<Category, Integer> {

    CategoryTest() {
        super(Category.class, Integer.class);
    }
}
