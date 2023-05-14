package com.github.jinahya.persistence;

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

import java.util.Locale;
import java.util.Optional;

/**
 * An entity class for mapping {@value #TABLE_NAME} table.
 * <p>
 * <blockquote>
 * The {@value TABLE_NAME} table contains a list of countries.<br/>The {@value TABLE_NAME} table is referred to by a
 * foreign key in the {@value MappedCity#TABLE_NAME} table.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-country.html">5.1.5 The country Table</a>
 */
@NamedQuery(name = "Country_findAll",
            query = "SELECT e FROM Country AS e")
@NamedQuery(name = "Country_findByCountryId",
            query = "SELECT e FROM Country AS e WHERE e.countryId = :countryId")
@Entity
@Table(name = Country.TABLE_NAME)
public class Country
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "country";

    /**
     * The name of the table column to which the {@link Country_#countryId countryId} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_COUNTRY_ID = "country_id";

    /**
     * Creates a new instance.
     */
    public Country() {
        super();
    }

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
        if (!(obj instanceof Country that)) return false;
        return equals_(that);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    protected Integer identifier() {
        return getCountryId();
    }

    public Integer getCountryId() {
        return countryId;
    }

    @Deprecated(forRemoval = true)
    private void setCountryId(final Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    /**
     * A surrogate primary key used to uniquely identify each country in the table.
     */
    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_COUNTRY_ID, nullable = false,
            insertable = true, // EclipseLink
            updatable = false)
    private Integer countryId;

    /**
     * The name of the country.
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "country", nullable = false, length = 50)
    private String country;

    /**
     * 현재 이 객체가 가지고 있는 {@link Country_#country country} attribute 의 값을 {@link Locale} 값으로 반환한다.
     *
     * @return {@link Country_#country country} attribute 값에 해당하는 {@link Locale} 값; {@link Country_#country country}
     * attribute 값이 {@code null} 이거나 적절한 {@link Locale} 값을 찾을 수 없으면 {@code null}.
     */
    @Transient
    public Locale getCountryAsLocale() {
        // TODO: implement!
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * 현재 이 객체가 가지고 있는 {@link Country_#country country} attribute 의 값을 명시된 {@link Locale} 값의
     * {@link Locale#getDisplayCountry(Locale) displayCountry(ENGLISH)} 값으로 변경한다.
     *
     * @param locale {@link Country_#country country} attribute 에 저장될 {@link Locale} 값.
     * @see Locale#getDisplayCountry(Locale)
     * @see Locale#ENGLISH
     */
    public void setCountryAsLocale(final Locale locale) {
        setCountry(
                Optional.ofNullable(locale)
                        .map(l -> l.getDisplayCountry(Locale.ENGLISH))
                        .orElse(null)
        );
    }
}
