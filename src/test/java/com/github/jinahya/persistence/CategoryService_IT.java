package com.github.jinahya.persistence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryService_IT
        extends _BaseEntityServiceIT<CategoryService, Category, Integer> {

    CategoryService_IT() {
        super(CategoryService.class);
    }

    @Test
    void locateByName__Action() {
        final var name = "Action";
        final var located = applyServiceInstance(s -> s.locateByName(name));
        assertThat(located)
                .isNotNull()
                .extracting(Category::getName)
                .isEqualTo(name);
    }

    @Test
    void locateByName__Unknown() {
        final var name = "Unknown";
        final var located = applyServiceInstance(s -> s.locateByName(name));
        assertThat(located)
                .isNotNull()
                .extracting(Category::getName)
                .isEqualTo(name);
    }
}
