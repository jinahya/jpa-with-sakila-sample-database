package com.github.jinahya.persistence.sakila;

public final class CountryConstants {

    public static final String QUERY_FIND_BY_COUNTRY_ID = "Country_findByCountryId";

    public static final String QUERY_PARAM_COUNTRY_ID = "countryId";

    static {
        assert QUERY_PARAM_COUNTRY_ID.equals(Country_.countryId.getName());
    }

    public static final String QUERY_FIND_ALL = "Country_findAll";

    public static final String QUERY_FIND_ALL_BY_COUNTRY_ID_GREATER_THAN = "Country_findAllIdGreaterThan";

    public static final String QUERY_PARAM_COUNTRY_ID_MIN_EXCLUSIVE = "countryIdMinExclusive";

    public static final String QUERY_FIND_ALL_BY_COUNTRY = "Country_findByCountry";

    public static final String QUERY_PARAM_COUNTRY = "country";

    static {
        assert QUERY_PARAM_COUNTRY.equals(Country_.country.getName());
    }

    private CountryConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
