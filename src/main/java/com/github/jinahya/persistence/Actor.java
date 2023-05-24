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
 * <blockquote>
 * The {@value Actor#TABLE_NAME} table lists information for all actors. <br/> The {@value Actor#TABLE_NAME} table is
 * joined to the {@value Film#TABLE_NAME} table by means of the {@value FilmActor#TABLE_NAME} table.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-actor.html">5.1.1 The actor Table</a>
 * @see ActorConstants
 */
@NamedQuery(name = ActorConstants.NAMED_QUERY_FIND_ALL_BY_LAST_NAME_ACTOR_ID_GREATER_THAN,
            query = "SELECT a FROM Actor AS a"
                    + " WHERE a.lastName = :lastName AND a.actorId > :actorIdMinExclusive"
                    + " ORDER BY a.actorId ASC")
@NamedQuery(name = ActorConstants.NAMED_QUERY_FIND_ALL_BY_LAST_NAME,
            query = "SELECT a FROM Actor AS a WHERE a.lastName = :lastName")
@NamedQuery(name = ActorConstants.NAMED_QUERY_FIND_ALL_BY_ACTOR_ID_GREATER_THAN,
            query = "SELECT a FROM Actor AS a WHERE a.actorId > :actorIdMinExclusive ORDER BY a.actorId ASC")
@NamedQuery(name = ActorConstants.NAMED_QUERY_FIND_ALL,
            query = "SELECT a FROM Actor AS a")
@NamedQuery(name = ActorConstants.NAMED_QUERY_FIND_BY_ACTOR_ID,
            query = "SELECT a FROM Actor AS a WHERE a.actorId = :actorId")
@Entity
@Table(name = Actor.TABLE_NAME, indexes = {@Index(columnList = Actor.COLUMN_NAME_LAST_NAME)})
public class Actor
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "actor";

    /**
     * The name of the table column to which the {@value Actor_#ACTOR_ID} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_ACTOR_ID = "actor_id";

    /**
     * The name of the table column to which the {@value Actor_#FIRST_NAME} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_FIRST_NAME = "first_name";

    /**
     * The name of the table column to which the {@value Actor_#LAST_NAME} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_LAST_NAME = "last_name";

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
     * A surrogate primary key used to uniquely identify each actor in the table.
     */
    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_ACTOR_ID, nullable = false,
            insertable = true, // EclipseLink
            updatable = false)
    private Integer actorId;

    /**
     * The actor first name.
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    /**
     * The actor last name.
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LAST_NAME, nullable = false, length = 45)
    private String lastName;
}
