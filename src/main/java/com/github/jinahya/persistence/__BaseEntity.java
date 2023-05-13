package com.github.jinahya.persistence;

import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

@MappedSuperclass
public abstract class __BaseEntity<U> {

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

    protected boolean equals_(final __BaseEntity<?> obj) {
        return Objects.equals(Objects.requireNonNull(identifier(), "identifier() is null"), obj.identifier());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    protected abstract U identifier();
}
