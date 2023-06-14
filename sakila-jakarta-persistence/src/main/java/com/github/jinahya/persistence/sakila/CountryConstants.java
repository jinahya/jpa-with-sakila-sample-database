package com.github.jinahya.persistence.sakila;

import jakarta.persistence.metamodel.Attribute;

import static java.util.Optional.ofNullable;

/**
 * Defines constants related to {@link Country} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see Country
 */
public final class CountryConstants {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String QUERY_FIND_BY_COUNTRY_ID = "Country_findByCountryId";

    public static final String PARAMETER_COUNTRY_ID = "countryId";

    static {
        ofNullable(Country_.countryId).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_COUNTRY_ID);
        });
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static final String QUERY_FIND_ALL = "Country_findAll";

    public static final String PARAMETER_COUNTRY_ID_MIN_EXCLUSIVE = "countryIdMinExclusive";

    // -----------------------------------------------------------------------------------------------------------------

    private CountryConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
