package com.github.jinahya.sakila.persistence;

import java.util.Comparator;

import static java.util.Comparator.comparing;

final class RentalComparators {

    static final Comparator<Rental> COMPARING_RENTAL_ID = __BaseEntityComparators.comparingIdentifier();

    static final Comparator<Rental> COMPARING_RENTAL_DATE = comparing(Rental::getRentalDate);

    static final Comparator<Rental> COMPARING_RENTAL_DATE_RENTAL_ID
            = COMPARING_RENTAL_DATE.thenComparing(COMPARING_RENTAL_ID);

    private RentalComparators() {
        throw new AssertionError("instantiation is not allowed");
    }
}
