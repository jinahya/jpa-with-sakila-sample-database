package com.github.jinahya.persistence.sakila;

import jakarta.persistence.metamodel.Attribute;

import static java.util.Optional.ofNullable;

public final class RentalConstants {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String QUERY_FIND_BY_RENTAL_ID = "Rental_findByRentalId";

    public static final String PARAMETER_RENTAL_ID = "rentalId";

    static {
        ofNullable(Rental_.rentalId).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_RENTAL_ID);
        });
    }

    public static final String QUERY_FIND_ALL = "Rental_findAll";

    public static final String PARAMETER_RENTAL_ID_MIN_EXCLUSIVE = "rentalIdMinExclusive";

    // ------------------------------------------------------------------------------------------------------ rentalDate

    public static final String QUERY_FIND_ALL_BY_RENTAL_DATE_BETWEEN = "Rental_findAllByRentalDateBetween";

    public static final String PARAMETER_RENTAL_DATE_MIN = "rentalDateMin";

    public static final String PARAMETER_RENTAL_DATE_MIN_INCLUSIVE = "rentalDateMinInclusive";

    public static final String PARAMETER_RENTAL_DATE_MAX_INCLUSIVE = "rentalDateMaxInclusive";

    public static final String QUERY_FIND_ALL_BY_RENTAL_DATE_BETWEEN_HALF_OPEN = "Rental_findAllByRentalDateBetweenHalfOpen";

    public static final String PARAMETER_RENTAL_DATE_MAX_EXCLUSIVE = "rentalDateMaxExclusive";

    // ------------------------------------------------------------------------------------------- inventoryId/inventory

    // --------------------------------------------------------------------------------------------- customerId/customer

    // --------------------------------------------------------------------------------------------------- staffId/staff

    // -----------------------------------------------------------------------------------------------------------------
    private RentalConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
