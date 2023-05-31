package com.github.jinahya.persistence.sakila;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * A class for identifying {@link FilmActor} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public class FilmActorId
        implements Serializable,
                   Comparable<FilmActorId> {

    @Serial
    private static final long serialVersionUID = -3335863482033861830L;

    private static final Comparator<FilmActorId> NATURAL_ORDER =
            Comparator.comparing(FilmActorId::getActorId)
                    .thenComparing(FilmActorId::getFilmId);

    /**
     * Creates a new instance with specified values of {@code actorId} and {@code filmId}.
     *
     * @param actorId a value for the {@code actorId} property.
     * @param filmId  a value for the {@code filmId} property.
     * @return a new instance of {@code actorId} and {@code filmId}.
     */
    public static FilmActorId of(final Integer actorId, final Integer filmId) {
        final var instance = new FilmActorId();
        instance.setActorId(actorId);
        instance.setFilmId(filmId);
        return instance;
    }

    /**
     * Creates a new instance.
     */
    public FilmActorId() {
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
        if (!(obj instanceof FilmActorId that)) return false;
        return Objects.equals(actorId, that.actorId) &&
               Objects.equals(filmId, that.filmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId, filmId);
    }

    @Override
    public int compareTo(final FilmActorId o) {
        return NATURAL_ORDER.compare(this, o);
    }

    /**
     * Returns current value of {@code actorId} property with specified value.
     *
     * @return current value of the {@code actorId} property.
     */
    public Integer getActorId() {
        return actorId;
    }

    /**
     * Replaces current value of {@code actorId} property with specified value.
     *
     * @param actorId new value for the {@code actorId} property.
     */
    void setActorId(final Integer actorId) {
        this.actorId = actorId;
    }

    /**
     * Returns current value of {@code filmId} property with specified value.
     *
     * @return current value of the {@code filmId} property.
     */
    public Integer getFilmId() {
        return filmId;
    }

    /**
     * Replaces current value of {@code filmId} property with specified value.
     *
     * @param filmId new value for the {@code filmId} property.
     */
    void setFilmId(final Integer filmId) {
        this.filmId = filmId;
    }

    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    private Integer actorId;

    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    private Integer filmId;
}
