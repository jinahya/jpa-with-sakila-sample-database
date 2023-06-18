package com.github.jinahya.sakila.persistence;

import jakarta.persistence.metamodel.Attribute;

import java.util.Optional;

/**
 * Defines constants related to the {@link Actor} class.
 * <p>
 * Predefined named queries, its JPQLs and equivalent SQLs are as follows.
 * <table>
 * <caption>Named queries, JPQLs, and SQLs</caption>
 * <thead><tr><th>Name</th><th>JPQL</th><th>SQL</th></tr></thead>
 * <tbody>
 * <tr>
 * <td>{@link #QUERY_FIND_BY_ACTOR_ID}<br/>({@value #QUERY_FIND_BY_ACTOR_ID})</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM Actor AS e
 * WHERE e.actorId = :actorId  // @link substring=".actorId" target="Actor_#actorId" @link substring=":actorId" target="#PARAMETER_ACTOR_ID"
 *}</td>
 * <td>{@snippet lang = "sql":
 * SELECT *
 * FROM actor
 * WHERE actor_id = ? // @link substring="actor_id" target="Actor#COLUMN_NAME_ACTOR_ID"
 *}</td>
 * </tr>
 * <tr>
 * <td>{@link #QUERY_FIND_ALL}<br/>({@value #QUERY_FIND_ALL})</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM Actor AS e
 * WHERE e.actorId > :actorIdMinExclusive // @link substring=":actorIdMinExclusive" target="#PARAMETER_ACTOR_ID_MIN_EXCLUSIVE"
 * ORDER BY e.actorId ASC
 *}</td>
 * <td>{@snippet lang = "sql":
 * SELECT *
 * FROM actor
 * WHERE actor_id > ?
 * ORDER BY actor_id ASC
 *}</td>
 * </tr>
 * <tr>
 * <td>{@link #QUERY_FIND_ALL_BY_LAST_NAME}<br/>({@value #QUERY_FIND_ALL_BY_LAST_NAME})</td>
 * <td>{@snippet lang = "jpql":
 * SELECT e
 * FROM Actor AS e
 * WHERE e.lastName = :lastName
 *       AND e.actorId > :actorIdMinExclusive
 * ORDER BY e.actorId ASC
 *}</td>
 * <td>{@snippet lang = "sql":
 * SELECT *
 * FROM actor
 * WHERE last_name = ? // @link substring="last_name" target="Actor#COLUMN_NAME_LAST_NAME"
 *       AND actor_id > ?
 * ORDER BY actor_id ASC
 *}</td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see Actor
 */
public final class ActorConstants {

    /**
     * The name of the query which selects a rorw whose value of {@value Actor#COLUMN_NAME_ACTOR_ID} column matches a
     * specific value. The value is {@value}.
     */
    public static final String NATIVE_QUERY_SELECT_BY_ACTOR_ID = "Actor_selectByActorId_Native";

    /**
     * The name of the query which selects all rows ordered by {@value Actor#COLUMN_NAME_ACTOR_ID} column in ascending
     * order. The value is {@value}.
     */
    public static final String NATIVE_QUERY_SELECT_ALL = "Actor_selectAll";

    /**
     * The name of the query which selects an entity whose value of {@link Actor_#actorId actorId} attribute matches a
     * specific value. The value is {@value}.
     *
     * @see #PARAMETER_ACTOR_ID
     */
    public static final String QUERY_FIND_BY_ACTOR_ID = "Actor_findByActorId";

    /**
     * The name of the query parameter for specifying a value of {@link Actor_#actorId actorId} attribute. The value is
     * {@value}.
     */
    public static final String PARAMETER_ACTOR_ID = "actorId";

    static {
        Optional.ofNullable(Actor_.actorId).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_ACTOR_ID);
        });
    }

    /**
     * The name of the query which selects all entities which each {@link Actor_#actorId actorId} attribute is greater
     * than a specific value, ordered by {@link Actor_#actorId actorId} attribute in ascending order. The value is
     * {@value}.
     *
     * @see Actor_#actorId
     * @see #PARAMETER_ACTOR_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL = "Actor_findAll";

    /**
     * The name of the query parameter for limiting lower exclusive value of {@link Actor_#actorId actorId} attribute.
     * The value is {@value}.
     */
    public static final String PARAMETER_ACTOR_ID_MIN_EXCLUSIVE = "actorIdMinExclusive";

    /**
     * The name of the query which selects entities which each {@link Actor_#lastName lastName} attribute matches
     * specified value, and each {@link Actor_#actorId actorId} attribute is greater than specified value, ordered by
     * {@link Actor_#actorId actorId} attribute in ascending order. The value is {@value}.
     *
     * @see Actor_#lastName
     * @see Actor_#actorId
     * @see #PARAMETER_ACTOR_ID_MIN_EXCLUSIVE
     */
    public static final String QUERY_FIND_ALL_BY_LAST_NAME = "Actor_findAllByLastName";

    /**
     * The name of the parameter for specifying a value of {@link Actor_#lastName lastName} attribute. The value is
     * {@value}.
     *
     * @see Actor_#lastName
     * @see #QUERY_FIND_ALL_BY_LAST_NAME
     */
    public static final String PARAMETER_LAST_NAME = "lastName";

    static {
        Optional.ofNullable(Actor_.lastName).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_LAST_NAME);
        });
    }

    /**
     * The name of the graph which fetches {@link Actor_#films films} attribute. The value is {@value}.
     *
     * @see #ATTRIBUTE_NODE_FILMS
     */
    public static final String ENTITY_GRAPH_FILMS = "Actor_films";

    /**
     * The name of the graph node denoting {@link Actor_#films} attribute.
     *
     * @see #ENTITY_GRAPH_FILMS
     */
    public static final String ATTRIBUTE_NODE_FILMS = "films";

    static {
        Optional.ofNullable(Actor_.films).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(ATTRIBUTE_NODE_FILMS);
        });
    }

    private ActorConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
