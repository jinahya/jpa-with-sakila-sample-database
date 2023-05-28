package com.github.jinahya.persistence;

public class AddressConstants {

    public static final String QUERY_FIND_ALL = "Address_findAll";

    public static final String QUERY_FIND_BY_ADDRESS_ID = "Address_findByAddressId";

    public static final String QUERY_FIND_ALL_BY_CITY_ID = "Address_findAllByCityId";

    public static final String QUERY_FIND_ALL_BY_CITY = "Address_findAllByCity";

    private AddressConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
