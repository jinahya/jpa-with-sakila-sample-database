package com.github.jinahya.sakila.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import static com.github.jinahya.sakila.persistence._DomainConstants.MAX_TINYINT_UNSIGNED;
import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * An entity class for mapping {@value Category#TABLE_NAME} table.
 * <p>
 * <cite><a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-category.html">5.1.3 The category
 * Table</a></cite>
 * <blockquote>
 * <p>The category table lists the categories that can be assigned to a film.</p>
 * <p>The category table is joined to the {@value Film#TABLE_NAME} table by means of the
 * {@value FilmCategory#TABLE_NAME} table.</p>
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-category.html">5.1.3 The category Table</a>
 */
@NamedQuery(name = CategoryConstants.QUERY_FIND_ALL,
            query = """
                    SELECT e
                    FROM Category AS e
                    WHERE e.categoryId > :categoryIdMinExclusive
                    ORDER BY e.categoryId ASC""")
@NamedQuery(name = CategoryConstants.QUERY_FIND_BY_CATEGORY_ID,
            query = """
                    SELECT e
                    FROM Category AS e
                    WHERE e.categoryId = :categoryId""")
@Entity
@Table(name = Category.TABLE_NAME)
public class Category
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "category";

    /**
     * The name of the table column to which the {@link Category_#categoryId categoryId} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_CATEGORY_ID = "category_id";

    static Category ofCategoryId(final Integer categoryId) {
        final var instance = new Category();
        instance.categoryId = categoryId;
        return instance;
    }

    public static Category of(final String name) {
        final var instance = new Category();
        instance.name = name;
        return instance;
    }

    /**
     * Creates a new instance.
     */
    public Category() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "categoryId=" + categoryId +
               ",name='" + name +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Category)) return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    Integer identifier() {
        return getCategoryId();
    }

    /**
     * Returns current value of {@value Category_#CATEGORY_ID} attribute.
     *
     * @return current value of {@value Category_#CATEGORY_ID} attribute.
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    @Deprecated(forRemoval = true)
    @_VisibleForTesting
    void setCategoryId(final Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Returns current value of {@value Category_#NAME} attribute.
     *
     * @return current value of {@value Category_#NAME} attribute.
     */
    public String getName() {
        return name;
    }

    /**
     * Replaces current value of {@value Category_#NAME} attribute with specified value.
     *
     * @param name new value for {@value Category_#NAME} attribute.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-category.html">
     * A surrogate primary key used to uniquely identify each category in the table.
     * </blockquote>
     */
    @Max(MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = COLUMN_NAME_CATEGORY_ID, nullable = false,
            insertable = true, // EclipseLink
            updatable = false)
    private Integer categoryId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-category.html">
     * The name of the category.
     * </blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 25)
    private String name;
}
