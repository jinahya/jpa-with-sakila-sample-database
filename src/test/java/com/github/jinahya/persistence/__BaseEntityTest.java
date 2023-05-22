package com.github.jinahya.persistence;

import org.junit.jupiter.api.Test;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;

abstract class __BaseEntityTest<T extends __BaseEntity<U>, U>
        extends ___BaseEntityTestBase<T, U> {

    protected __BaseEntityTest(final Class<T> entityClass, final Class<U> idClass) {
        super(entityClass, idClass);
    }

    @Test
    void getter__() throws IntrospectionException, ReflectiveOperationException {
        final var instance = newEntityInstance();
        final var beanInfo = Introspector.getBeanInfo(entityClass);
        for (final var propertyDescriptor : beanInfo.getPropertyDescriptors()) {
            final var readMethod = propertyDescriptor.getReadMethod();
            if (readMethod == null) {
                continue;
            }
            if (readMethod.canAccess(instance)) {
                readMethod.setAccessible(true);
            }
            final var returnType = readMethod.getReturnType();
            if (returnType == Void.TYPE) {
                return;
            }
            try {
                final var value = readMethod.invoke(instance);
            } catch (final InvocationTargetException ite) {
                if (ite.getTargetException() instanceof UnsupportedOperationException) {
                    continue;
                }
                throw ite;
            }
        }
    }

    @Test
    void setter__() throws IntrospectionException, ReflectiveOperationException {
        final var instance = newEntityInstance();
        final var beanInfo = Introspector.getBeanInfo(entityClass);
        for (final var propertyDescriptor : beanInfo.getPropertyDescriptors()) {
            final var writeMethod = propertyDescriptor.getWriteMethod();
            if (writeMethod == null) {
                continue;
            }
            if (writeMethod.canAccess(instance)) {
                writeMethod.setAccessible(true);
            }
            final var parameterType = writeMethod.getParameterTypes()[0];
            if (!parameterType.isPrimitive()) {
                try {
                    writeMethod.invoke(instance, new Object[]{null});
                } catch (final InvocationTargetException ite) {
                    if (ite.getTargetException() instanceof UnsupportedOperationException) {
                        continue;
                    }
                    throw ite;
                }
            }
        }
    }
}
