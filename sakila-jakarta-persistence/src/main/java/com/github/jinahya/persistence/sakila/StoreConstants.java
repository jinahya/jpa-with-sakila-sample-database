package com.github.jinahya.persistence.sakila;

import jakarta.persistence.metamodel.Attribute;

import static java.util.Optional.ofNullable;

/**
 * Defines constants related to {@link Store} entity.
 * <p>
 * Predefined named queries, its JPQLs and equivalent SQLs are as follows.
 * <table>
 * <caption>Named queries, JPQLs, and SQLs</caption>
 * <thead><tr><th>Name</th><th>JPQL</th><th>SQL</th></tr></thead>
 * <tbody>
 * <tr>
 * <td>{@link #QUERY_FIND_BY_STORE_ID}<br/>({@value #QUERY_FIND_BY_STORE_ID})</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM Store AS e
 * WHERE e.storeId = :storeId  // @link substring=".storeId" target="Store_#storeId" @link substring=":storeId" target="#PARAMETER_STORE_ID"
 *}</td>
 * <td>{@snippet lang = "sql":
 * SELECT *
 * FROM store
 * WHERE store_id = ? // @link substring="store_id" target="Store#COLUMN_NAME_STORE_ID"
 *}</td>
 * </tr>
 * <tr>
 * <td>{@link #QUERY_FIND_ALL}<br/>({@value #QUERY_FIND_ALL})</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM Store AS e
 * WHERE e.storeId > :storeIdMinExclusive // @link substring=":storeIdMinExclusive" target="#PARAMETER_STORE_ID_MIN_EXCLUSIVE"
 * ORDER BY e.storeId ASC
 *}</td>
 * <td>{@snippet lang = "sql":
 * SELECT *
 * FROM store
 * WHERE store_id > ?
 * ORDER BY store_id ASC
 *}</td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see Store
 */
public final class StoreConstants {

    public static final String QUERY_FIND_BY_STORE_ID = "Store_findByStoreId";

    public static final String PARAMETER_STORE_ID = "storeId";

    static {
        ofNullable(Store_.storeId).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_STORE_ID);
        });
    }

    public static final String QUERY_FIND_ALL = "Store_findAll";

    public static final String PARAMETER_STORE_ID_MIN_EXCLUSIVE = "storeIdMinExclusive";

    public static final String QUERY_FIND_BY_MANAGER_STAFF_ID = "Store_findByManagerStaffId";

    public static final String PARAMETER_MANAGER_STAFF_ID = "managerStaffId";

    static {
        ofNullable(Store_.managerStaffId).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_MANAGER_STAFF_ID);
        });
    }

    public static final String QUERY_FIND_BY_MANAGER_STAFF = "Store_findByManagerStaff";

    public static final String PARAMETER_MANAGER_STAFF = "managerStaff";

    static {
        ofNullable(Store_.managerStaff).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_MANAGER_STAFF);
        });
    }

    public static final String ENTITY_GRAPH_MANAGER_STAFF = "Store_managerStaff";

    public static final String ATTRIBUTE_NODE_MANAGER_STAFF = "managerStaff";

    static {
        ofNullable(Store_.managerStaff).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(ATTRIBUTE_NODE_MANAGER_STAFF);
        });
    }

    public static final String ENTITY_GRAPH_ADDRESS = "Store_address";

    public static final String ATTRIBUTE_NODE_ADDRESS = "address";

    static {
        ofNullable(Store_.address).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(ATTRIBUTE_NODE_ADDRESS);
        });
    }

    public static final String ENTITY_GRAPH_MANAGER_STAFF_AND_ADDRESS = "Store_managerStaffAndAddress";

    public static final String ENTITY_GRAPH_MANAGER_STAFF_WITH_ONES_ADDRESS_AND_ADDRESS = "Store_managerStaffWithOnesAddressAndAddress";

    private StoreConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
