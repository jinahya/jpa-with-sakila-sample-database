package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * An abstract mapped superclass for mapping {@value MappedActor#TABLE_NAME} table.
 * <p>
 * <blockquote>
 * The {@value MappedActor#TABLE_NAME} table lists information for all actors. <br/> The {@value MappedActor#TABLE_NAME}
 * table is joined to the {@value MappedFilm#TABLE_NAME} table by means of the {@value MappedFilmActor#TABLE_NAME}
 * table.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-actor.html">5.1.1 The actor Table</a>
 */
@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class MappedActor
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "actor";

    /**
     * The name of the table column to which the {@value MappedActor_#ACTOR_ID} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_ACTOR_ID = "actor_id";

    /**
     * The name of the table column to which the {@value MappedActor_#LAST_NAME} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_LAST_NAME = "last_name";

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
        if (!(obj instanceof MappedActor)) return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * A surrogate primary key used to uniquely identify each actor in the table.
     */
    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_ACTOR_ID, nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
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
