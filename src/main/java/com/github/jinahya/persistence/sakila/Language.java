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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Locale;
import java.util.Optional;

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
@NamedQuery(name = LanguageConstants.NAMED_QUERY_FIND_ALL_BY_NAME,
            query = "SELECT e FROM Language AS e WHERE e.name = :name") // not indexed!
@NamedQuery(name = LanguageConstants.NAMED_QUERY_FIND_BY_LANGUAGE_ID,
            query = "SELECT e FROM Language AS e WHERE e.languageId = :languageId")
@NamedQuery(name = LanguageConstants.NAMED_QUERY_FIND_ALL_BY_LANGUAGE_ID_GREATER_THAN,
            query = "SELECT e FROM Language AS e" +
                    " WHERE e.languageId > :languageIdMinExclusive" +
                    " ORDER BY e.languageId ASC")
@NamedQuery(name = LanguageConstants.NAMED_QUERY_FIND_ALL,
            query = "SELECT e FROM Language AS e")
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

    public static final String COLUMN_NAME_NAME = "name";

    public static final int COLUMN_LENGTH_NAME = 20;

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
     * @deprecated for removal
     */
    @Deprecated
    private void setLanguageId(final Integer languageId) {
        if (true) {
            throw new UnsupportedOperationException("the languageId attribute is a generated value");
        }
        this.languageId = languageId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-language.html">
     * Surrogate primary key used to uniquely identify each language.
     * </blockquote>
     */
    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
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
     * Returns the locale represents current value of {@link Language_#name name} attribute.
     *
     * @return the locale represents current value of {@link Language_#name name} attribute; {@code null} if not found.
     */
    public Locale getNameAsLocale() {
        return Optional.ofNullable(getName())
                .flatMap(LocaleUtils::valueOfDisplayLanguageInEnglish)
                .orElse(null);
    }

    /**
     * Replaces current value of {@link Language_#name name} attribute with specified locale's
     * {@link Locale#getDisplayLanguage(Locale) display language} retrieved for {@link Locale#ENGLISH}.
     *
     * @param locale the locale whose {@link Locale#getDisplayLanguage(Locale) display language} is set on the
     *               {@link Language_#name name} attribute.
     * @see Locale#getDisplayLanguage(Locale)
     * @see Locale#ENGLISH
     */
    public void setNameAsLocale(final Locale locale) {
        setName(
                Optional.ofNullable(locale)
                        .map(l -> l.getDisplayLanguage(Locale.ENGLISH))
                        .orElse(null)
        );
    }
}
