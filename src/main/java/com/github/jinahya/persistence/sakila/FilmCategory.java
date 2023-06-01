package com.github.jinahya.persistence.sakila;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@NamedQuery(name = FilmCategoryConstants.QUERY_FIND_ALL_BY_CATEGORY,
            query = """
                    SELECT e
                    FROM FilmCategory AS e
                    WHERE e.category = :category
                          AND e.id > :idMinExclusive
                    ORDER BY e.id ASC""")
@NamedQuery(name = FilmCategoryConstants.QUERY_FIND_ALL_BY_FILM,
            query = """
                    SELECT e
                    FROM FilmCategory AS e
                    WHERE e.film = :film
                          AND e.id > :idMinExclusive
                    ORDER BY e.id ASC""")
@NamedQuery(name = FilmCategoryConstants.QUERY_FIND_ALL_BY_ID_CATEGORY_ID,
            query = """
                    SELECT e
                    FROM FilmCategory AS e
                    WHERE e.categoryId = :categoryId
                          AND e.id > :idMinExclusive
                    ORDER BY e.id ASC""")
@NamedQuery(name = FilmCategoryConstants.QUERY_FIND_ALL_BY_ID_FILM_ID,
            query = """
                    SELECT e
                    FROM FilmCategory AS e
                    WHERE e.id.filmId = :idFilmId
                          AND e.id > :idMinExclusive
                    ORDER BY e.id ASC""")
@NamedQuery(name = FilmCategoryConstants.QUERY_FIND_ALL,
            query = """
                    SELECT e
                    FROM FilmCategory AS e
                    WHERE e.id > :idMinExclusive""")
@NamedQuery(name = FilmCategoryConstants.QUERY_FIND_BY_ID,
            query = """
                    SELECT e
                    FROM FilmCategory AS e
                    WHERE e.id = :id""")
//@IdClass(FilmCategoryId.class) // this class uses an embedded id
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

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString() + '{' +
               "id=" + id +
               "filmId=" + filmId +
               ",categoryId=" + categoryId +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FilmCategory)) return false;
        return equals(obj);
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
    public void setId(final FilmCategoryId id) {
        this.id = id;
    }

    /**
     * Returns current value of {@link FilmCategory_#filmId filmId} attribute.
     *
     * @return current value of the {@link FilmCategory_#filmId filmId} attribute.
     * @deprecated for removal, by {@link #id}
     */
    // TODO: remove!
    @Deprecated(forRemoval = true)
    Integer getFilmId() {
        return filmId;
    }

    /**
     * Replaces current value of {@link FilmCategory_#filmId filmId} attribute with specified value.
     *
     * @param filmId new value for the {@link FilmCategory_#filmId filmId} attribute.
     * @deprecated for removal, by {@link #id}
     */
    // TODO: remove!
    @Deprecated(forRemoval = true)
    void setFilmId(final Integer filmId) {
        this.filmId = filmId;
    }

    /**
     * Returns current value of {@link FilmCategory_#categoryId categoryId} attribute.
     *
     * @return current value of the {@link FilmCategory_#categoryId categoryId} attribute.
     * @deprecated for removal, by {@link #id}
     */
    // TODO: remove!
    @Deprecated(forRemoval = true)
    Integer getCategoryId() {
        return categoryId;
    }

    /**
     * Replaces current value of {@link FilmCategory_#categoryId categoryId} attribute with specified value.
     *
     * @param categoryId new current value for the {@link FilmCategory_#categoryId categoryId} attribute.
     * @deprecated for removal, by {@link #id}
     */
    // TODO: remove!
    @Deprecated(forRemoval = true)
    void setCategoryId(final Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * The embedded id of this entity.
     */
    @Valid
    @NotNull
    @EmbeddedId
    private FilmCategoryId id;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-film_category.html">
     * A foreign key identifying the film.
     * </blockquote>
     *
     * @deprecated for removal, by {@link #id}
     */
    // TODO: remove!
    @Deprecated(forRemoval = true)
    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    //@NotNull
    @Column(name = COLUMN_NAME_FILM_ID, nullable = false,
            insertable = false, // !!!
            updatable = false   // !!!
            )
    private Integer filmId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-film_category.html">
     * A foreign key identifying the category.
     * </blockquote>
     *
     * @deprecated for removal, by {@link #id}
     */
    // TODO: remove!
    @Deprecated(forRemoval = true)
    @Max(_DomainConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    //@NotNull
    @Column(name = COLUMN_NAME_CATEGORY_ID, nullable = false,
            insertable = false, // !!!
            updatable = false   // !!!
            )
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
     * @apiNote This method also replaces current value of {@link FilmCategory_#id id} attribute's
     * {@link FilmCategoryId_#filmId filmId} attribute with {@code film?.filmId}.
     */
    public void setFilm(final Film film) {
        this.film = film;
        setFilmId(
                Optional.ofNullable(this.film)
                        .map(Film::getFilmId)
                        .orElse(null)
        );
        Optional.ofNullable(id)
                .orElseGet(() -> id = new FilmCategoryId())
                .setFilmId(
                        Optional.ofNullable(this.film)
                                .map(Film::getFilmId)
                                .orElse(null)
                );
    }

    /**
     * 이 엔터티에 매핑된 영화(Film).
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
     * @apiNote Then method also replaces current value of {@link FilmCategory_#id id} attribute's
     * {@link FilmCategoryId_#categoryId categoryId} attribute with {@code category?.categoryId}.
     */
    public void setCategory(final Category category) {
        this.category = category;
        setCategoryId(
                Optional.ofNullable(this.category)
                        .map(Category::getCategoryId)
                        .orElse(null)
        );
        Optional.ofNullable(id)
                .orElseGet(() -> id = new FilmCategoryId())
                .setCategoryId(
                        Optional.ofNullable(this.category)
                                .map(Category::getCategoryId)
                                .orElse(null)
                );
    }

    /**
     * 이 엔터티에 매핑된 카테고리(Category).
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_CATEGORY_ID, nullable = false, insertable = false, updatable = false)
    private Category category;
}
