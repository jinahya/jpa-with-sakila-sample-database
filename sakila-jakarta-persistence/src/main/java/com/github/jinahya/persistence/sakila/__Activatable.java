package com.github.jinahya.persistence.sakila;

import jakarta.persistence.Transient;

import static com.github.jinahya.persistence.sakila.__ActivatableHelper.getActiveField;
import static com.github.jinahya.persistence.sakila.__ActivatableHelper.getActiveVarHandle;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;

public interface __Activatable<ENTITY extends __BaseEntity<?> & __Activatable<ENTITY>> {

    /**
     * The name of a string attribute represents an <em>active</em> state. The value is {@value}.
     */
    String ATTRIBUTE_NAME_ACTIVE = "active";

    /**
     * Returns current value of {@value #ATTRIBUTE_NAME_ACTIVE} attribute.
     *
     * @return current value of the {@value #ATTRIBUTE_NAME_ACTIVE} attribute.
     */
    @Transient
    default Integer getActive() {
        if (current().nextBoolean()) {
            final var varHandle = getActiveVarHandle(getClass());
            return (Integer) varHandle.get(this);
        }
        final var field = getActiveField(getClass());
        if (!field.canAccess(this)) {
            field.setAccessible(true);
        }
        try {
            return (Integer) field.get(this);
        } catch (final IllegalAccessException iae) {
            throw new RuntimeException("failed to get value from " + field, iae);
        }
    }

    /**
     * Replaces current value of {@value #ATTRIBUTE_NAME_ACTIVE} attribute with specified value.
     *
     * @param active new value for the {@value #ATTRIBUTE_NAME_ACTIVE} attribute.
     */
    default void setActive(final Integer active) {
        if (current().nextBoolean()) {
            final var varHandle = getActiveVarHandle(getClass());
            varHandle.set(this, active);
            return;
        }
        final var field = getActiveField(getClass());
        if (!field.canAccess(this)) {
            field.setAccessible(true);
        }
        try {
            field.set(this, active);
        } catch (final IllegalAccessException iae) {
            throw new RuntimeException("failed to set value onto " + field, iae);
        }
    }

    /**
     * Returns current value of {@value #ATTRIBUTE_NAME_ACTIVE} property as a boolean value.
     *
     * @return a boolean value represents current value of the {@value #ATTRIBUTE_NAME_ACTIVE} property;
     * {@link Boolean#FALSE} for {@code 0}; {@link Boolean#TRUE} for a non-zero value; {@code null} if the current value
     * of the {@value #ATTRIBUTE_NAME_ACTIVE} property is {@code null}.
     * @see #getActive()
     * @see com.github.jinahya.persistence.sakila._DomainConverters.BooleanConverter#intToBoolean(int)
     */
    @Transient
    default Boolean getActiveAsBoolean() {
        return ofNullable(getActive())
                .map(_DomainConverters.BooleanConverter::intToBoolean)
                .orElse(null);
    }

    /**
     * Replaces current value of {@value #ATTRIBUTE_NAME_ACTIVE} property with specified boolean value.
     *
     * @param activeAsBoolean the boolean value for the {@value #ATTRIBUTE_NAME_ACTIVE} property; {@link Boolean#FALSE}
     *                        for {@code 0}; {@link Boolean#TRUE} for {@code 1}; {@code null} for {@code null}.
     * @see com.github.jinahya.persistence.sakila._DomainConverters.BooleanConverter#booleanToInt(boolean)
     * @see #setActive(Integer)
     */
    default void setActiveAsBoolean(final Boolean activeAsBoolean) {
        setActive(
                ofNullable(activeAsBoolean)
                        .map(_DomainConverters.BooleanConverter::booleanToInt)
                        .orElse(null)
        );
    }

    /**
     * Activates this entity, and returns <em>this</em> object.
     *
     * @return this entity.
     * @see #setActiveAsBoolean(Boolean)
     */
    @SuppressWarnings({"unchecked"})
    default ENTITY activate() {
        setActiveAsBoolean(Boolean.TRUE);
        return (ENTITY) this;
    }

    /**
     * Deactivates this entity, and returns <em>this</em> object.
     *
     * @return this entity.
     * @see #setActiveAsBoolean(Boolean)
     */
    @SuppressWarnings({"unchecked"})
    default ENTITY deactivate() {
        setActiveAsBoolean(Boolean.FALSE);
        return (ENTITY) this;
    }
}
