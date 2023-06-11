package com.github.jinahya.persistence.sakila;

import jakarta.persistence.metamodel.Attribute;

import java.util.Objects;
import java.util.Optional;

public final class CityConstants {

    public static final String QUERY_FIND_BY_CITY_ID = "City_findByCityId";

    public static final String QUERY_PARAM_CITY_ID = "cityId";

    static {
        Optional.ofNullable(City_.cityId)
                .map(Attribute::getName)
                .ifPresent(v -> {
                    assert v.equals(QUERY_PARAM_CITY_ID);
                });
    }

    /**
     * The name of the query selects entities, ordered by {@link City_#cityId cityId} attribute in ascending order.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <caption>Named queries, JPQLs, and SQLs</caption>
     * <thead><tr><th>JPQL</th><th>(My)SQL</th></tr></thead>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM City AS e
     * WHERE e.cityId > :cityIdMinExclusive
     * ORDER BY e.cityId ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM city
     * WHERE city_id > ?
     * ORDER BY city_id ASC
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see City_#cityId
     * @see #PARAM_CITY_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL = "City_findAll";

    public static final String PARAM_CITY_ID_MIN_EXCLUSIVE = "cityIdMinExclusive";

    /**
     * The name of the query selects entities which each meets following conditions.
     * <ul>
     *   <li>{@link City_#countryId countryId} attribute matches a specific value</li>
     *   <li>{@link City_#cityId cityId} is greater than a specific value</li>
     * </ul>
     * ordered by {@link City_#cityId cityId} attribute in ascending order.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <thead><tr><th>JPQL</th><th>(My)SQL</th></tr></thead>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM City AS e
     * WHERE e.countryId = :countryId
     *       AND e.cityId > :cityIdMinExclusive
     * ORDER BY e.cityId ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM city
     * WHERE country_id = ?
     *       AND city_id > ?
     * ORDER BY city_id ASC
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see City_#countryId
     * @see #PARAM_COUNTRY_ID
     * @see #PARAM_CITY_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL_BY_COUNTRY_ID = "City_findAllCountryId";

    public static final String PARAM_COUNTRY_ID = "countryId";

    static {
        Optional.ofNullable(City_.countryId)
                .map(Attribute::getName)
                .ifPresent(v -> {
                    assert v.equals(PARAM_COUNTRY_ID);
                });
    }

    /**
     * The name of the query selects entities which each meets following conditions.
     * <ul>
     *   <li>{@link City_#country country} attribute matches a specific value</li>
     *   <li>{@link City_#cityId cityId} attribute is greater than a specific value</li>
     * </ul>
     * ordered by {@link City_#cityId cityId} attribute in ascending order.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <thead><tr><th>JPQL</th><th>(My)SQL</th></tr></thead>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM City AS e
     * WHERE e.country = :country
     *       AND e.cityId > :cityIdMinExclusive
     * ORDER BY e.cityId ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM city
     * WHERE country_id = ?
     *       AND city_id > ?
     * ORDER BY city_id ASC
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see City_#country
     * @see #PARAM_COUNTRY
     * @see #PARAM_CITY_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL_BY_COUNTRY = "City_findAllByCountry";

    public static final String PARAM_COUNTRY = "country";

    static {
        Optional.ofNullable(City_.country).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAM_COUNTRY);
        });
    }

    public static final String GRAPH_COUNTRY = "City_country";

    public static final String GRAPH_NODE_COUNTRY = "country";

    static {
        Optional.ofNullable(City_.country).ifPresent(a -> {
            assert Objects.equals(a.getName(), GRAPH_NODE_COUNTRY);
        });
    }

    private CityConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
