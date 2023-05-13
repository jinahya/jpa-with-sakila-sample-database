package com.github.jinahya.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

abstract class _BaseEntityTest<T extends _BaseEntity<U>, U>
        extends __BaseEntityTest<T, U> {

    protected _BaseEntityTest(final Class<T> entityClass, final Class<U> idClass) {
        super(entityClass, idClass);
    }

    @DisplayName("toString")
    @Test
    void toString_NotBlank_() {
        final var string = newEntityInstance().toString();
        assertThat(string).isNotBlank();
    }
}
