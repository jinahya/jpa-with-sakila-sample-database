package com.github.jinahya.persistence.sakila;

/**
 * Defines constants related to {@link Address} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see Address
 * @see Address_
 */
public class AddressConstants {

    /**
     * The name of the query select the entity whose {@link Address_#addressId addressid} attributes match specified
     * value.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <head><tr><th>JPQL</th><th>(My)SQL</th></tr></head>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM Address AS e
     * WHERE e.addressId = :addressId
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM address
     * WHERE address_id = ?
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see Address_#addressId
     * @see #QUERY_PARAM_ADDRESS_ID
     */
    public static final String QUERY_FIND_BY_ADDRESS_ID = "Address_findByAddressId";

    public static final String QUERY_PARAM_ADDRESS_ID = "addressId";

    static {
        assert QUERY_FIND_BY_ADDRESS_ID.equals(Address_.addressId.getName());
    }

    /**
     * The name of the query selects all entities. The value is {@value}.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <head><tr><th>JPQL</th><th>(My)SQL</th></tr></head>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM Address AS e
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM address
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     */
    public static final String QUERY_FIND_ALL = "Address_findAll";

    /**
     * The name of the query selects all entities whose {@link Address_#addressId addressId} attributes are greater than
     * specified value, ordered by {@link Address_#addressId addressId} in ascending order. The value is {@value}.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <head><tr><th>JPQL</th><th>(My)SQL</th></tr></head>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM Address AS e
     * WHERE e.addressId > :addressIdMinExclusive
     * ORDER BY e.addressId ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM address
     * WHERE address_id > ?
     * ORDER BY address_id ASC
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     */
    public static final String QUERY_FIND_ALL_ADDRESS_ID_GREATER_THAN = "Address_findAllAddressIdGreaterThan";

    public static final String QUERY_PARAM_ADDRESS_MIN_EXCLUSIVE = "addressIdMinExclusive";

    /**
     * The name of the query selects all entities whose {@link Address_#cityId cityId} attributes are greater than
     * specified value, ordered by {@link Address_#addressId addressId} in ascending order. The value is {@value}.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <head><tr><th>JPQL</th><th>(My)SQL</th></tr></head>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM Address AS e
     * WHERE e.cityId = :cityId
     * ORDER BY e.addressId ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM address
     * WHERE city_id > ?
     * ORDER BY address_id ASC
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     */
    public static final String QUERY_FIND_ALL_BY_CITY_ID = "Address_findAllByCityId";

    public static final String QUERY_PARAM_CITY_ID = "cityId";

    static {
        assert QUERY_PARAM_CITY_ID.equals(Address_.cityId.getName());
    }

    /**
     * The name of the query selects all entities whose {@link Address_#city city} attributes are greater than specified
     * value, ordered by {@link Address_#addressId addressId} in ascending order. The value is {@value}.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <head><tr><th>JPQL</th><th>(My)SQL</th></tr></head>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM Address AS e
     * WHERE e.city = :city
     * ORDER BY e.addressId ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM address
     * WHERE city_id > ?
     * ORDER BY address_id ASC
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     */
    public static final String QUERY_FIND_ALL_BY_CITY = "Address_findAllByCity";

    public static final String PARAM_CITY = "city";

    static {
        assert PARAM_CITY.equals(Address_.city.getName());
    }

    public static final String GRAPH_CITY = "Address_city";

    public static final String NODE_CITY = "city";

    static {
        assert NODE_CITY.equals(Address_.city.getName());
    }

    public static final String GRAPH_CITY_COUNTRY = "Address_city_country";

    public static final String SUBGRAPH_CITY_COUNTRY = CityConstants.NODE_COUNTRY;

    private AddressConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
