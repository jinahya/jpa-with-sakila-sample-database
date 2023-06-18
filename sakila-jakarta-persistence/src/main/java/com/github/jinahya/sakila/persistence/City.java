package com.github.jinahya.sakila.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Optional;

import static com.github.jinahya.sakila.persistence.CityConstants.GRAPH_COUNTRY;
import static com.github.jinahya.sakila.persistence.CityConstants.GRAPH_NODE_COUNTRY;
import static com.github.jinahya.sakila.persistence.CityConstants.PARAM_CITY_ID_MIN_EXCLUSIVE;
import static com.github.jinahya.sakila.persistence.CityConstants.PARAM_COUNTRY_ID;
import static com.github.jinahya.sakila.persistence.CityConstants.QUERY_FIND_ALL;
import static com.github.jinahya.sakila.persistence.CityConstants.QUERY_FIND_ALL_BY_COUNTRY;
import static com.github.jinahya.sakila.persistence.CityConstants.QUERY_FIND_ALL_BY_COUNTRY_ID;
import static com.github.jinahya.sakila.persistence.CityConstants.QUERY_FIND_BY_CITY_ID;
import static com.github.jinahya.sakila.persistence.CityConstants.QUERY_PARAM_CITY_ID;
import static com.github.jinahya.sakila.persistence._DomainConstants.MAX_SMALLINT_UNSIGNED;

/**
 * An entity for mapping {@value #TABLE_NAME} table.
 * <p>
 * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-city.html">
 * The {@value #TABLE_NAME} table contains a list of cities.<br/>The {@value #TABLE_NAME} table is referred to by a
 * foreign key in the {@value Address#TABLE_NAME} table and refers to the {@value Country#TABLE_NAME} table using a
 * foreign key.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-city.html">5.1.4 The city Table</a>
 */
@NamedEntityGraph(
        name = GRAPH_COUNTRY,
        attributeNodes = {
                @NamedAttributeNode(GRAPH_NODE_COUNTRY)
        }
)
@NamedQuery(name = QUERY_FIND_ALL_BY_COUNTRY,
            query = """
                    SELECT e
                    FROM City AS e
                    WHERE e.country = :country
                          AND e.cityId > :cityIdMinExclusive
                    ORDER BY e.cityId ASC""")
@NamedQuery(name = QUERY_FIND_ALL_BY_COUNTRY_ID,
            query = "SELECT e" +
                    " FROM City AS e" +
                    " WHERE e.countryId = :" + PARAM_COUNTRY_ID +
                    "       AND e.cityId > :" + PARAM_CITY_ID_MIN_EXCLUSIVE +
                    " ORDER BY e.cityId ASC")
@NamedQuery(name = QUERY_FIND_ALL,
            query = "SELECT e" +
                    " FROM City AS e" +
                    " WHERE e.cityId > :" + PARAM_CITY_ID_MIN_EXCLUSIVE +
                    " ORDER BY e.cityId ASC")
@NamedQuery(name = QUERY_FIND_BY_CITY_ID,
            query = "SELECT e" +
                    " FROM City AS e" +
                    " WHERE e.cityId = :" + QUERY_PARAM_CITY_ID)
@Entity
@Table(name = City.TABLE_NAME)
public class City
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "city";

    /**
     * The name of the table column to which the {@link City_#cityId cityId} attribute maps.
     */
    public static final String COLUMN_NAME_CITY_ID = "city_id";

    /**
     * The name of the table column to which the {@link City_#countryId countryId} attribute maps.
     */
    public static final String COLUMN_NAME_COUNTRY_ID = "country_id";

    /**
     * Creates a new instance withs specified value of {@link City_#cityId cityId} attribute.
     *
     * @param cityId the value of {@link City_#cityId cityId} attribute.
     * @return a new instance with {@code cityId}.
     */
    static City ofCityId(final Integer cityId) {
        final var instance = new City();
        instance.cityId = cityId;
        return instance;
    }

    /**
     * Creates a new instance with specified value of {@link City_#city city} attribute.
     *
     * @param city the value of {@link City_#cityId city} attribute.
     * @return a new instance with {@code city}.
     */
    public static City of(final String city) {
        final var instance = new City();
        instance.city = city;
        return instance;
    }

    /**
     * Creates a new instance.
     */
    public City() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "cityId=" + cityId +
               ",city='" + city +
               ",countryId=" + countryId +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof City)) return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    Integer identifier() {
        return getCityId();
    }

    /**
     * Returns current value of {@link City_#cityId cityId} attribute.
     *
     * @return current value of the {@link City_#cityId cityId} attribute.
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * Replaces current value of {@link City_#cityId cityId} attribute with specified value.
     *
     * @param cityId new value for the {@link City_#cityId cityId} attribute.
     * @deprecated for removal.
     */
    // TODO: remove!
    @Deprecated(forRemoval = true)
    private void setCityId(final Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * Returns current value of {@link City_#city city} attribute.
     *
     * @return current value of the {@link City_#city city} attribute.
     */
    public String getCity() {
        return city;
    }

    /**
     * Replaces current value of {@link City_#city city} attribute with specified value.
     *
     * @param city new value for the {@link City_#city city} attribute.
     */
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * Returns current value of {@link City_#countryId countryId} attribute.
     *
     * @return current value of the {@link City_#countryId countryId} attribute.
     */
    public Integer getCountryId() {
        return countryId;
    }

    /**
     * Replaces current value of {@link City_#countryId countryId} attribute with specified value.
     *
     * @param countryId new value for the {@link City_#countryId countryId} attribute.
     */
    void setCountryId(final Integer countryId) {
        this.countryId = countryId;
    }

    /**
     * Returns current value of {@link City_#country country} attribute.
     *
     * @return current value of the {@link City_#country country} attribute.
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Replaces current value of {@link City_#country country} attribute with specified value.
     *
     * @param country new value for the {@link City_#country country} attribute.
     * @apiNote This method also update {@link City_#countryId countryId} attribute with {@code country?.countryId}.
     */
    public void setCountry(final Country country) {
        this.country = country;
        setCountryId(
                Optional.ofNullable(this.country)
                        .map(Country::getCountryId)
                        .orElse(null)
        );
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-city.html">
     * A surrogate primary key used to uniquely identify each city in the table.
     * </blockquote>
     */
    @Max(MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_CITY_ID, nullable = false,
            insertable = /*false*/true, // EclipseLink
            updatable = false)
    private Integer cityId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-city.html">
     * The name of the city.
     * </blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "city", nullable = false, length = 50)
    private String city;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-city.html">
     * A foreign key identifying the country that the city belongs to.
     * </blockquote>
     */
    @Max(MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_COUNTRY_ID, nullable = false)
    private Integer countryId;

    /**
     * 이 도시가 포함된 국가. {@link Country_#countryId countryId} attribute 에 더하여, 같은 컬럼({@value #COLUMN_NAME_COUNTRY_ID})에 매핑된
     * attribute 이다.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_COUNTRY_ID, nullable = false,
                insertable = false, // !!!
                updatable = false   // !!!
                )
    private Country country;
}
