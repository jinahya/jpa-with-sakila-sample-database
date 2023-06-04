package com.github.jinahya.persistence.sakila;

import java.util.Optional;

public final class StaffConstants {

    /**
     * The name of the query selects the entity whose {@link Staff_#staffId staffId} attribute matches specified value.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <head><tr><th>JPQL</th><th>(My)SQL</th></tr></head>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM Staff AS e
     * WHERE e.staffId = :staffId
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM staff
     * WHERE staff_id = ?
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see Staff_#staffId
     * @see #QUERY_PARAM_STAFF_ID
     */
    public static final String QUERY_FIND_BY_STAFF_ID = "Staff_findByStaffId";

    public static final String QUERY_PARAM_STAFF_ID = "staffId";

    /**
     * The name of the query selects all entities which each {@link Staff_#staffId} attribute is greater than specified
     * value, ordered by the {@link Staff_#staffId} in ascending order. The value is {@value}.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <head><tr><th>JPQL</th><th>(My)SQL</th></tr></head>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM Staff AS e
     * WHERE e.staffId > :staffIdMinExclusive
     * ORDER BY e.staffId ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM staff
     * WHERE staff_id > ?
     * ORDER BY staff_id ASC
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see #QUERY_PARAM_STAFF_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL = "Staff_findAll";

    /**
     * The name of the parameter for limiting lower exclusive value of {@link Staff_#staffId} attribute. The value is
     * {@value}.
     *
     * @see #QUERY_FIND_ALL
     */
    public static final String QUERY_PARAM_STAFF_ID_MIN_EXCLUSIVE = "staffIdMinExclusive";

    /**
     * The name of the query selects all entities which each {@code address.city} attribute matches to specified value.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <head><tr><th>JPQL</th><th>(My)SQL</th></tr>
     * </head>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM Staff AS e
     *
     *
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
     * @see #QUERY_PARAM_CITY
     */
    public static final String QUERY_FIND_ALL_BY_CITY = "Staff_findAllByAddressCity";

    public static final String QUERY_PARAM_CITY = "city";

    /**
     * The name of the query selects all entities which each {@code address.city.country} attribute matches to specified
     * value.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <head><tr><th>JPQL</th><th>(My)SQL</th></tr></head>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM Staff AS e
     *
     *
     *
     * WHERE e.address.city.country = :country
     *       AND e.staffId > :staffIdMinExclusive
     * ORDER BY e.staffId ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT s.*
     * FROM staff AS s
     *         JOIN address AS a ON s.address_id = a.address_id
     *         JOIN city AS c ON c.city_id = a.city_id
     *         JOIN country AS c2 ON c2.country_id = c.country_id
     * WHERE c2.country_id = ?
     *       AND s.staff_id > ?
     * ORDER BY s.staff_id ASC
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see #QUERY_PARAM_COUNTRY
     */
    public static final String QUERY_FIND_ALL_BY_COUNTRY = "Staff_findAllByCountry";

    public static final String QUERY_PARAM_COUNTRY = "country";

    public static final String GRAPH_ADDRESS = "Staff_graph_address";

    public static final String GRAPH_NODE_ADDRESS = "address";

    static {
        Optional.ofNullable(Staff_.address).ifPresent(a -> {
            assert a.getName().equals(GRAPH_NODE_ADDRESS);
        });
    }

    public static final String GRAPH_ADDRESS_CITY = "Staff_graph_address_city";

    public static final String GRAPH_ADDRESS_CITY_COUNTRY = "Staff_graph_address_city_country";

    private StaffConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
