package com.github.jinahya.persistence.sakila;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;
import java.util.Optional;

/**
 * An entity class for mapping {@value #TABLE_NAME} table.
 * <p>
 * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-film_category.html">
 * The {@value #TABLE_NAME} table is used to support a many-to-many relationship between films and categories. For each
 * category applied to a film, there will be one row in the {@value #TABLE_NAME} table listing the category and
 * film.<br/>The {@value #TABLE_NAME} table refers to the {@value Film#TABLE_NAME} and {@value Category#TABLE_NAME}
 * tables using foreign keys.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see FilmCategoryId
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-film_category.html">5.1.9 The film_category
 * Table</a>
 */
@NamedQuery(name = "FilmCategory_findAllByCategory",
            query = "SELECT e FROM FilmCategory AS e WHERE e.category = :category")
@NamedQuery(name = "FilmCategory_findAllByFilm",
            query = "SELECT e FROM FilmCategory AS e WHERE e.film = :film")
@NamedQuery(name = "FilmCategory_findAllByCategoryId",
            query = "SELECT e FROM FilmCategory AS e WHERE e.categoryId = :categoryId")
@NamedQuery(name = "FilmCategory_findAllByFilmId",
            query = "SELECT e FROM FilmCategory AS e WHERE e.filmId = :filmId")
@IdClass(FilmCategoryId.class)
@Entity
@Table(name = FilmCategory.TABLE_NAME)
public class FilmCategory
        extends _BaseEntity<FilmCategoryId> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "film_category";

    /**
     * The name of the table column to which the {@link FilmCategory_#filmId filmId} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_FILM_ID = Film.COLUMN_NAME_FILM_ID;

    /**
     * The name of the table column to which the {@link FilmCategory_#categoryId categoryId} attribute maps. The value
     * is {@value}.
     */
    public static final String COLUMN_NAME_CATEGORY_ID = Category.COLUMN_NAME_CATEGORY_ID;

    public static FilmCategory of(final Film film, final Category category) {
        final var instance = new FilmCategory();
        instance.setFilm(film);
        instance.setCategory(category);
        return instance;
    }

    /**
     * Creates a new instance.
     */
    public FilmCategory() {
        super();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    FilmCategoryId identifier() {
        return FilmCategoryId.of(filmId, categoryId);
    }

    /**
     * Returns current value of {@link FilmCategory_#filmId filmId} attribute.
     *
     * @return current value of the {@link FilmCategory_#filmId filmId} attribute.
     */
    public Integer getFilmId() {
        return filmId;
    }

    /**
     * Replaces current value of {@link FilmCategory_#filmId filmId} attribute with specified value.
     *
     * @param filmId new value for the {@link FilmCategory_#filmId filmId} attribute.
     */
    void setFilmId(final Integer filmId) {
        this.filmId = filmId;
    }

    /**
     * Returns current value of {@link FilmCategory_#categoryId categoryId} attribute.
     *
     * @return current value of the {@link FilmCategory_#categoryId categoryId} attribute.
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * Replaces current value of {@link FilmCategory_#categoryId categoryId} attribute with specified value.
     *
     * @param categoryId new current value for the {@link FilmCategory_#categoryId categoryId} attribute.
     */
    void setCategoryId(final Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-film_category.html">
     * A foreign key identifying the film.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_FILM_ID, nullable = false)
    private Integer filmId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-film_category.html">
     * A foreign key identifying the category.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_TINYINT_UNSIGNED)
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
     * @apiNote This method also replaces current value of {@link FilmCategory_#filmId filmId} with
     * {@code film?.filmId}.
     */
    public void setFilm(final Film film) {
        this.film = film;
        setFilmId(
                Optional.ofNullable(this.film)
                        .map(Film::getFilmId)
                        .orElse(null)
        );
    }

    /**
     * 이 매핑 엔터티의 영화.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_FILM_ID, nullable = false, insertable = false, updatable = false)
    private Film film;

    /**
     * Returns current value of {@link FilmCategory_#category category} attribute.
     *
     * @return current value of the {@link FilmCategory_#category category} attribute.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Replaces current value of {@link FilmCategory_#category category} attribute with specified value.
     *
     * @param category new value for the {@link FilmCategory_#category category} attribute.
     * @apiNote Then method also replaces current value of {@link FilmCategory_#categoryId categoryId} attribute with
     * {@code category?.categoryId}.
     */
    public void setCategory(final Category category) {
        this.category = category;
        setCategoryId(
                Optional.ofNullable(this.category)
                        .map(Category::getCategoryId)
                        .orElse(null)
        );
    }

    /**
     * 이 매핑 엔터티의 카테고리.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_CATEGORY_ID, nullable = false, insertable = false, updatable = false)
    private Category category;
}
