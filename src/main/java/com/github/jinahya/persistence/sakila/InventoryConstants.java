package com.github.jinahya.persistence.sakila;

/**
 * Constants related to {@link Inventory} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see Inventory
 */
public final class InventoryConstants {

    public static final String QUERY_FIND_BY_INVENTORY_ID = "Inventory_findByInventoryId";

    public static final String QUERY_PARAM_INVENTORY_ID = "inventoryId";

    static {
        assert QUERY_FIND_BY_INVENTORY_ID.equals(Inventory_.inventoryId.getName());
    }

    public static final String QUERY_FIND_ALL = "Inventory_findAll";

    public static final String QUERY_FIND_ALL_INVENTORY_ID_GREATER_THAN
            = "Inventory_findAllInventoryIdGreaterThan";

    public static final String QUERY_PARAM_INVENTORY_ID_MIN_EXCLUSIVE = "inventoryIdMinExclusive";

    public static final String QUERY_FIND_ALL_BY_FILM_ID_AND_STORE_ID = "Inventory_findByFilmIdAndStoreId";

    public static final String QUERY_FIND_ALL_BY_FILM_ID = "Inventory_findAllByFilmId";

    public static final String QUERY_PARAM_STORE_ID_MIN_EXCLUSIVE = "storeIdMinExclusive";

    public static final String QUERY_FIND_ALL_BY_STORE_ID = "Inventory_findAllByStoreId";

    public static final String QUERY_PARAM_FILM_ID_MIN_EXCLUSIVE = "filmIdMinExclusive";

    private InventoryConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
