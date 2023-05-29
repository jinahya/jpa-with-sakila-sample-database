package com.github.jinahya.persistence.sakila;

public final class CityConstants {

    public static final String QUERY_FIND_BY_CITY_ID = "City_findByCityId";

    public static final String QUERY_PARAM_CITY_ID = "cityId";

    static {
        assert QUERY_PARAM_CITY_ID.equals(City_.cityId.getName());
    }

    public static final String QUERY_FIND_ALL = "City_findAll";

    public static final String QUERY_FIND_ALL_BY_CITY_ID_GREATER_THAN = "City_findAllIdGreaterThan";

    public static final String QUERY_PARAM_CITY_ID_MIN_EXCLUSIVE = "cityIdMinExclusive";

    public static final String QUERY_FIND_ALL_BY_CITY = "City_findByCity";

    public static final String QUERY_PARAM_CITY = "city";

    /**
     * The name of the query selects entities whose {@link City_#countryId countryId} attributes match specified value,
     * ordered by {@link City_#cityId cityId} attribute in ascending order.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <head><tr><th>JPQL</th><th>(My)SQL</th></th></tr></head>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM City AS e
     * WHERE e.countryId = :countryId
     * ORDER BY e.cityId ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM city
     * WHERE country_id = ?
     * ORDER BY city_id ASC
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see City_#countryId
     * @see #QUERY_PARAM_COUNTRY_ID
     */
    public static final String QUERY_FIND_ALL_BY_COUNTRY_ID_CITY_ID_GREATER_THAN = "City_findAllCountryId";

    public static final String QUERY_PARAM_COUNTRY_ID = CountryConstants.QUERY_PARAM_COUNTRY_ID;

    /**
     * The name of the query selects entities whose {@link City_#country country} attributes match specified value,
     * ordered by {@link City_#cityId cityId} attribute in ascending order.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <head><tr><th>JPQL</th><th>(My)SQL</th></th></tr></head>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM City AS e
     * WHERE e.country = :country
     * ORDER BY e.cityId ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM city
     * WHERE country_id = ?
     * ORDER BY city_id ASC
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see City_#country
     * @see #QUERY_PARAM_COUNTRY
     */
    public static final String QUERY_FIND_ALL_BY_COUNTRY_CITY_ID_GREATER_THAN = "City_findAllByCountry";

    public static final String QUERY_PARAM_COUNTRY = "country";

    static {
        assert QUERY_PARAM_CITY.equals(City_.city.getName());
    }

    private CityConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
