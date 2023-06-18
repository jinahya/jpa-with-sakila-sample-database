package com.github.jinahya.sakila.persistence;

import java.util.Comparator;

import static java.util.Comparator.comparing;

final class __BaseEntityComparators {

    static <ENTITY extends __BaseEntity<ID>, ID extends Comparable<? super ID>> Comparator<ENTITY> comparingIdentifier() {
        return comparing(__BaseEntity::identifier);
    }

    private __BaseEntityComparators() {
        throw new AssertionError("instantiation is not allowed");
    }
}
