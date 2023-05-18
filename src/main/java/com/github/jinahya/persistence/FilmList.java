package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * An entity class for mapping {@value FilmList#VIEW_NAME} view.
 * <p>
 * <blockquote>
 * The {@value FilmList#VIEW_NAME} view contains a formatted view of the {@value Film#TABLE_NAME} table, with a
 * comma-separated list of actors for each film. <br/> The {@value FilmList#VIEW_NAME} view incorporates data from the
 * {@value Film#TABLE_NAME}, {@value Category#TABLE_NAME}, {@value FilmCategory#TABLE_NAME}, {@value Actor#TABLE_NAME},
 * and {@value FilmActor#TABLE_NAME} tables.
 * </blockquote>
 */
@NamedQuery(name = "FilmList_findAllByFid",
            query = "SELECT e FROM FilmList AS e WHERE e.fid = :fid")
@IdClass(FilmListId.class)
@Entity
@Table(name = FilmList.VIEW_NAME)
public class FilmList
        extends __BaseEntity<FilmListId> {

    /**
     * The name of the database view to which this class maps. The value is {@value}.
     */
    public static final String VIEW_NAME = "film_list";

    /**
     * The name of the view column to which the {@link FilmList_#fid fid} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_FID = "FID";

    /**
     * The name of the view column to which the {@link FilmList_#title title} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_TITLE = Film.COLUMN_NAME_TITLE;

    public static final String COLUMN_NAME_DESCRIPTION = Film.COLUMN_NAME_DESCRIPTION;

    public static final String COLUMN_NAME_CATEGORY = "category";

    public static final String COLUMN_NAME_LENGTH = Film.COLUMN_NAME_LENGTH;

    /**
     * The name of the view column to which the {@value FilmList_#RATING} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_RATING = Film.COLUMN_NAME_RATING;

    /**
     * Creates a new instance.
     */
    protected FilmList() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "fid=" + fid +
               ",title=" + title +
               ",description=" + description +
               ",category=" + category +
               ",price=" + price +
               ",length=" + length +
               ",rating=" + rating +
               ",actors=" + actors +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FilmList that)) return false;
        return equals_(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier());
    }

    @Override
    protected FilmListId identifier() {
        return FilmListId.of(getFid(), getCategory());
    }

    public Integer getFid() {
        return fid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getLength() {
        return length;
    }

    public Film.Rating getRating() {
        return rating;
    }

    public String getActors() {
        return actors;
    }

    @NotNull
    @Id
    @Basic
    @Column(name = COLUMN_NAME_FID, nullable = false,
            insertable = /*false*/true, // EclipseLink
            updatable = false)
    private Integer fid;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_TITLE, nullable = false, length = 128, insertable = false, updatable = false)
    private String title;

    @Basic(optional = true)
    @Column(name = COLUMN_NAME_DESCRIPTION, nullable = true, length = _PersistenceConstants.COLUMN_LENGTH_TEXT,
            insertable = false, updatable = false)
    private String description;

    @Id
    @Basic(optional = true)
    @Column(name = COLUMN_NAME_CATEGORY, nullable = true, length = 25,
            insertable = /*false*/true, // EclipseLink
            updatable = false)
    private String category;

    @NotNull
    @Basic(optional = false)
    @Column(name = "price", nullable = false, precision = 4, scale = 2, insertable = false, updatable = false)
    private BigDecimal price;

    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Basic(optional = true)
    @Column(name = COLUMN_NAME_LENGTH, nullable = true, insertable = false, updatable = false)
    private Integer length;

    @Convert(converter = Film.RatingConverter.class)
    @Basic(optional = true)
    @Column(name = COLUMN_NAME_RATING, nullable = true, insertable = false, updatable = false)
    private Film.Rating rating;

    @Basic(optional = true)
    @Column(name = "actors", nullable = true, length = _PersistenceConstants.COLUMN_LENGTH_TEXT, insertable = false, updatable = false)
    private String actors;

    public Set<String> getCategories() {
        return categories;
    }

    void setCategories(final Set<String> categories) {
        this.categories = categories;
    }

    @Transient
    private Set<@NotBlank String> categories;
}
