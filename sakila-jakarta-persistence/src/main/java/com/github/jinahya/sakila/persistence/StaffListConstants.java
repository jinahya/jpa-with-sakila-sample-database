package com.github.jinahya.sakila.persistence;

/**
 * Defines constants related to {@link StaffList} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see StaffList
 */
public final class StaffListConstants {

    public static final String QUERY_FIND_ALL = "StaffList_findAll";

    public static final String QUERY_FIND_BY_ID = "StaffList_findById";

    public static final String QUERY_FIND_ALL_BY_ID_GREATER_THAN = "StaffList_findAllByIdGreaterThan";

    private StaffListConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
