package com.github.jinahya.persistence.sakila;

import com.github.jinahya.persistence.sakila.util.LocaleUtils;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.Locale;

import static com.github.jinahya.persistence.sakila.CountryConstants.QUERY_FIND_ALL;
import static com.github.jinahya.persistence.sakila.CountryConstants.QUERY_FIND_BY_COUNTRY_ID;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

/**
 * An entity class for mapping {@value #TABLE_NAME} table.
 *
 * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-country.html">
 * The {@value TABLE_NAME} table contains a list of countries.<br/>The {@value TABLE_NAME} table is referred to by a
 * foreign key in the {@value City#TABLE_NAME} table.<br/><cite><a
 * href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-country.html">5.1.5 The country Table</a></cite>
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-country.html">5.1.5 The country Table</a>
 */
@NamedQuery(
        name = QUERY_FIND_ALL,
        query = """
                SELECT e
                FROM Country AS e
                WHERE e.countryId > :countryIdMinExclusive
                ORDER BY e.countryId ASC"""
)
@NamedQuery(
        name = QUERY_FIND_BY_COUNTRY_ID,
        query = """
                SELECT e
                FROM Country AS e
                WHERE e.countryId = :countryId"""
)
@Entity
@Table(name = Country.TABLE_NAME)
public class Country
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "country";

    // ------------------------------------------------------------------------------------------------------ country_id

    /**
     * The name of the table column to which the {@link Country_#countryId countryId} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_COUNTRY_ID = "country_id";

    // --------------------------------------------------------------------------------------------------------- country
    /**
     * The name of the table column to which the {@link Country_#country country} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_COUNTRY = "country";

    /**
     * The length of the {@link #COLUMN_NAME_COUNTRY} column. The value is {@value}.
     */
    public static final int COLUMN_LENGTH_COUNTRY = 50;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a new instance of specified {@link Country_#countryId countryId} attribute value.
     *
     * @param countryId the {@link Country_#countryId countryId} attribute value.
     * @return a new instance of {@code countryId}.
     */
    static Country ofCountryId(final int countryId) {
        final var instance = new Country();
        instance.countryId = countryId;
        return instance;
    }

    /**
     * Creates new instance with specified value of {@link Country_#country country} attribute.
     *
     * @param country the value for the {@link Country_#country country} attribute.
     * @return a new instance of {@code country}.
     */
    public static Country of(final String country) {
        final var instance = new Country();
        instance.country = country;
        return instance;
    }

    /**
     * Creates a new instance.
     */
    public Country() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return super.toString() + '{' +
               "countryId=" + countryId +
               ",country='" + country +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Country)) return false;
        return equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    Integer identifier() {
        return getCountryId();
    }

    // ------------------------------------------------------------------------------------------------------- countryId

    /**
     * Returns current value of {@link Country_#countryId countryId} attribute.
     *
     * @return current value of the {@link Country_#countryId countryId} attribute.
     */
    public Integer getCountryId() {
        return countryId;
    }

    /**
     * Replaces current value of {@link Country_#countryId countryId} attribute with specified value.
     *
     * @param countryId new value for the {@link Country_#countryId countryId} attribute.
     * @deprecated for removal; the column is an <em>auto-increment</em> column.
     */
    // TODO: remove!
    @Deprecated(forRemoval = true)
    private void setCountryId(final Integer countryId) {
        this.countryId = countryId;
    }

    // --------------------------------------------------------------------------------------------------------- country

    /**
     * Returns current value of {@link Country_#country country} attribute.
     *
     * @return current value of the {@link Country_#country country} attribute.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Replaces current value of {@link Country_#country country} attribute with specified value.
     *
     * @param country new value for the {@link Country_#country country} attribute.
     */
    public void setCountry(final String country) {
        this.country = country;
    }

    /**
     * Returns locales which each {@link Locale#getDisplayCountry(Locale) displayCountry(ENGLISH)} is equal to current
     * value of {@link Country_#country} attribute.
     *
     * @return a list of locales which each {@link Locale#getDisplayCountry(Locale) displayCountry(ENGLISH)} is equal to
     * current value of {@link Country_#country} attribute; {@code empty} if current value of {@link Country_#country}
     * attribute is {@code null} or no locales matched.
     * @see LocaleUtils#valuesOfDisplayCountryInEnglish(String)
     */
    @Transient
    public List<Locale> getLocalesForCountry() {
        return ofNullable(getCountry())
                .filter(v -> !v.isBlank())
                .map(LocaleUtils::valuesOfDisplayCountryInEnglish)
                .orElse(emptyList());
    }

    /**
     * Replaces current value of {@link Country_#country country} attribute with specified locale's
     * {@link Locale#getDisplayCountry(Locale) display country} represented in {@link Locale#ENGLISH ENGLISH}.
     *
     * @param locale the locale whose {@link Locale#getDisplayCountry(Locale) display country} value is used for the
     *               {@link Country_#country country} attribute; may be {@code null}.
     * @see Locale#getDisplayCountry(Locale)
     * @see Locale#ENGLISH
     */
    @Transient
    public void setCountryAsLocale(final Locale locale) {
        setCountry(
                ofNullable(locale)
                        .map(l -> l.getDisplayCountry(Locale.ENGLISH))
                        .orElse(null)
        );
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-country.html">
     * A surrogate primary key used to uniquely identify each country in the table.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_COUNTRY_ID, nullable = false,
            insertable = true, // EclipseLink
            updatable = false)
    private Integer countryId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-country.html">
     * The name of the country.
     * </blockquote>
     */
    @NotNull
    @Basic(optional = false)
    // TODO: use COLUMN_NAME_COUNTRY for the name element
    // TODO: use COLUMN_LENGTH_COUNTRY for the length element
    @Column(name = "country", nullable = false, length = 50)
    private String country;
}
