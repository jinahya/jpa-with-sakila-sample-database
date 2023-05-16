package com.github.jinahya.persistence;

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
 * <blockquote>
 * The {@value Language#TABLE_NAME} table is a lookup table listing the possible languages that films can have for their
 * language and original language values.<br/>The {@value TABLE_NAME} table is referred to by the
 * {@value Film#TABLE_NAME} table.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-language.html">5.1.12 The language
 * Table</a>
 */
@NamedQuery(name = "Language_findAll", query = "SELECT c FROM Language AS c")
@NamedQuery(name = "Language_findByName", query = "SELECT c FROM Language AS c WHERE c.name = :name") // not indexed!
@Entity
@Table(name = Language.TABLE_NAME)
public class Language
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "language";

    /**
     * The name of the table column to which the {@value Language_#LANGUAGE_ID} attribute maps. That value is {@value}.
     */
    public static final String COLUMN_NAME_LANGUAGE_ID = "language_id";

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
    protected Integer identifier() {
        return getLanguageId();
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(final Integer languageId) {
        this.languageId = languageId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Surrogate primary key used to uniquely identify each language.
     */
    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_LANGUAGE_ID, nullable = false, insertable = true, updatable = false)
    private Integer languageId;

    /**
     * The English name of the language.
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    /**
     * Replaces current value of {@value Language_#NAME} attribute with specified locale's
     * {@link Locale#getDisplayLanguage(Locale) display language} retrieved for {@link Locale#ENGLISH}.
     *
     * @param locale the locale whose {@link Locale#getDisplayLanguage(Locale) display language} is set on the
     *               {@value Language_#NAME} attribute.
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
