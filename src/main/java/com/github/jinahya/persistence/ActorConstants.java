package com.github.jinahya.persistence;

import jakarta.persistence.Query;

/**
 * Defines constants related to {@link Actor} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see Actor
 */
public final class ActorConstants {

    /**
     * The name of the query selects all entities. The value is {@value}.
     */
    public static final String NAMED_QUERY_FIND_ALL = "Actor_findAll";

    /**
     * The name of a named-query for selecting an entity identified by {@link Actor_#actorId actorId} attribute. The
     * value is {@value}.
     */
    public static final String NAMED_QUERY_FIND_BY_ACTOR_ID = "Actor_findByActorId";

    /**
     * The name of the query selects entities whose {@link Actor_#actorId actorId} attributes are greater than specific
     * value, ordered by {@link Actor_#actorId actorId} attribute in ascending order. The value is {@value}.
     * <p>
     * The JPQL and the equivalent SQL are as follows.
     * <table>
     * <head><tr><th>JPQL</th><th>(My)SQL</th></th></tr></head>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM actor AS e
     * WHERE e.actorId > :actorIdMinExclusive
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
     * <td>{@snippet lang = "java":
     * Query#setMaxResults(int) // @link target="Query#setMaxResults(int)"
     *}</td>
     * <td>{@snippet lang = "sql":
     * LIMIT ?
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see Actor_#actorId
     * @see Query#setMaxResults(int)
     * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/limit-optimization.html">8.2.1.19 LIMIT Query
     * Optimization</a> (MySQL)
     */
    public static final String QUERY_FIND_ALL_BY_ACTOR_ID_GREATER_THAN = "Actor_findAllByActorIdGreaterThan";

    public static final String NAMED_QUERY_FIND_ALL_BY_LAST_NAME = "Actor_findAllByLastName";

    /**
     * The name of the query selects entities whose {@link Actor_#lastName lastName} attributes match specified value,
     * and whose {@link Actor_#actorId actorId} attributes greater than specified value, ordered by
     * {@link Actor_#actorId actorId} attribute in ascending order. The value is {@value}.
     * <p>
     * The JPQL and the equivalent SQL are as follows.
     * <table>
     * <head><tr><th>JPQL</th><th>(My)SQL</th></th></tr></head>
     * <tbody>
     * <tr>
     * <td>{@snippet lang = "jpql":
     * SELECT e
     * FROM actor AS e
     * WHERE e.lastName = :lastName
     *       AND e.actorId > :actorIdMinExclusive
     * ORDER BY e.actorId ASC
     *}</td>
     * <td>{@snippet lang = "sql":
     * SELECT *
     * FROM actor
     * WHERE last_name = ?
     *       AND actor_id > ?
     * ORDER BY actor_id ASC
     *}</td>
     * </tr>
     * <tr>
     * <td>{@snippet lang = "java":
     * Query#setMaxResults(int) // @link target="Query#setMaxResults(int)"
     *}</td>
     * <td>{@snippet lang = "sql":
     * LIMIT ?
     *}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @see Actor_#lastName
     * @see Actor_#actorId
     * @see Query#setMaxResults(int)
     * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/limit-optimization.html">8.2.1.19 LIMIT Query
     * Optimization</a> (MySQL)
     */
    public static final String NAMED_QUERY_FIND_ALL_BY_LAST_NAME_ACTOR_ID_GREATER_THAN
            = "Actor_findAllByLastNameActorIdGreaterThan";

    private ActorConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
