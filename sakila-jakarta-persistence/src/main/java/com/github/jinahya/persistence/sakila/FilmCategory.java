package com.github.jinahya.persistence.sakila;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.NATIVE_QUERY_SELECT_ALL_KEYSET;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.NATIVE_QUERY_SELECT_ALL_ROWSET;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.NATIVE_QUERY_SELECT_BY_FILM_ID_AND_CATEGORY_ID;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_ALL;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_ALL_BY_CATEGORY;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_ALL_BY_CATEGORY_CATEGORY_ID;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_ALL_BY_FILM;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_ALL_BY_FILM_FILM_ID;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_ALL_BY_ID_CATEGORY_ID;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_ALL_BY_ID_FILM_ID;
import static com.github.jinahya.persistence.sakila.FilmCategoryConstants.QUERY_FIND_BY_ID;
import static java.util.Optional.ofNullable;

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
 * @see FilmCategoryConstants
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-film_category.html">5.1.9 The film_category
 * Table</a>
 */
@NamedQuery(
        name = QUERY_FIND_ALL_BY_CATEGORY_CATEGORY_ID,
        query = """
                SELECT e
                FROM FilmCategory AS e
                WHERE e.category.categoryId = :categoryCategoryId
                      AND e.id.filmId > :idFilmIdMinExclusive
                ORDER BY e.id.filmId ASC"""
)
@NamedQuery(
        name = QUERY_FIND_ALL_BY_CATEGORY,
        query = """
                SELECT e
                FROM FilmCategory AS e
                WHERE e.category = :category
                      AND e.id.filmId > :idFilmIdMinExclusive
                ORDER BY e.id.filmId ASC"""
)
@NamedQuery(
        name = QUERY_FIND_ALL_BY_ID_CATEGORY_ID,
        query = """
                SELECT e
                FROM FilmCategory AS e
                WHERE e.id.categoryId = :idCategoryId
                      AND e.id.filmId > :idFilmIdMinExclusive
                ORDER BY e.id.filmId ASC"""
)
@NamedQuery(
        name = QUERY_FIND_ALL_BY_FILM,
        query = """
                SELECT e
                FROM FilmCategory AS e
                WHERE e.film = :film
                      AND e.id.categoryId > :idCategoryIdMinExclusive
                ORDER BY e.id.categoryId ASC"""
)
@NamedQuery(
        name = QUERY_FIND_ALL_BY_FILM_FILM_ID,
        query = """
                SELECT e
                FROM FilmCategory AS e
                WHERE e.film.filmId = :filmFilmId
                      AND e.id.categoryId > :idCategoryIdMinExclusive
                ORDER BY e.id.categoryId ASC"""
)
@NamedQuery(
        name = QUERY_FIND_ALL_BY_ID_FILM_ID,
        query = """
                SELECT e
                FROM FilmCategory AS e
                WHERE e.id.filmId = :idFilmId
                      AND e.id.categoryId > :idCategoryIdMinExclusive
                ORDER BY e.id.categoryId ASC"""
)
@NamedQuery(
        name = QUERY_FIND_ALL,
        query = """
                SELECT e
                FROM FilmCategory AS e
                WHERE (e.id.filmId = :idFilmIdMin AND e.id.categoryId > :idCategoryIdMinExclusive)
                   OR e.id.filmId > :idFilmIdMin
                ORDER BY e.id.filmId ASC, e.id.categoryId ASC"""
)
@NamedQuery(
        name = QUERY_FIND_BY_ID,
        query = """
                SELECT e
                FROM FilmCategory AS e
                WHERE e.id = :id"""
)
@NamedNativeQuery(
        name = NATIVE_QUERY_SELECT_ALL_KEYSET,
        query = """
                SELECT *
                FROM film_category
                WHERE (film_id = ? AND category_id > ?)
                   OR film_id > ?
                ORDER BY film_id ASC, category_id ASC
                LIMIT ?""",
        resultClass = FilmCategory.class
)
@NamedNativeQuery(
        name = NATIVE_QUERY_SELECT_ALL_ROWSET,
        query = """
                SELECT *
                FROM film_category
                ORDER BY film_id ASC, category_id ASC
                LIMIT ?,?""",
        resultClass = FilmCategory.class
)
@NamedNativeQuery(
        name = NATIVE_QUERY_SELECT_BY_FILM_ID_AND_CATEGORY_ID,
        query = """
                SELECT *
                FROM film_category AS e
                WHERE film_id = ? AND category_id = ?""",
        resultClass = FilmCategory.class
)
//@IdClass(FilmCategoryId.class) // this class uses an embedded id!
@Entity
@Table(name = FilmCategory.TABLE_NAME)
public class FilmCategory
        extends _BaseEntity<FilmCategoryId> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "film_category";

    /**
     * The name of the table column to which the {@link FilmCategoryId_#filmId id.filmId} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_FILM_ID = Film.COLUMN_NAME_FILM_ID;

    /**
     * The name of the table column to which the {@link FilmCategoryId_#categoryId id.categoryId} attribute maps. The
     * value is {@value}.
     */
    public static final String COLUMN_NAME_CATEGORY_ID = Category.COLUMN_NAME_CATEGORY_ID;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified film and category.
     *
     * @param film     the film for {@link FilmCategory_#film} attribute.
     * @param category the category for {@link FilmCategory_#category} attribute.
     * @return a new instance with {@code film} and {@code category}.
     */
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

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString() + '{' +
               "id=" + id +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmCategory that)) return false;
        return Objects.equals(id, that.id);
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
        return id;
    }

    // -------------------------------------------------------------------------------------------------------------- id

    /**
     * Returns current value of {@link FilmCategory_#id id} attribute.
     *
     * @return current value of the {@link FilmCategory_#id id} attribute.
     */
    public FilmCategoryId getId() {
        return id;
    }

    /**
     * Replaces current value of {@link FilmCategory_#id id} attribute with specified value.
     *
     * @param id new value for the {@link FilmCategory_#id id} attribute.
     */
    void setId(final FilmCategoryId id) {
        this.id = id;
    }

    // ------------------------------------------------------------------------------------------------------------ film
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
     * @apiNote This method also replaces current value of {@link FilmCategory_#id id} attribute's
     * {@link FilmCategoryId_#filmId filmId} attribute with {@code film?.filmId}.
     */
    public void setFilm(final Film film) {
        this.film = film;
        ofNullable(getId())
                .orElseGet(() -> id = new FilmCategoryId())
                .setFilmId(
                        ofNullable(this.film)
                                .map(Film::getFilmId)
                                .orElse(null)
                );
    }

    // -------------------------------------------------------------------------------------------------------- category
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
     * @apiNote Then method also replaces current value of {@link FilmCategory_#id id} attribute's
     * {@link FilmCategoryId_#categoryId categoryId} attribute with {@code category?.categoryId}.
     */
    public void setCategory(final Category category) {
        this.category = category;
        ofNullable(getId())
                .orElseGet(() -> id = new FilmCategoryId())
                .setCategoryId(
                        ofNullable(this.category)
                                .map(Category::getCategoryId)
                                .orElse(null)
                );
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The embedded id of this entity.
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-film_category.html">
     * film_id: A foreign key identifying the film.<br/>category_id: A foreign key identifying the category.
     * </blockquote>
     */
    @Valid
    @NotNull
    @EmbeddedId
    private FilmCategoryId id;

    /**
     * 이 엔터티에 매핑된 영화(Film).
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_FILM_ID, nullable = false, insertable = false, updatable = false)
    private Film film;

    /**
     * 이 엔터티에 매핑된 카테고리(Category).
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_CATEGORY_ID, nullable = false, insertable = false, updatable = false)
    private Category category;
}
