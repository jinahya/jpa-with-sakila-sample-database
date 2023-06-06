package com.github.jinahya.persistence.sakila;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;
import java.util.Optional;

/**
 * An entity class for mapping {@value #TABLE_NAME} table.
 * <p>
 * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-film_actor.html">
 * The {@value #TABLE_NAME} table is used to support a many-to-many relationship between films and actors. For each
 * actor in a given film, there will be one row in the {@value #TABLE_NAME} table listing the actor and film.<br/>The
 * {@value #TABLE_NAME} table refers to the {@value Film#TABLE_NAME} and {@value Actor#TABLE_NAME} tables using foreign
 * keys.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-film_actor.html">5.1.8 The film_actor
 * Table</a>
 * @see FilmActorConstants
 */
//@NamedQuery(name = FilmActorConstants.QUERY_FIND_ALL_BY_FILM_ID,
//            query = """
//                    SELECT e
//                    FROM FilmActor AS e
//                    WHERE e.filmId = :filmId
//                          AND id > :idMinExclusive
//                    ORDER BY e.id ASC""")
//@NamedQuery(name = FilmActorConstants.QUERY_FIND_ALL_BY_ACTOR_ID,
//            query = """
//                    SELECT e
//                    FROM FilmActor AS e
//                    WHERE e.actorId = :actorId
//                          AND id > :idMinExclusive
//                    ORDER BY e.id ASC""")
//@NamedQuery(name = FilmActorConstants.QUERY_FIND_ALL_ID_GREATER_THAN,
//            query = """
//                    SELECT e
//                    FROM FilmActor AS e
//                    WHERE e.id > :idMinExclusive
//                    ORDER BY e.id ASC""")
//@NamedQuery(name = FilmActorConstants.QUERY_FIND_ALL,
//            query = "SELECT e FROM FilmActor AS e")
//@NamedQuery(name = FilmActorConstants.QUERY_FIND_BY_ID,
//            query = "SELECT e FROM FilmActor AS e WHERE e.id = :Id")
@IdClass(FilmActorId.class)
@Entity
@Table(name = FilmActor.TABLE_NAME)
public class FilmActor
        extends _BaseEntity<FilmActorId> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "film_actor";

    /**
     * The name of the table column to which the {@link FilmActor_#actorId actorId} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_ACTOR_ID = Actor.COLUMN_NAME_ACTOR_ID;

    /**
     * The name of the table column to which the {@link FilmActor_#filmId filmId} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_FILM_ID = Film.COLUMN_NAME_FILM_ID;

    /**
     * Creates a new instance with specified actor and film.
     *
     * @param actor the actor.
     * @param film  the film
     * @return a new instance with {@code actor} and {@code film}.
     * @see #setActor(Actor)
     * @see #setFilm(Film)
     */
    public static FilmActor of(final Actor actor, final Film film) {
        final var instance = new FilmActor();
        instance.setActor(actor);
        instance.setFilm(film);
        return instance;
    }

    /**
     * Creates a new instance.
     */
    public FilmActor() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "actorId=" + actorId +
               ",filmId=" + filmId +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FilmActor that)) return false;
        return equals_(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier());
    }

    @Override
    FilmActorId identifier() {
        return FilmActorId.of(actorId, filmId);
    }

    /**
     * Returns current value of {@link FilmActor_#actorId actorId} attribute.
     *
     * @return current value of the {@link FilmActor_#actorId actorId} attribute.
     */
    public Integer getActorId() {
        return actorId;
    }

    /**
     * Replaces current value of {@link FilmActor_#actorId actorId} attribute with specified value.
     *
     * @param actorId new value for the {@link FilmActor_#actorId actorId} attribute.
     */
    void setActorId(final Integer actorId) {
        this.actorId = actorId;
    }

    /**
     * Returns current value of {@link FilmActor_#filmId filmId} attribute.
     *
     * @return current value of the {@link FilmActor_#filmId filmId} attribute.
     */
    public Integer getFilmId() {
        return filmId;
    }

    /**
     * Replaces current value of {@link FilmActor_#filmId filmId} attribute with specified value.
     *
     * @param filmId new value for the {@link FilmActor_#filmId filmId} attribute.
     */
    void setFilmId(final Integer filmId) {
        this.filmId = filmId;
    }

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-film_actor.html">
     * A foreign key identifying the actor.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_ACTOR_ID, nullable = false)
    private Integer actorId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-film_actor.html">
     * A foreign key identifying the film.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_FILM_ID, nullable = false)
    private Integer filmId;

    /**
     * Returns current value of {@link FilmActor_#actor actor} attribute.
     *
     * @return current value of {@link FilmActor_#actor actor} attribute.
     */
    public Actor getActor() {
        return actor;
    }

    /**
     * Replaces current value of {@link FilmActor_#actor actor} attribute with specified value.
     *
     * @param actor new value for the {@link FilmActor_#actor actor} attribute.
     * @apiNote This method also replaces current value of {@link FilmActor_#actorId actorId} attribute with
     * {@code actor?.actorId}.
     */
    public void setActor(final Actor actor) {
        this.actor = actor;
        setActorId(
                Optional.ofNullable(this.actor)
                        .map(Actor::getActorId)
                        .orElse(null)
        );
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_ACTOR_ID, nullable = false, insertable = false, updatable = false)
    private Actor actor;

    /**
     * Returns current value of {@link FilmActor_#film film} attribute.
     *
     * @return current value of {@link FilmActor_#film film} attribute.
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Replaces current value of {@link FilmActor_#film film} attribute with specified value.
     *
     * @param film new value for the {@link FilmActor_#film film} attribute.
     * @apiNote This method also replaces current value of {@link FilmActor_#filmId filmId} attribute with
     * {@code film?.filmId}.
     */
    public void setFilm(final Film film) {
        this.film = film;
        setFilmId(
                Optional.ofNullable(this.film)
                        .map(Film::getFilmId)
                        .orElse(null)
        );
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_FILM_ID, nullable = false, insertable = false, updatable = false)
    private Film film;
}
