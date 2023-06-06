package com.github.jinahya.persistence.sakila;

import jakarta.persistence.metamodel.Attribute;

import static java.util.Optional.ofNullable;

public final class CategoryConstants {

    public static final String QUERY_FIND_BY_CATEGORY_ID = "Category_findByCategoryId";

    public static final String PARAMETER_CATEGORY_ID = "categoryId";

    static {
        ofNullable(Category_.categoryId).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_CATEGORY_ID);
        });
    }

    public static final String QUERY_FIND_ALL = "Category_findAll";

    public static final String PARAMETER_CATEGORY_ID_MIN_EXCLUSIVE = "categoryIdMinExclusive";

    static {
        ofNullable(Category_.categoryId).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_CATEGORY_ID);
        });
    }

    private CategoryConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
