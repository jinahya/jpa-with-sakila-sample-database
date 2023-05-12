package com.github.jinahya.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Entity
@Table(name = FilmActor.TABLE_NAME)
@IdClass(FilmActorId.class)
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class FilmActor
        extends _BaseEntity<FilmActorId> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "film_actor";

    public static final String COLUMN_NAME_ACTOR_ID = Actor.COLUMN_NAME_ACTOR_ID;

    public static final String COLUMN_NAME_FILM_ID = Film.COLUMN_NAME_FILM_ID;

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
        if (!(obj instanceof FilmActor)) return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier());
    }

    @Override
    protected FilmActorId identifier() {
        return FilmActorId.builder()
                .actorId(getActorId())
                .filmId(getFilmId())
                .build();
    }

    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_ACTOR_ID, nullable = false)
    private Integer actorId;

    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_FILM_ID, nullable = false)
    private Integer filmId;
}
