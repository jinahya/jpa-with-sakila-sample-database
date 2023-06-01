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
     * <table>
     * <head><tr><th>JPQL</th><th>(My)SQL</th></th></tr></head>
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
        Optional.ofNullable(FilmCategory_.id)
                .map(Attribute::getName)
                .ifPresent(v -> {
                    assert v.equals(QUERY_PARAM_ID);
                });
    }

    public static final String QUERY_FIND_ALL = "FilmCategory_findAll";

    public static final String QUERY_PARAM_ID_MIN_EXCLUSIVE = "idMinExclusive";

    /**
     * The name of the query selects all entities whose {@link FilmCategoryId_#filmId id.filmId} attributes match
     * specified value, ordered by {@link FilmCategory_#id id} attribute in ascending order.
     * <p>
     * The JPQL and an equivalent SQL are as follows.
     * <table>
     * <head><tr><th>JPQL</th><th>(My)SQL</th></th></tr></head>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM FilmCategory AS e
     * WHERE e.id.filmId = :idFilmId
     *       AND e.id > :idMinExclusive
     * ORDER BY e.id ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM film_category
     * WHERE film_id = ?
     *       AND ((film_id = ? AND category_id > ?) OR film_id > ?)
     * ORDER BY film_id ASC, category_id ASC
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see FilmCategory_#id
     * @see FilmCategoryId_#filmId
     * @see #QUERY_PARAM_ID_FILM_ID
     * @see #QUERY_PARAM_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL_BY_ID_FILM_ID = "FilmCategory_findAllByIdFilmId";

    public static final String QUERY_PARAM_ID_FILM_ID = "idFilmId";

    public static final String QUERY_FIND_ALL_BY_ID_CATEGORY_ID = "FilmCategory_findAllByIdCategoryId";

    public static final String QUERY_PARAM_ID_CATEGORY_ID = "idCategoryId";

    public static final String QUERY_FIND_ALL_BY_FILM = "FilmCategory_findAllByFilm";

    public static final String QUERY_FIND_ALL_BY_CATEGORY = "FilmCategory_findAllByCategory";

    private FilmCategoryConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
