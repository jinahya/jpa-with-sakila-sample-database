package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Category_IT
        extends _BaseEntityIT<Category, Integer> {

    Category_IT() {
        super(Category.class, Integer.class);
    }
}
