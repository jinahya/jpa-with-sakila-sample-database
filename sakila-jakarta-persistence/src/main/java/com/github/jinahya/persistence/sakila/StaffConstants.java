package com.github.jinahya.persistence.sakila;

import jakarta.persistence.metamodel.Attribute;

import static com.github.jinahya.persistence.sakila.Staff_.address;
import static java.util.Optional.ofNullable;

/**
 * Constants related to {@link Staff} class.
 * <p>
 * The JPQL and an equivalent SQL are as follows.
 * <table>
 * <thead><tr><th>JPQL</th><th>(My)SQL</th></tr></head>
 * <tbody>
 * <tr>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM Staff AS e
 * WHERE e.address.city = :city
 *       AND e.staffId > :staffIdMinExclusive
 * ORDER BY e.staffId ASC
 *}</td>
 * <td>{@snippet lang = "sql":
 * SELECT s.*
 * FROM staff AS s
 *         JOIN address AS a ON s.address_id = a.address_id
 *         JOIN city AS c ON c.city_id = a.city_id
 * WHERE c.city_id = ?
 *       AND s.staff_id > ?
 * ORDER BY s.staff_id ASC
 *}</td>
 * </tr>
 * <tr>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM Staff AS e
 *         JOIN FETCH e.address AS a
 *         JOIN FETCH a.city AS c
 * WHERE c = :city
 *       AND e.staffId > :staffIdMinExclusive
 * ORDER BY e.staffId ASC
 *}</td>
 * <td>{@snippet lang = "sql":
 * SELECT s.*, a.*, c.*
 * FROM staff AS s
 *         JOIN address AS a ON s.address_id = a.address_id
 *         JOIN city AS c ON c.city_id = a.city_id
 * WHERE c.city_id = ?
 *       AND s.staff_id > ?
 * ORDER BY s.staff_id ASC
 *}</td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class StaffConstants {

    // --------------------------------------------------------------------------------------------------------- staffId

    /**
     * The name of the query selects the entity whose {@link Staff_#staffId staffId} attribute matches a specific
     * value.
     *
     * @see Staff_#staffId
     * @see #PARAMETER_STAFF_ID
     */
    public static final String QUERY_FIND_BY_STAFF_ID = "Staff_findByStaffId";

    public static final String PARAMETER_STAFF_ID = "staffId";

    static {
        ofNullable(Staff_.staffId).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_STAFF_ID);
        });
    }

    /**
     * The name of the query selects entities which each {@link Staff_#staffId} attribute is greater than a specific
     * value, ordered by the {@link Staff_#staffId} in ascending order. The value is {@value}.
     *
     * @see #PARAMETER_STAFF_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL = "Staff_findAll";

    /**
     * The name of the parameter for limiting lower exclusive value of {@link Staff_#staffId} attribute. The value is
     * {@value}.
     */
    public static final String PARAMETER_STAFF_ID_MIN_EXCLUSIVE = "staffIdMinExclusive";

    // --------------------------------------------------------------------------------------------------------- address

    /**
     * The name of the query selects all entities which each {@code address.city} attribute matches to a specific
     * value.
     */
    public static final String QUERY_FIND_ALL_BY_CITY = "Staff_findAllByAddressCity";

    public static final String PARAMETER_CITY = "city";

    /**
     * The name of the query selects all entities which each {@code address.city.country} attribute matches to a
     * specific value.
     */
    public static final String QUERY_FIND_ALL_BY_COUNTRY = "Staff_findAllByCountry";

    public static final String PARAMETER_COUNTRY = "country";

    // --------------------------------------------------------------------------------------------------- storeId/store

    // -------------------------------------------------------------------------------------------------------- username
    public static final String QUERY_FIND_ALL_BY_USERNAME = "Staff_findAllByUsername";

    public static final String PARAMETER_USERNAME = "username";

    static {
        ofNullable(Staff_.username).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_USERNAME);
        });
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static final String GRAPH_ADDRESS = "Staff_graph_address";

    public static final String ATTRIBUTE_NODE_ADDRESS = "address";

    static {
        ofNullable(address).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(ATTRIBUTE_NODE_ADDRESS);
        });
    }

    public static final String GRAPH_ADDRESS_CITY = "Staff_graph_address_city";

    public static final String GRAPH_ADDRESS_CITY_COUNTRY = "Staff_graph_address_city_country";

    private StaffConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
