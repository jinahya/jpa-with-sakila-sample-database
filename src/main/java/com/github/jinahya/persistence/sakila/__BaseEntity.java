package com.github.jinahya.persistence.sakila;

import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

/**
 * An abstract mapped superclass with specified id type.
 *
 * @param <ID> the type of <em>id</em> of this entity class.
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@MappedSuperclass
public abstract class __BaseEntity<ID extends Comparable<? super ID>> {

    /**
     * Creates a new instance.
     */
    __BaseEntity() {
        super();
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of this object.
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if {@link #identifier()} method returns a <em>non-null</em> value, and
     * {@link #equals_(__BaseEntity) equals_(obj)} method returns {@code true}; {@code false} otherwise.
     * @see #equals_(__BaseEntity)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof __BaseEntity<?> that)) return false;
        return identifier() != null && equals_(that);
    }

    /**
     * Compares this object's <em>non-null</em> {@link #identifier() identifier} with that of specified object.
     *
     * @param obj the object to compare.
     * @return {@code true} if this object's {@link #identifier() identifier} is equal to that of {@code obj}.
     * @throws NullPointerException if the {@link #identifier()} method returns {@code null}.
     * @see #identifier()
     */
    boolean equals_(final __BaseEntity<?> obj) {
        return Objects.equals(
                Objects.requireNonNull(identifier(), "identifier() returned null"),
                obj.identifier()
        );
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     * @implNote The {@code hashCode()} method of {@code __BaseEntity} class returns the value of
     * {@code getClass().hashCode()}.
     * @implSpec Subclasses with any unique steady-state column(s) should override this method.
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /**
     * Returns a value identifying this entity.
     *
     * @return a value identifying this entity.
     */
    abstract ID identifier();
}
