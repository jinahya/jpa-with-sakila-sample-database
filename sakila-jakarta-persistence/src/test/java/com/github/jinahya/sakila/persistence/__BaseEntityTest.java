package com.github.jinahya.sakila.persistence;

import jakarta.persistence.Transient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.IntrospectionException;

import static java.beans.Introspector.getBeanInfo;
import static org.assertj.core.api.Assertions.assertThat;

abstract class __BaseEntityTest<T extends __BaseEntity<U>, U extends Comparable<? super U>>
        extends ___BaseEntityTestBase<T, U> {

    __BaseEntityTest(final Class<T> entityClass, final Class<U> idClass) {
        super(entityClass, idClass);
    }

    @DisplayName("toString()!blank")
    @Test
    void toString_NotBlank_() {
        final var string = newEntityInstance().toString();
        assertThat(string).isNotBlank();
    }

    @DisplayName("get...()")
    @Test
    void get__() throws IntrospectionException, ReflectiveOperationException {
        final var instance = newEntityInstance();
        for (final var descriptor : getBeanInfo(entityClass).getPropertyDescriptors()) {
            final var reader = descriptor.getReadMethod();
            if (reader == null) {
                continue;
            }
            if (reader.isAnnotationPresent(Transient.class)) {
                continue;
            }
            if (!reader.canAccess(instance)) {
                reader.setAccessible(true);
            }
            final var value = reader.invoke(instance);
        }
    }

    @DisplayName("set...(null)")
    @Test
    void set__() throws IntrospectionException, ReflectiveOperationException {
        final var instance = newEntityInstance();
        for (final var descriptor : getBeanInfo(entityClass).getPropertyDescriptors()) {
            final var writer = descriptor.getWriteMethod();
            if (writer == null) {
                continue;
            }
            if (writer.isAnnotationPresent(Transient.class)) {
                continue;
            }
            if (!writer.canAccess(instance)) {
                writer.setAccessible(true);
            }
            if (descriptor.getPropertyType().isPrimitive()) {
                continue;
            }
            writer.invoke(instance, new Object[]{null});
        }
    }
}
