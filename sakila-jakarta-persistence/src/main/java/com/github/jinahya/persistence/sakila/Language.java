package com.github.jinahya.persistence.sakila;

import com.github.jinahya.persistence.sakila.util.LocaleUtils2;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.github.jinahya.persistence.sakila.LanguageConstants.QUERY_FIND_ALL;
import static com.github.jinahya.persistence.sakila.LanguageConstants.QUERY_FIND_BY_LANGUAGE_ID;
import static java.util.Optional.ofNullable;

/**
 * An entity class for mapping {@value #TABLE_NAME} table.
 * <p>
 * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-language.html">
 * <p>The {@value Language#TABLE_NAME} table is a lookup table listing the possible languages that films can have for
 * their language and original language values.</p>
 * <p>The {@value TABLE_NAME} table is referred to by the {@value Film#TABLE_NAME} table.</p>
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-language.html">5.1.12 The language
 * Table</a>
 */
@NamedQuery(
        name = QUERY_FIND_ALL,
        query = """
                SELECT e FROM Language AS e
                WHERE e.languageId > :languageIdMinExclusive
                ORDER BY e.languageId ASC"""
)
@NamedQuery(
        name = QUERY_FIND_BY_LANGUAGE_ID,
        query = """
                SELECT e
                FROM Language AS e
                WHERE e.languageId = :languageId"""
)
@Entity
@Table(name = Language.TABLE_NAME)
public class Language
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "language";

    /**
     * The name of the table column to which the {@link Language_#languageId languageId} attribute maps. That value is
     * {@value}.
     */
    public static final String COLUMN_NAME_LANGUAGE_ID = "language_id";

    /**
     * The name of the table column to which the {@link Language_#name name} attribute maps. That value is {@value}.
     */
    public static final String COLUMN_NAME_NAME = "name";

    /**
     * The length of the {@link #COLUMN_NAME_NAME} column. That value is {@value}.
     */
    public static final int COLUMN_LENGTH_NAME = 20;

    /**
     * Creates a new instance with specified value of {@link Language_#name name} attribute.
     *
     * @param name the value of the {@link Language_#name name} attribute.
     * @return a new instance with {@code name}.
     */
    public static Language of(final String name) {
        final var instance = new Language();
        instance.name = name;
        return instance;
    }

    /**
     * Creates a new instance.
     */
    public Language() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "languageId=" + languageId +
               ",name=" + name +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Language)) return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    Integer identifier() {
        return getLanguageId();
    }

    /**
     * Returns current value of {@link Language_#languageId languageId} attribute.
     *
     * @return current value of the {@link Language_#languageId languageId} attribute.
     */
    public Integer getLanguageId() {
        return languageId;
    }

    /**
     * Replaces current value of {@link Language_#languageId languageId} attribute with specified value.
     *
     * @param languageId new value for the {@link Language_#languageId languageId} attribute.
     * @deprecated for removal.
     */
    @Deprecated(forRemoval = true)
    private void setLanguageId(final Integer languageId) {
        throw new UnsupportedOperationException("the languageId attribute is a generated value");
    }

    /**
     * Returns current value of {@link Language_#name name} attribute.
     *
     * @return current value of the {@link Language_#name name} attribute.
     */
    public String getName() {
        return name;
    }

    /**
     * Replaces current value of {@link Language_#name name} attribute with specified value.
     *
     * @param name new value for the {@link Language_#name name} attribute.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-language.html">
     * Surrogate primary key used to uniquely identify each language.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_TINYINT_UNSIGNED)
    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_LANGUAGE_ID, nullable = false, insertable = true, updatable = false)
    private Integer languageId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-language.html">
     * The English name of the language.
     * </blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_NAME, nullable = false, length = COLUMN_LENGTH_NAME)
    private String name;

    /**
     * Returns an unmodifiable list of locales which each value of
     * {@link Locale#getDisplayLanguage(Locale) displayLanguage}, represented in {@link Locale#ENGLISH ENGLISH}, matches
     * current value of {@link Language_#name} attribute.
     *
     * @return a list of locales for current value of {@link Language_#name} attribute; may be empty if current value of
     * {@link Language_#name} is {@code null} {@link String#isBlank() blank}, or no locales matched.
     */
    public List<Locale> getLocalesForName() {
        return ofNullable(getName())
                .filter(v -> !v.isBlank())
                .map(LocaleUtils2::valuesOfDisplayLanguageInEnglish)
                .orElseGet(Collections::emptyList);
    }

    /**
     * Replaces current value of {@link Language_#name name} attribute with specified locale's
     * {@link Locale#getDisplayLanguage(Locale) displayLanguage} retrieved for {@link Locale#ENGLISH}.
     *
     * @param locale the locale whose {@link Locale#getDisplayLanguage(Locale) displayLanguage} is set on the
     *               {@link Language_#name name} attribute.
     * @see Locale#getDisplayLanguage(Locale)
     * @see Locale#ENGLISH
     */
    public void setNameAsLocale(final Locale locale) {
        setName(
                ofNullable(locale)
                        .map(l -> l.getDisplayLanguage(Locale.ENGLISH))
                        .orElse(null)
        );
    }
}
