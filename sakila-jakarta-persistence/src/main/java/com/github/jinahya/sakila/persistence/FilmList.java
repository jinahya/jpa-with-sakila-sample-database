package com.github.jinahya.sakila.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * <s>An entity</s>A class for mapping {@value FilmList#VIEW_NAME} view.
 * <p>
 * <blockquote>
 * The {@value FilmList#VIEW_NAME} view contains a formatted view of the {@value Film#TABLE_NAME} table, with a
 * comma-separated list of actors for each film. <br/> The {@value FilmList#VIEW_NAME} view incorporates data from the
 * {@value Film#TABLE_NAME}, {@value Category#TABLE_NAME}, {@value FilmCategory#TABLE_NAME}, {@value Actor#TABLE_NAME},
 * and {@value FilmActor#TABLE_NAME} tables.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @apiNote This class is not annotated with {@link jakarta.persistence.Entity} due to the absence of column(s) suitable
 * for uniquely identifying instances of this class.
 * @see <a href="https://bugs.mysql.com/bug.php?id=111095">Enhance film_list view by GROUP_CONCATing category.name
 * column values</a>
 */
@MappedSuperclass
//@jakarta.persistence.Entity // no columns(s) for uniquely identifying instances.
@Table(name = FilmList.VIEW_NAME)
public class FilmList {

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

    /**
     * The name of the view column to which the {@link FilmList_#description description} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_DESCRIPTION = Film.COLUMN_NAME_DESCRIPTION;

    /**
     * The name of the view column to which the {@link FilmList_#category category} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_CATEGORY = "category";

    /**
     * The name of the view column to which the {@link FilmList_#length length} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_LENGTH = Film.COLUMN_NAME_LENGTH;

    /**
     * The name of the view column to which the {@link FilmList_#rating rating} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_RATING = Film.COLUMN_NAME_RATING;

    /**
     * Creates a new instance.
     */
    public FilmList() {
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

    //    public Film.Rating getRating() {
//        return rating;
//    }
    public String getRating() {
        return rating;
    }

    public String getActors() {
        return actors;
    }

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_FID, nullable = false, insertable = false, updatable = false)
    private Integer fid;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_TITLE, nullable = false, length = 128, insertable = false, updatable = false)
    private String title;

    @Basic(optional = true)
    @Column(name = COLUMN_NAME_DESCRIPTION, nullable = true, length = _DomainConstants.COLUMN_LENGTH_TEXT,
            insertable = false, updatable = false)
    private String description;

    @Basic(optional = true)
    @Column(name = COLUMN_NAME_CATEGORY, nullable = true, length = 25, insertable = false, updatable = false)
    private String category;

    @NotNull
    @Basic(optional = false)
    @Column(name = "price", nullable = false, precision = 4, scale = 2, insertable = false, updatable = false)
    private BigDecimal price;

    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Basic(optional = true)
    @Column(name = COLUMN_NAME_LENGTH, nullable = true, insertable = false, updatable = false)
    private Integer length;

    //    @Convert(converter = Film.RatingConverter.class)
//    @Basic(optional = true)
//    @Column(name = COLUMN_NAME_RATING, nullable = true, insertable = false, updatable = false)
//    private Film.Rating rating;
    @Basic(optional = true)
    @Column(name = COLUMN_NAME_RATING, nullable = true, insertable = false, updatable = false)
    private String rating;

    @Basic(optional = true)
    @Column(name = "actors", nullable = true, length = _DomainConstants.COLUMN_LENGTH_TEXT, insertable = false,
            updatable = false)
    private String actors;

    @Transient
    public Set<String> getCategories() {
        if (categories == null) {
            categories = new HashSet<>();
            if (category != null && !category.isBlank()) {
                categories.add(category.strip());
            }
        }
        return categories;
    }

    @Transient
    private Set<@NotBlank String> categories;
}
