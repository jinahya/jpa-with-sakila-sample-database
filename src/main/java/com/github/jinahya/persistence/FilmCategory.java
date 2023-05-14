package com.github.jinahya.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;
import java.util.Optional;

/**
 * An entity class for mapping {@value #TABLE_NAME} table.
 * <p>
 * <blockquote>
 * The {@value #TABLE_NAME} table is used to support a many-to-many relationship between films and categories. For each
 * category applied to a film, there will be one row in the {@value #TABLE_NAME} table listing the category and
 * film.<br/> The {@value #TABLE_NAME} table refers to the {@value Film#TABLE_NAME} and {@link Category#TABLE_NAME}
 * tables using foreign keys.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
//@NamedQuery(name = "FilmCategory_findAllByCategory",
//            query = "SELECT e FROM FilmCategory AS e WHERE e.category = :category")
@NamedQuery(name = "FilmCategory_findAllByCategoryId",
            query = "SELECT e FROM FilmCategory AS e WHERE e.categoryId = :categoryId")
@NamedQuery(name = "FilmCategory_findAllByFilm",
            query = "SELECT e FROM FilmCategory AS e WHERE e.film = :film")
@NamedQuery(name = "FilmCategory_findAllByFilmId",
            query = "SELECT e FROM FilmCategory AS e WHERE e.filmId = :filmId")
@Entity
@Table(name = FilmCategory.TABLE_NAME)
@IdClass(FilmCategoryId.class)
public class FilmCategory
        extends _BaseEntity<FilmCategoryId> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "film_category";

    public static final String COLUMN_NAME_FILM_ID = Film.COLUMN_NAME_FILM_ID;

    public static final String COLUMN_NAME_CATEGORY_ID = Category.COLUMN_NAME_CATEGORY_ID;

    /**
     * Creates a new instance.
     */
    public FilmCategory() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "filmId=" + filmId +
               ",categoryId=" + categoryId +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FilmCategory that)) return false;
        return equals_(that);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifier());
    }

    @Override
    protected FilmCategoryId identifier() {
        return FilmCategoryId.of(filmId, categoryId);
    }

    /**
     * Returns current value of {@link FilmCategory_#filmId filmId} attribute.
     *
     * @return current value of {@link FilmCategory_#filmId filmId} attribute.
     */
    public Integer getFilmId() {
        return filmId;
    }

    /**
     * Replaces current value of {@link FilmCategory_#filmId filmId} attribute with specified value.
     *
     * @param filmId new value for the {@link FilmCategory_#filmId filmId} attribute.
     */
    private void setFilmId(final Integer filmId) {
        this.filmId = filmId;
    }

    /**
     * Returns current value of {@link FilmCategory_#categoryId categoryId} attribute.
     *
     * @return current value of {@link FilmCategory_#categoryId categoryId} attribute.
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * Replaces current value of {@link FilmCategory_#categoryId categoryId} attribute with specified value.
     *
     * @param categoryId new current value for the {@link FilmCategory_#categoryId categoryId} attribute.
     */
    private void setCategoryId(final Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * A foreign key identifying the film.
     */
    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_FILM_ID, nullable = false)
    private Integer filmId;

    /**
     * A foreign key identifying the category.
     */
    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_CATEGORY_ID, nullable = false)
    private Integer categoryId;

    /**
     * Returns current value of {@link FilmCategory_#film film} attribute.
     *
     * @return current value of {@link FilmCategory_#film film} attribute.
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Replaces current value of {@link FilmCategory_#film film} attribute with specified value.
     *
     * @param film new value for the {@link FilmCategory_#film film} attribute.
     */
    public void setFilm(final Film film) {
        this.film = film;
        setFilmId(
                Optional.ofNullable(film)
                        .map(Film::getFilmId)
                        .orElse(null)
        );
    }

    @Valid
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_FILM_ID, nullable = false, insertable = false, updatable = false)
    private Film film;

    // TODO: Map for the Category!
}
