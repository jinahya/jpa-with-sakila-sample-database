package com.github.jinahya.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;
import java.util.Optional;

/**
 * An entity class for mapping {@value #TABLE_NAME} table.
 * <p>
 * <blockquote>
 * The {@value #TABLE_NAME} table is used to support a many-to-many relationship between films and actors. For each
 * actor in a given film, there will be one row in the {@value #TABLE_NAME} table listing the actor and film.<br/> The
 * {@value #TABLE_NAME} table refers to the {@value Film#TABLE_NAME} and {@value Actor#TABLE_NAME} tables using foreign
 * keys.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
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
        return FilmActorId.of(getActorId(), getFilmId());
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
    public void setActorId(final Integer actorId) {
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
    public void setFilmId(final Integer filmId) {
        this.filmId = filmId;
    }

    /**
     * A foreign key identifying the actor.
     */
    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_ACTOR_ID, nullable = false)
    private Integer actorId;

    /**
     * A foreign key identifying the film.
     */
    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_FILM_ID, nullable = false)
    private Integer filmId;

    /**
     * Returns current value of {@link FilmActor_#actor} attribute.
     *
     * @return current value of {@link FilmActor_#actor} attribute.
     */
    public Actor getActor() {
        return actor;
    }

    /**
     * Replaces current value of {@link FilmActor_#actor} attribute with specified value.
     *
     * @param actor new value for the {@link FilmActor_#actor} attribute.
     */
    public void setActor(final Actor actor) {
        this.actor = actor;
        setActorId(
                Optional.ofNullable(this.actor)
                        .map(Actor::getActorId)
                        .orElse(null)
        );
    }

    @Valid // without @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_ACTOR_ID, nullable = false, insertable = false, updatable = false)
    private Actor actor;

    // TODO: Map for an Film!
}
