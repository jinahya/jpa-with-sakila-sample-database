package com.github.jinahya.sakila.persistence;

import jakarta.persistence.metamodel.Attribute;

import static com.github.jinahya.sakila.persistence.Inventory_.film;
import static com.github.jinahya.sakila.persistence.Inventory_.inventoryId;
import static com.github.jinahya.sakila.persistence.Inventory_.store;
import static com.github.jinahya.sakila.persistence.Inventory_.storeId;
import static java.util.Optional.ofNullable;

/**
 * Constants related to {@link Inventory} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see Inventory
 */
public final class InventoryConstants {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String QUERY_FIND_BY_INVENTORY_ID = "Inventory_findByInventoryId";

    public static final String PARAMETER_INVENTORY_ID = "inventoryId";

    static {
        ofNullable(inventoryId).map(Attribute::getName).ifPresent(v -> {
            assert v.equalsIgnoreCase(PARAMETER_INVENTORY_ID);
        });
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static final String QUERY_FIND_ALL = "Inventory_findAll";

    public static final String PARAMETER_INVENTORY_ID_MIN_EXCLUSIVE = "inventoryIdMinExclusive";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String PARAMETER_FILM_ID = "filmId";

    static {
        assert PARAMETER_FILM_ID.equals(Inventory_.filmId.getName());
    }

    public static final String QUERY_FIND_ALL_BY_FILM_ID = "Inventory_findAllByFilmId";

    public static final String QUERY_FIND_ALL_BY_FILM = "Inventory_findAllByFilm";

    public static final String PARAMETER_FILM = "film";

    static {
        ofNullable(film).map(Attribute::getName).ifPresent(v -> {
            assert v.equalsIgnoreCase(PARAMETER_FILM);
        });
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static final String QUERY_FIND_ALL_BY_STORE_ID = "Inventory_findAllByStoreId";

    public static final String PARAMETER_STORE_ID = "storeId";

    static {
        ofNullable(storeId).map(Attribute::getName).ifPresent(v -> {
            assert v.equalsIgnoreCase(PARAMETER_STORE_ID);
        });
    }

    public static final String QUERY_FIND_ALL_BY_STORE = "Inventory_findAllByStore";

    public static final String PARAMETER_STORE = "store";

    static {
        ofNullable(store).map(Attribute::getName).ifPresent(v -> {
            assert v.equalsIgnoreCase(PARAMETER_STORE);
        });
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static final String QUERY_FIND_ALL_BY_STORE_ID_AND_FILM_ID = "Inventory_findByFilmIdAndStoreId";

    public static final String QUERY_FIND_ALL_BY_STORE_AND_FILM = "Inventory_findByFilmAndStore";

    // -----------------------------------------------------------------------------------------------------------------

    public static final String QUERY_FIND_DISTINCT_FILMS_BY_STORE_ID = "Inventory_findDistinctFilmsByStoreId";

    public static final String PARAMETER_FILM_ID_MIN_EXCLUSIVE = "filmIdMinExclusive";

    public static final String QUERY_FIND_DISTINCT_FILMS_BY_STORE = "Inventory_findDistinctFilmsByStore";

    private InventoryConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
