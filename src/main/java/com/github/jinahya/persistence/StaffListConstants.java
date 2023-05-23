package com.github.jinahya.persistence;

public final class StaffListConstants {

    public static final String NAMED_QUERY_FIND_ALL = "StaffList_findAll";

    public static final String NAMED_QUERY_FIND_BY_ID = "StaffList_findById";

    private StaffListConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
