package com.github.jinahya.persistence;

import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

/**
 * An abstract mapped superclass with specified id type.
 *
 * @param <U> the type of id of this entity class.
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@MappedSuperclass
abstract class __BaseEntity<U> {

    /**
     * Creates a new instance.
     */
    protected __BaseEntity() {
        super();
    }

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
     * @return {@code true} if this object's {@link #identifier() identifier} is equals to that of {@code obj}.
     * @see #identifier()
     */
    protected boolean equals_(final __BaseEntity<?> obj) {
        return Objects.equals(
                Objects.requireNonNull(identifier(), "identifier() is null"),
                obj.identifier()
        );
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /**
     * Returns a value identifying this entity.
     *
     * @return a value identifying this entity.
     */
    protected abstract U identifier();
}
