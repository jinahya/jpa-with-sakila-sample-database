package com.github.jinahya.persistence.sakila;

import jakarta.persistence.metamodel.Attribute;

import static com.github.jinahya.persistence.sakila.Film_.filmId;
import static com.github.jinahya.persistence.sakila.Film_.language;
import static com.github.jinahya.persistence.sakila.Film_.languageId;
import static com.github.jinahya.persistence.sakila.Film_.originalLanguage;
import static com.github.jinahya.persistence.sakila.Film_.originalLanguageId;
import static java.util.Optional.ofNullable;

/**
 * Defines constants related to {@link Film} entity.
 * <p>
 * Predefined named queries, its JPQLs and equivalent SQLs are as follows.
 * <table>
 * <caption>Named queries, JPQLs, and SQLs</caption>
 * <thead><tr><th>Name</th><th>JPQL</th><th>SQL</th></tr></thead>
 * <tbody>
 * <tr>
 * <td>{@link #QUERY_FIND_BY_FILM_ID}<br/>({@value #QUERY_FIND_BY_FILM_ID})</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM Film AS e
 * WHERE e.filmId = :filmId  // @link substring=".filmId" target="Film_#filmId" @link substring=":filmId" target="#PARAMETER_FILM_ID"
 *}</td>
 * <td>{@snippet lang = "sql":
 * SELECT *
 * FROM film
 * WHERE film_id = ? // @link substring="film_id" target="Film#COLUMN_NAME_FILM_ID"
 *}</td>
 * </tr>
 * <tr>
 * <td>{@link #QUERY_FIND_ALL}<br/>({@value #QUERY_FIND_ALL})</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM Film AS e
 * WHERE e.filmId > :filmIdMinExclusive // @link substring=":filmIdMinExclusive" target="#PARAMETER_FILM_ID_MIN_EXCLUSIVE"
 * ORDER BY e.filmId ASC
 *}</td>
 * <td>{@snippet lang = "sql":
 * SELECT *
 * FROM film
 * WHERE film_id > ?
 * ORDER BY film_id ASC
 *}</td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see Film
 */
public final class FilmConstants {

    public static final String QUERY_FIND_BY_FILM_ID = "Film_findByFilmId";

    public static final String PARAMETER_FILM_ID = "filmId";

    static {
        ofNullable(filmId).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_FILM_ID);
        });
    }

    public static final String QUERY_FIND_ALL = "Film_findAll";

    public static final String PARAMETER_FILM_ID_MIN_EXCLUSIVE = "filmIdMinExclusive";

    public static final String QUERY_FIND_ALL_BY_LANGUAGE_ID = "Film_findAllByLanguageId";

    public static final String PARAMETER_LANGUAGE_ID = "languageId";

    static {
        ofNullable(languageId).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_LANGUAGE_ID);
        });
    }

    public static final String QUERY_FIND_ALL_BY_LANGUAGE = "Film_findAllByLanguage";

    public static final String PARAMETER_LANGUAGE = "language";

    static {
        ofNullable(language).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_LANGUAGE);
        });
    }

    public static final String QUERY_FIND_ALL_BY_ORIGINAL_LANGUAGE_ID = "Film_findAllByOriginalLanguageId";

    public static final String PARAMETER_ORIGINAL_LANGUAGE_ID = "originalLanguageId";

    static {
        ofNullable(originalLanguageId).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_ORIGINAL_LANGUAGE_ID);
        });
    }

    public static final String QUERY_FIND_ALL_BY_ORIGINAL_LANGUAGE = "Film_findAllByOriginalLanguage";

    public static final String PARAMETER_ORIGINAL_LANGUAGE = "originalLanguage";

    static {
        ofNullable(originalLanguage).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_ORIGINAL_LANGUAGE);
        });
    }

    public static final String QUERY_FIND_ALL_BY_TITLE = "Film_findAllByTitle";

    public static final String PARAMETER_TITLE = "title";

    static {
        ofNullable(Film_.title).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_TITLE);
        });
    }

    public static final String QUERY_FIND_ALL_BY_TITLE_LIKE = "Film_findAllByTitleLike";

    public static final String PARAMETER_TITLE_PATTERN = "titlePattern";

    private FilmConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
