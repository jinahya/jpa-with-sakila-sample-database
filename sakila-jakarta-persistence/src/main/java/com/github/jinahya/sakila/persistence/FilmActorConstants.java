package com.github.jinahya.sakila.persistence;

/**
 * Defines constants related to {@link FilmActor} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see FilmActor
 */
public final class FilmActorConstants {

    public static final String QUERY_FIND_BY_ID = "FilmActor_findById";

    public static final String QUERY_FIND_ALL = "FilmActor_findAll";

    public static final String QUERY_FIND_ALL_ID_GREATER_THAN = "FilmActor_findAllIdGreaterThan";

    public static final String QUERY_PARAM_ID_MIN_EXCLUSIVE = "filmActorIdMinExclusive";

    public static final String QUERY_FIND_ALL_BY_ACTOR_ID = "FilmActor_findAllByActorId";

    public static final String QUERY_FIND_ALL_BY_FILM_ID = "FilmActor_findAllByFilmId";

    private FilmActorConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
