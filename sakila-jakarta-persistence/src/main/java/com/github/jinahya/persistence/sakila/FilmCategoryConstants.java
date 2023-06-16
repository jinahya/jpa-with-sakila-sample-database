package com.github.jinahya.persistence.sakila;

import jakarta.persistence.metamodel.Attribute;

import static com.github.jinahya.persistence.sakila.FilmCategory_.category;
import static com.github.jinahya.persistence.sakila.FilmCategory_.film;
import static com.github.jinahya.persistence.sakila.FilmCategory_.id;
import static java.util.Optional.ofNullable;

/**
 * Defines constants related to {@link FilmCategory} class.
 * <p>
 * Named queries, JPQLs, and equivalent SQLs are as follows.
 * <table summery="Named queries, JPQLs, and SQLs">
 * <thead><tr><th>Name</th><th>JPQL</th><th>(My)SQL</th></tr></thead>
 * <tbody>
 * <tr>
 * <td>{@link #QUERY_FIND_BY_ID}<br/>{@value #QUERY_FIND_BY_ID}</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM FilmCategory AS e
 * WHERE e.id = :id // @highlight substring="e.id"
 *}</td>
 * <td><span id="selectByFilmIdAndCategoryId">{@snippet lang = "sql":
 * SELECT *
 * FROM film_category
 * WHERE film_id = ? AND category_id = ? // @highlight substring="film_id" @highlight substring="category_id"
 *}</span>
 * </tr>
 * <tr>
 * <td>{@link #QUERY_FIND_ALL}<br/>{@value #QUERY_FIND_ALL}</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM FilmCategory AS e
 * WHERE (e.id.filmId = :idFilmIdMin // @highlight substring="e.id.filmId"
 *        AND e.id.categoryId > :idCategoryIdMinExclusive) // @highlight substring="e.id.categoryId"
 *    OR e.id.filmId > :filmIdMin
 * ORDER BY e.id.filmId ASC, e.id.categoryId ASC
 *
 *}</td>
 * <td>{@snippet lang = "sql":
 * SELECT *
 * FROM film_category
 * WHERE (film_id = ? // @highlight substring="film_id"
 *        AND category_id > ?) // @highlight substring="category_id"
 *    OR film_id > ?
 * ORDER BY film_id ASC, category_id ASC
 * LIMIT ?
 *}</td>
 * </tr>
 * </tr>
 * <tr>
 * <td>{@link #QUERY_FIND_ALL_BY_ID_FILM_ID}<br/>{@value #QUERY_FIND_ALL_BY_ID_FILM_ID}</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM FilmCategory AS e
 * WHERE e.id.filmId = :idFilmId // @highlight substring="e.id.filmId" @link substring=":idFilmId" target="#PARAMETER_ID_FILM_ID"
 *   AND e.id.categoryId > :idCategoryIdMinExclusive
 * ORDER BY e.id.categoryId ASC
 *
 *}</td>
 * <td>{@snippet lang = "sql":
 * SELECT *
 * FROM film_category
 * WHERE film_id = ?
 *   AND category_id > ?
 * ORDER BY category_id ASC
 * LIMIT ?
 *}</td>
 * </tr>
 * <tr>
 * <td>{@link #QUERY_FIND_ALL_BY_FILM_FILM_ID}<br/>{@value #QUERY_FIND_ALL_BY_FILM_FILM_ID}</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM FilmCategory AS e
 * WHERE e.film.filmId = :filmFilmId // @highlight substring="e.film.filmId"
 *   AND e.id.categoryId > :idCategoryIdMinExclusive
 * ORDER BY e.id.categoryId ASC
 *}</td>
 * <td style="text-align: center">?</td>
 * </tr>
 * <tr>
 * <td>{@link #QUERY_FIND_ALL_BY_FILM}<br/>{@value #QUERY_FIND_ALL_BY_FILM}</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM FilmCategory AS e
 * WHERE e.film = :film // @highlight substring="e.film"
 *   AND e.id.categoryId > :idCategoryIdMinExclusive
 * ORDER BY e.id.categoryId ASC
 *}</td>
 * <td style="text-align: center">?</td>
 * </tr>
 * <tr>
 * <td>{@link #QUERY_FIND_ALL_BY_ID_CATEGORY_ID}<br/>{@value #QUERY_FIND_ALL_BY_ID_CATEGORY_ID}</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM CategoryFilm AS e
 * WHERE e.id.categoryId = :idCategoryId // @highlight substring="e.id.categoryId" @link substring=":idCategoryId" target="#PARAMETER_ID_CATEGORY_ID"
 *   AND e.id.filmId > :idFilmIdMinExclusive
 * ORDER BY e.id.filmId ASC
 *
 *}</td>
 * <td>{@snippet lang = "sql":
 * SELECT *
 * FROM film_category
 * WHERE category_id = ?
 *   AND film_id > ?
 * ORDER BY film_id ASC
 * LIMIT ?
 *}</td>
 * </tr>
 * <tr>
 * <td>{@link #QUERY_FIND_ALL_BY_CATEGORY_CATEGORY_ID}<br/>{@value #QUERY_FIND_ALL_BY_CATEGORY_CATEGORY_ID}</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM CategoryFilm AS e
 * WHERE e.category.categoryId = :categoryCategoryId // @highlight substring="e.category.categoryId"
 *   AND e.id.filmId > :idFilmIdMinExclusive
 * ORDER BY e.id.filmId ASC
 *}</td>
 * <td style="text-align: center">?</td>
 * </tr>
 * <tr>
 * <td>{@link #QUERY_FIND_ALL_BY_CATEGORY}<br/>{@value #QUERY_FIND_ALL_BY_CATEGORY}</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM CategoryFilm AS e
 * WHERE e.category = :category // @highlight substring="e.category"
 *   AND e.id.filmId > :idFilmIdMinExclusive
 * ORDER BY e.id.filmId ASC
 *}</td>
 * <td style="text-align: center">?</td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see FilmCategory
 */
public class FilmCategoryConstants {

    /**
     * The name of the query selects the entity whose {@link FilmCategory_#id id} attribute matches specified value.
     *
     * @see FilmCategory#COLUMN_NAME_FILM_ID
     * @see FilmCategory#COLUMN_NAME_CATEGORY_ID
     */
    public static final String NATIVE_QUERY_SELECT_BY_FILM_ID_AND_CATEGORY_ID =
            "FilmCategory_selectByFilmIdAndCategoryId";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the query selects all entities, ordered by {@value FilmCategory#COLUMN_NAME_FILM_ID} column in
     * ascending order, {@value FilmCategory#COLUMN_NAME_CATEGORY_ID } column in ascending order.
     *
     * @see FilmCategory#COLUMN_NAME_FILM_ID
     * @see FilmCategory#COLUMN_NAME_CATEGORY_ID
     */
    public static final String NATIVE_QUERY_SELECT_ALL_ROWSET = "FilmCategory_selectAll_rowset";

    /**
     * The name of the query selects all entities, ordered by {@value FilmCategory#COLUMN_NAME_FILM_ID} column in
     * ascending order, {@value FilmCategory#COLUMN_NAME_CATEGORY_ID } column in ascending order.
     *
     * @see FilmCategory#COLUMN_NAME_FILM_ID
     * @see FilmCategory#COLUMN_NAME_CATEGORY_ID
     */
    public static final String NATIVE_QUERY_SELECT_ALL_KEYSET = "FilmCategory_selectAll_keyset";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the query selects the entity whose {@link FilmCategory_#id id} attribute matches a specific value.
     *
     * @see #PARAMETER_ID
     */
    public static final String QUERY_FIND_BY_ID = "FilmCategory_findById";

    public static final String PARAMETER_ID = "id";

    static {
        ofNullable(id).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_ID);
        });
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the query selects all entities, order ed by {@link FilmCategoryId_#filmId filmId} and
     * {@link FilmCategoryId_#categoryId categoryId}, both in ascending order.
     *
     * @see #PARAMETER_ID_FILM_ID_MIN
     * @see #PARAMETER_ID_CATEGORY_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL = "FilmCategory_findAll";

    public static final String PARAMETER_ID_FILM_ID_MIN = "idFilmIdMin";

    public static final String PARAMETER_ID_CATEGORY_ID_MIN_EXCLUSIVE = "idCategoryIdMinExclusive";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the query selects all entities whose {@link FilmCategoryId_#filmId id.filmId} attributes match a
     * specific value, ordered by {@link FilmCategoryId_#categoryId id.categoryId} attribute in ascending order.
     *
     * @see #PARAMETER_ID_FILM_ID
     * @see #PARAMETER_ID_CATEGORY_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL_BY_ID_FILM_ID = "FilmCategory_findAllByIdFilmId";

    public static final String PARAMETER_ID_FILM_ID = "idFilmId";

    /**
     * The name of the query selects all entities whose {@link Film_#filmId film.filmId} attributes match a specific
     * value, ordered by {@link Category_#categoryId category.categoryId} attribute in ascending order.
     *
     * @see #PARAMETER_FILM_FILM_ID
     */
    public static final String QUERY_FIND_ALL_BY_FILM_FILM_ID = "FilmCategory_findAllByFilmFilmId";

    public static final String PARAMETER_FILM_FILM_ID = "filmFilmId";

    static {
        ofNullable(Film_.filmId).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_FILM_FILM_ID);
        });
    }

    /**
     * The name of the query selects all entities whose {@link FilmCategory_#film film} attributes match a specific
     * value, ordered by {@link FilmCategoryId_#categoryId id.categoryId} attribute in ascending order.
     *
     * @see #PARAMETER_FILM
     * @see #PARAMETER_ID_CATEGORY_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL_BY_FILM = "FilmCategory_findAllByFilm";

    public static final String PARAMETER_FILM = "film";

    static {
        ofNullable(film).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_FILM);
        });
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the query selects all entities whose {@link FilmCategoryId_#categoryId id.caetgoryId} attributes
     * match a specific value, ordered by {@link FilmCategoryId_#filmId id.filmId} attribute in ascending order.
     *
     * @see #PARAMETER_ID_CATEGORY_ID
     * @see #PARAMETER_ID_FILM_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL_BY_ID_CATEGORY_ID = "FilmCategory_findAllByIdCategoryId";

    public static final String PARAMETER_ID_CATEGORY_ID = "idCategoryId";

    public static final String PARAMETER_ID_FILM_ID_MIN_EXCLUSIVE = "idFilmIdMinExclusive";

    /**
     * The name of the query selects all entities whose {@link FilmCategory_#category caetgory} attributes match a
     * specific value, ordered by {@link FilmCategoryId_#filmId id.filmId} attribute in ascending order.
     *
     * @see #PARAMETER_CATEGORY
     * @see #PARAMETER_ID_FILM_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL_BY_CATEGORY = "FilmCategory_findAllByCategory";

    /**
     * The name of the query selects all entities whose {@link Category_#categoryId category.categoryId} attributes
     * match a specific value, ordered by {@link FilmCategoryId_#filmId id.filmId} attribute in ascending order.
     *
     * @see #PARAMETER_FILM_FILM_ID
     */
    public static final String QUERY_FIND_ALL_BY_CATEGORY_CATEGORY_ID = "FilmCategory_findAllByCategoryCategoryId";

    public static final String PARAMETER_CATEGORY_CATEGORY_ID = "categoryCategoryId";

    static {
        ofNullable(Category_.categoryId).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_CATEGORY_CATEGORY_ID);
        });
    }

    /**
     * The name of the parameter for specifying a value of {@link FilmCategory_#category} attribute. The value is
     * {@value}.
     *
     * @see #QUERY_FIND_ALL_BY_CATEGORY
     */
    public static final String PARAMETER_CATEGORY = "category";

    static {
        ofNullable(category).ifPresent(v -> {
            assert v.getName().equals(PARAMETER_CATEGORY);
        });
    }

    // -----------------------------------------------------------------------------------------------------------------
    private FilmCategoryConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
