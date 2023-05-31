package com.github.jinahya.persistence.sakila;

import com.github.jinahya.persistence.sakila.service.ActorService;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

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
 * @see ActorService
 */
@NamedQuery(name = ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME_ACTOR_ID_GREATER_THAN,
            query = """
                    SELECT e FROM Actor AS e
                    WHERE e.lastName = :lastName
                          AND e.actorId > :actorIdMinExclusive
                    ORDER BY e.actorId ASC
                    """)
@NamedQuery(name = ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME,
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
@NamedQuery(name = ActorConstants.QUERY_FIND_BY_ACTOR_ID,
            query = """
                    SELECT e
                    FROM Actor AS e
                    WHERE e.actorId = :actorId
                    """)
@NamedQuery(name = ActorConstants.QUERY_FIND_ALL,
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
     * The name of the table column to which the {@link Actor_#lastName lastName} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_LAST_NAME = "last_name";

    /**
     * The length of the {@value #COLUMN_NAME_LAST_NAME} column. The value is {@value}.
     */
    public static final int COLUMN_LENGTH_LAST_NAME = 45;

    /**
     * Creates a new instance with specified {@link Actor_#actorId actorId} attribute value.
     *
     * @param actorId the value of {@link Actor_#actorId actorId} attribute.
     * @return a new instance with {@code actorId}.
     */
    static Actor ofActorId(final Integer actorId) {
        final var instance = new Actor();
        instance.actorId = actorId;
        return instance;
    }

    /**
     * Creates a new instance with specified arguments.
     *
     * @param firstName a value for {@link Actor_#firstName firstName} attribute
     * @param lastName  a value for {@link Actor_#lastName lastName} attribute.
     * @return a new instance with {@code firstName} and {@code lastName}.
     */
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
     * Returns current value of {@link Actor_#actorId actorId} attribute.
     *
     * @return current value of {@link Actor_#actorId actorId} attribute.
     */
    public Integer getActorId() {
        return actorId;
    }

    /**
     * Replaces current value of {@link Actor_#actorId actorId} attribute with specified value.
     *
     * @param actorId new value for {@link Actor_#actorId actorId} attribute.
     * @deprecated for removal; the column is an auto-increment column.
     */
    // TODO: remove!
    @Deprecated(forRemoval = true)
    private void setActorId(final Integer actorId) {
        this.actorId = actorId;
    }

    /**
     * Returns current value of {@link Actor_#firstName firstName} attribute.
     *
     * @return current value of {@link Actor_#firstName firstName} attribute.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Replaces current value of {@link Actor_#firstName firstName} attribute with specified value.
     *
     * @param firstName new value for the {@link Actor_#firstName firstName} attribute.
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns current value of {@link Actor_#lastName lastName} attribute.
     *
     * @return current value of {@link Actor_#lastName lastName} attribute.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Replaces current value of {@link Actor_#lastName lastName} attribute with specified value.
     *
     * @param lastName new value for the {@link Actor_#lastName lastName} attribute.
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-actor.html">
     * A surrogate primary key used to uniquely identify each actor in the table.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
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
    @Column(name = COLUMN_NAME_LAST_NAME, nullable = false, length = COLUMN_LENGTH_LAST_NAME)
    private String lastName;

    static final String ATTRIBUTE_NAME_FILMS = "films";

//    static {
//        Optional.ofNullable(Actor_.films).map(Attribute::getName).ifPresent(v -> {
//            assert v.equals(ATTRIBUTE_NAME_FILMS);
//        });
//    }

    /**
     * 이 배우가 출연한 영화 목록.
     */
    @ManyToMany(
            cascade = {
            },
            fetch = FetchType.LAZY, // default
//            mappedBy = Film.ATTRIBUTE_NAME_ACTORS,
            targetEntity = Film.class
    )
    @JoinTable(
            name = FilmActor.TABLE_NAME,
            joinColumns = {
                    @JoinColumn(name = FilmActor.COLUMN_NAME_ACTOR_ID)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = FilmActor.COLUMN_NAME_FILM_ID)
            }
    )
    private List<@Valid @NotNull Film> films;
}
