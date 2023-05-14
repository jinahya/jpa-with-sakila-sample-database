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
import jakarta.validation.constraints.PositiveOrZero;

/**
 * An entity class for mapping {@value Category#TABLE_NAME} table.
 * <p>
 * <blockquote>
 * The category table lists the categories that can be assigned to a film.<br/>The category table is joined to the
 * {@value Film#TABLE_NAME} table by means of the {@value MappedFilmCategory#TABLE_NAME} table.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-category.html">5.1.3 The category Table</a>
 */
@NamedQuery(name = "Category_findAllByName", query = "SELECT c FROM Category AS c WHERE c.name = :name") // not indexed!
@NamedQuery(name = "Category_findAll", query = "SELECT c FROM Category AS c")
@NamedQuery(name = "Category_findByCategoryId", query = "SELECT c FROM Category AS c WHERE c.categoryId = :categoryId")
@Entity
@Table(name = Category.TABLE_NAME)
public class Category
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "category";

    /**
     * The name of the table column to which the {@value Category_#CATEGORY_ID} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_CATEGORY_ID = "category_id";

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
    protected Integer identifier() {
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
    private void setCategoryId(final Integer categoryId) {
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
     * A surrogate primary key used to uniquely identify each category in the table.
     */
    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_CATEGORY_ID, nullable = false,
            insertable = true, // EclipseLink
            updatable = false)
    private Integer categoryId;

    /**
     * The name of the category.
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 25)
    private String name;
}
