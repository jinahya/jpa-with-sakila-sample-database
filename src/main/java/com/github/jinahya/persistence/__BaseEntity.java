package com.github.jinahya.persistence;

import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public abstract class __BaseEntity<U> {

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof __BaseEntity<?> that)) return false;
        final U identifier = identifier();
        return identifier != null && Objects.equals(identifier, that.identifier());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @SuppressWarnings({"unchecked"})
    protected U identifier() {
        return (U) _PersistenceUtils.getIdentifier(this);
    }
}
