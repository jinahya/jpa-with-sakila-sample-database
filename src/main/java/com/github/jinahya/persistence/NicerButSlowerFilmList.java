package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/**
 * An entity class for mapping {@value #VIEW_NAME} view.
 * <p>
 * <blockquote>
 * <cite><a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-views-nicer_film_list.html">5.2.4 The
 * nicer_but_slower_film_list View</a></cite>
 * <p>The {@code nicer_but_slower_film_list} view contains a formatted view of the film table, with a comma-separated
 * list of the film's actors.</p>
 * <p>The {@code nicer_but_slower_film_list} view differs from the {@value FilmList#VIEW_NAME}
 * view in the list of actors. The lettercase of the actor names is adjusted so that the first letter of each name is
 * capitalized, rather than having the name in all-caps.</p>
 * <p>As indicated in its name, the {@code nicer_but_slower_film_list} view performs additional processing and
 * therefore takes longer to return data than the {@value FilmList#VIEW_NAME} view.</p>
 * <p>The {@code nicer_but_slower_film_list} view incorporates data from the {@value Film#TABLE_NAME},
 * {@value Category#TABLE_NAME}, {@value FilmCategory#TABLE_NAME}, {@value Actor#TABLE_NAME}, and
 * {@value FilmActor#TABLE_NAME} tables.</p>
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @apiNote This class is not annotated with {@link jakarta.persistence.Entity} due to the absence of column(s) suitable
 * for uniquely identifying instances of this class.
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-views-nicer_film_list.html">5.2.4 The
 * nicer_but_slower_film_list View</a>
 */
@MappedSuperclass
//@jakarta.persistence.Entity
public class NicerButSlowerFilmList {

    /**
     * The name of the database view to which this class maps. The value is {@value}.
     */
    public static final String VIEW_NAME = "nicer_but_slower_film_list";

    /**
     * The name of the view column to which the {@link NicerButSlowerFilmList_#fid fid} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_FID = "FID";

    /**
     * Creates a new instance.
     */
    protected NicerButSlowerFilmList() {
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

    public Film.Rating getRating() {
        return rating;
    }

    public String getActors() {
        return actors;
    }

    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_FID, nullable = false, insertable = false, updatable = false)
    private Integer fid;

    @NotNull
    @Basic(optional = false)
    @Column(name = "title", nullable = false, length = 128, insertable = false, updatable = false)
    private String title;

    @Basic(optional = true)
    @Column(name = "description", nullable = true, length = _PersistenceConstants.COLUMN_LENGTH_TEXT,
            insertable = false, updatable = false)
    private String description;

    @Basic(optional = false)
    @Column(name = "category", nullable = true, length = 25, insertable = false, updatable = false)
    private String category;

    @NotNull
    @Basic(optional = false)
    @Column(name = "price", nullable = false, precision = Film.COLUMN_PRECISION_RENTAL_RATE,
            scale = Film.COLUMN_SCALE_RENTAL_RATE, insertable = false, updatable = false)
    private BigDecimal price;

    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Basic(optional = true)
    @Column(name = "length", nullable = true, insertable = false, updatable = false)
    private Integer length;

    @Convert(converter = Film.RatingConverter.class)
    @Basic(optional = true)
    @Column(name = "rating", nullable = true)
    private Film.Rating rating;

    @Basic(optional = true)
    @Column(name = "actors", nullable = true, length = _PersistenceConstants.COLUMN_LENGTH_TEXT)
    private String actors;
}
