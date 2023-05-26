package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * An entity class for mapping {@value Actor#TABLE_NAME} table.
 * <p>
 * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-actor.html">
 * The {@value Actor#TABLE_NAME} table lists information for all actors.<br/>The {@value Actor#TABLE_NAME} table is
 * joined to the {@value Film#TABLE_NAME} table by means of the {@value FilmActor#TABLE_NAME} table.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-actor.html">5.1.1 The actor Table</a>
 * @see ActorConstants
 */
@NamedQuery(name = ActorConstants.NAMED_QUERY_FIND_ALL_BY_LAST_NAME_ACTOR_ID_GREATER_THAN,
            query = """
                    SELECT e FROM Actor AS e
                    WHERE e.lastName = :lastName
                          AND e.actorId > :actorIdMinExclusive
                    ORDER BY e.actorId ASC
                    """)
@NamedQuery(name = ActorConstants.NAMED_QUERY_FIND_ALL_BY_LAST_NAME,
            query = """
                    SELECT e
                    FROM Actor AS e
                    WHERE e.lastName = :lastName
                    """)
@NamedQuery(name = ActorConstants.QUERY_FIND_ALL_BY_ACTOR_ID_GREATER_THAN,
            query = """
                    SELECT e
                    FROM Actor AS e
                    WHERE e.actorId > :actorIdMinExclusive
                    ORDER BY e.actorId ASC
                    """)
@NamedQuery(name = ActorConstants.NAMED_QUERY_FIND_BY_ACTOR_ID,
            query = """
                    SELECT e
                    FROM Actor AS e
                    WHERE e.actorId = :actorId
                    """)
@NamedQuery(name = ActorConstants.NAMED_QUERY_FIND_ALL,
            query = """
                    SELECT e
                    FROM Actor AS e
                    """)
@Entity
@Table(name = Actor.TABLE_NAME, indexes = {@Index(columnList = Actor.COLUMN_NAME_LAST_NAME)})
public class Actor
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "actor";

    /**
     * The name of the table column to which the {@link Actor_#actorId actorId} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_ACTOR_ID = "actor_id";

    /**
     * The name of the table column to which the {@link Actor_#firstName firstName} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_FIRST_NAME = "first_name";

    /**
     * The name of the table column to which the {@link Actor_#lastName lastName} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_LAST_NAME = "last_name";

    public static Actor of(final String firstName, final String lastName) {
        final var instance = new Actor();
        instance.firstName = firstName;
        instance.lastName = lastName;
        return instance;
    }

    /**
     * Creates a new instance.
     */
    public Actor() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "actorId=" + actorId +
               ",firstName=" + firstName +
               ",lastName=" + lastName +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Actor)) return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    Integer identifier() {
        return getActorId();
    }

    /**
     * Returns current value of {@value Actor_#ACTOR_ID} attribute.
     *
     * @return current value of {@value Actor_#ACTOR_ID} attribute.
     */
    public Integer getActorId() {
        return actorId;
    }

    /**
     * Replaces current value of {@value Actor_#ACTOR_ID} attribute with specified value.
     *
     * @param actorId new value for {@value Actor_#ACTOR_ID} attribute.
     * @deprecated for removal.
     */
    // TODO: remove!
    @Deprecated(forRemoval = true)
    private void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    /**
     * Returns current value of {@value Actor_#FIRST_NAME} attribute.
     *
     * @return current value of {@value Actor_#FIRST_NAME} attribute.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Replaces current value of {@value Actor_#FIRST_NAME} attribute with specified value.
     *
     * @param firstName new value for the {@value Actor_#FIRST_NAME} attribute.
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns current value of {@value Actor_#LAST_NAME} attribute.
     *
     * @return current value of {@value Actor_#LAST_NAME} attribute.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Replaces current value of {@value Actor_#LAST_NAME} attribute with specified value.
     *
     * @param lastName new value for the {@value Actor_#LAST_NAME} attribute.
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-actor.html">
     * A surrogate primary key used to uniquely identify each actor in the table.
     * </blockquote>
     */
    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_ACTOR_ID, nullable = false,
            insertable = /*false*/true, // EclipseLink
            updatable = false)
    private Integer actorId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-actor.html">
     * The actor first name.
     * </blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-actor.html">
     * The actor last name.
     * </blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LAST_NAME, nullable = false, length = 45)
    private String lastName;
}
