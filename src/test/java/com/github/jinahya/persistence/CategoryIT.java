package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class CategoryIT
        extends _BaseEntityIT<Category, Integer> {

    CategoryIT() {
        super(Category.class, Integer.class);
    }
}
