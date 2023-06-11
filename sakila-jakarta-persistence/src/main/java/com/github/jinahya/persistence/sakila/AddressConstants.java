package com.github.jinahya.persistence.sakila;

import jakarta.persistence.metamodel.Attribute;

import static java.util.Optional.ofNullable;

/**
 * Defines constants related to {@link Address} class.
 * <p>
 * Predefined named queries, its JPQLs and equivalent SQLs are as follows.
 * <table>
 * <caption>Named queries, JPQLs, and SQLs</caption>
 * <thead><tr><th>Name</th><th>JPQL</th><th>SQL</th></tr></thead>
 * <tbody>
 * <tr>
 * <td>{@link #QUERY_FIND_BY_ADDRESS_ID}<br/>({@value #QUERY_FIND_BY_ADDRESS_ID})</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM Address AS e
 * WHERE e.addressId = :addressId  // @link substring=".addressId" target="Address_#addressId" @link substring=":addressId" target="#PARAMETER_ADDRESS_ID"
 *}</td>
 * <td>{@snippet lang = "sql":
 * SELECT *
 * FROM address
 * WHERE address_id = ? // @link substring="address_id" target="Address#COLUMN_NAME_ADDRESS_ID"
 *}</td>
 * </tr>
 * <tr>
 * <td>{@link #QUERY_FIND_ALL}<br/>({@value #QUERY_FIND_ALL})</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM Address AS e
 * WHERE e.addressId > :addressIdMinExclusive // @link substring=":addressIdMinExclusive" target="#PARAMETER_ADDRESS_ID_MIN_EXCLUSIVE"
 * ORDER BY e.addressId ASC
 *}</td>
 * <td>{@snippet lang = "sql":
 * SELECT *
 * FROM address
 * WHERE address_id > ?
 * ORDER BY address_id ASC
 *}</td>
 * </tr>
 * <tr>
 * <td>{@link #QUERY_FIND_ALL_BY_CITY_ID}<br/>({@value #QUERY_FIND_ALL_BY_CITY_ID})</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM Address AS e
 * WHERE e.cityId = :cityId // @link substring=".cityId" target="Address_#cityId" @link substring=":cityId" target="#PARAMETER_CITY_ID"
 *       AND e.addressId > :addressIdMinExclusive
 * ORDER BY e.addressId ASC
 *}</td>
 * <td>{@snippet lang = "sql":
 * SELECT *
 * FROM address
 * WHERE city_id > ?
 *       AND address_id > ?
 * ORDER BY address_id ASC
 *}</td>
 * </tr>
 * <tr>
 * <td>{@link #QUERY_FIND_ALL_BY_CITY}<br/>({@value #QUERY_FIND_ALL_BY_CITY})</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM Address AS e
 * WHERE e.city = :city // @link substring=".city" target="Address_#city" @link substring=":city" target="#PARAMETER_CITY"
 *       AND e.addressId > :addressIdMinExclusive
 * ORDER BY e.addressId ASC
 *}</td>
 * <td>{@snippet lang = "sql":
 * SELECT *
 * FROM address
 * WHERE city_id = ?
 *       AND address_id > ?
 * ORDER BY address_id ASC
 *}</td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see Address
 * @see Address_
 */
public class AddressConstants {

    /**
     * The name of the query which selects the entity whose {@link Address_#addressId addressid} attribute matches
     * specified value. The value is {@value}.
     *
     * @see Address_#addressId
     * @see #PARAMETER_ADDRESS_ID
     */
    public static final String QUERY_FIND_BY_ADDRESS_ID = "Address_findByAddressId";

    public static final String PARAMETER_ADDRESS_ID = "addressId";

    static {
        ofNullable(Address_.addressId).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(QUERY_FIND_BY_ADDRESS_ID);
        });
    }

    /**
     * The name of the query which selects all entities whose {@link Address_#addressId addressId} attributes are
     * greater than specified value, ordered by {@link Address_#addressId addressId} in ascending order. The value is
     * {@value}.
     *
     * @see #PARAMETER_ADDRESS_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL = "Address_findAll";

    public static final String PARAMETER_ADDRESS_ID_MIN_EXCLUSIVE = "addressIdMinExclusive";

    /**
     * The name of the query selects all entities whose {@link Address_#cityId cityId} attributes are greater than
     * specified value, ordered by {@link Address_#addressId addressId} in ascending order. The value is {@value}.
     *
     * @see #PARAMETER_CITY_ID
     */
    public static final String QUERY_FIND_ALL_BY_CITY_ID = "Address_findAllByCityId";

    public static final String PARAMETER_CITY_ID = "cityId";

    static {
        ofNullable(Address_.cityId).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_CITY_ID);
        });
    }

    /**
     * The name of the query selects all entities whose {@link Address_#city city} attributes match specific value,
     * ordered by {@link Address_#addressId addressId} in ascending order. The value is {@value}.
     *
     * @see #PARAMETER_CITY
     */
    public static final String QUERY_FIND_ALL_BY_CITY = "Address_findAllByCity";

    public static final String PARAMETER_CITY = "city";

    static {
        ofNullable(Address_.city).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_CITY);
        });
    }

    public static final String GRAPH_CITY = "Address_city";

    public static final String GRAPH_NODE_CITY = "city";

    static {
        ofNullable(Address_.city).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(GRAPH_NODE_CITY);
        });
    }

    public static final String GRAPH_CITY_COUNTRY = "Address_city_country";

    public static final String SUBGRAPH_CITY_COUNTRY = CityConstants.GRAPH_NODE_COUNTRY;

    private AddressConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
