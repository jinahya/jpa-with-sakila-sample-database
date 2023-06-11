package com.github.jinahya.persistence.sakila;

import jakarta.persistence.metamodel.Attribute;

import java.util.Optional;

/**
 * Defines constants related to {@link FilmCategory} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see FilmCategory
 */
public class FilmCategoryConstants {

    /**
     * The name of the query selects the entity whose {@link FilmCategory_#id id} attribute matches specified value.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table summery="Named queries, JPQLs, and SQLs">
     * <thead><tr><th>JPQL</th><th>(My)SQL</th></tr></thead>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM FilmCategory AS e
     * WHERE e.id = :id
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM film_category
     * WHERE film_id = ? AND category_id = ?
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see FilmCategory_#id
     * @see #QUERY_PARAM_ID
     */
    public static final String QUERY_FIND_BY_ID = "FilmCategory_findById";

    public static final String QUERY_PARAM_ID = "id";

    static {
        Optional.ofNullable(FilmCategory_.id).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(QUERY_PARAM_ID);
        });
    }

    /**
     * The name of the query selects all entities.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <thead><tr><th>JPQL</th><th>(My)SQL</th></tr></thead>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM FilmCategory AS e
     * WHERE (e.id.filmId = :idFilmIdMin AND e.id.categoryId > :idCategoryIdMinExclusive)
     *       OR e.id.filmId > :idFilmIdMin
     * ORDER BY e.id.filmId ASC, e.id.categoryId ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM film_category
     * WHERE (film_id = ? AND category_id > ?)
     *       OR film_id ?
     * ORDER BY film_id ASC, category_id ASC
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see FilmCategory_#id
     * @see FilmCategoryId_#filmId
     * @see FilmCategoryId_#categoryId
     * @see #QUERY_PARAM_ID_FILM_ID_MIN
     * @see #QUERY_PARAM_ID_CATEGORY_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL = "FilmCategory_findAll";

    public static final String QUERY_PARAM_ID_FILM_ID_MIN = "idFilmIdMin";

    public static final String QUERY_PARAM_ID_CATEGORY_ID_MIN_EXCLUSIVE = "idCategoryIdMinExclusive";

    /**
     * The name of the query selects all entities whose {@link FilmCategoryId_#filmId id.filmId} attributes match
     * specified value, ordered by {@link FilmCategoryId_#categoryId id.categoryId} attribute in ascending order.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <thead><tr><th>JPQL</th><th>(My)SQL</th></tr></thead>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM FilmCategory AS e
     * WHERE e.id.filmId = :idFilmId
     *       AND e.id.categoryId > :idCategoryIdMinExclusive
     * ORDER BY e.id.categoryId ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM film_category
     * WHERE film_id = ?
     *       AND category_id > ?
     * ORDER BY category_id ASC
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see FilmCategory_#id
     * @see FilmCategoryId_#filmId
     * @see FilmCategoryId_#categoryId
     * @see #QUERY_PARAM_ID_FILM_ID
     * @see #QUERY_PARAM_ID_CATEGORY_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL_BY_ID_FILM_ID = "FilmCategory_findAllByIdFilmId";

    public static final String QUERY_PARAM_ID_FILM_ID = "idFilmId";

    /**
     * The name of the query selects all entities whose {@link FilmCategoryId_#categoryId id.caetgoryId} attributes
     * match specified value, ordered by {@link FilmCategoryId_#filmId id.filmId} attribute in ascending order.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <thead><tr><th>JPQL</th><th>(My)SQL</th></tr></thead>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM FilmCategory AS e
     * WHERE e.id.categoryId = :idCategoryId
     *       AND e.id.filmId > :idFilmIdMinExclusive
     * ORDER BY e.id.filmId ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM film_category
     * WHERE category_id = ?
     *       AND film_id > ?
     * ORDER BY film_id ASC
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see FilmCategory_#id
     * @see FilmCategoryId_#filmId
     * @see FilmCategoryId_#categoryId
     * @see #QUERY_PARAM_ID_CATEGORY_ID
     * @see #QUERY_PARAM_ID_FILM_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL_BY_ID_CATEGORY_ID = "FilmCategory_findAllByIdCategoryId";

    public static final String QUERY_PARAM_ID_CATEGORY_ID = "idCategoryId";

    public static final String QUERY_PARAM_ID_FILM_ID_MIN_EXCLUSIVE = "idFilmIdMinExclusive";

    /**
     * The name of the query selects all entities whose {@link FilmCategory_#film film} attributes match specified
     * value, ordered by {@link FilmCategoryId_#categoryId id.categoryId} attribute in ascending order.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <thead><tr><th>JPQL</th><th>(My)SQL</th></tr></thead>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM FilmCategory AS e
     * WHERE e.filmI= :film
     *       AND e.id.categoryId > :idCategoryIdMinExclusive
     * ORDER BY e.id.categoryId ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM film_category
     * WHERE film_id = ?
     *       AND category_id > ?
     * ORDER BY category_id ASC
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see FilmCategory_#film
     * @see FilmCategoryId_#categoryId
     * @see #QUERY_PARAM_FILM
     * @see #QUERY_PARAM_ID_CATEGORY_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL_BY_FILM = "FilmCategory_findAllByFilm";

    public static final String QUERY_PARAM_FILM = "film";

    static {
        Optional.ofNullable(FilmCategory_.film).ifPresent(v -> {
            assert v.getName().equals(QUERY_PARAM_FILM);
        });
    }

    /**
     * The name of the query selects all entities whose {@link FilmCategory_#category caetgory} attributes match
     * specified value, ordered by {@link FilmCategoryId_#filmId id.filmId} attribute in ascending order.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <thead><tr><th>JPQL</th><th>(My)SQL</th></tr></thead>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM FilmCategory AS e
     * WHERE e.category = :category
     *       AND e.id.filmId > :idFilmIdMinExclusive
     * ORDER BY e.id.filmId ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM film_category
     * WHERE category_id = ?
     *       AND film_id > ?
     * ORDER BY film_id ASC
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see FilmCategory_#category
     * @see FilmCategoryId_#filmId
     * @see #QUERY_PARAM_CATEGORY
     * @see #QUERY_PARAM_ID_FILM_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL_BY_CATEGORY = "FilmCategory_findAllByCategory";

    /**
     * The name of the query param for denoting {@link FilmCategory_#category} attribute. The value is {@value}.
     *
     * @see #QUERY_FIND_ALL_BY_CATEGORY
     */
    public static final String QUERY_PARAM_CATEGORY = "category";

    static {
        Optional.ofNullable(FilmCategory_.category).ifPresent(v -> {
            assert v.getName().equals(QUERY_PARAM_CATEGORY);
        });
    }

    private FilmCategoryConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
