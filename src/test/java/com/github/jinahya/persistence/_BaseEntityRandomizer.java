package com.github.jinahya.persistence;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.api.ExclusionPolicy;
import org.jeasy.random.api.RandomizerContext;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.jeasy.random.FieldPredicates.named;

abstract class _BaseEntityRandomizer<T extends _BaseEntity<?>>
        extends __BaseEntityRandomizer<T> {

    _BaseEntityRandomizer(final Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    protected EasyRandomParameters parameters() {
        final var parameters = super.parameters()
                .excludeField(named("lastUpdate"));
        parameters.setExclusionPolicy(new ExclusionPolicy() {
            @Override
            public boolean shouldBeExcluded(final Field field, final RandomizerContext context) {
                final int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers)) {
                    return true;
                }
                return false;
            }

            @Override
            public boolean shouldBeExcluded(final Class<?> type, final RandomizerContext context) {
                if (type.getName().startsWith("org.eclipse")) {
                    return true;
                }
                if (type.getName().startsWith("java.beans")) {
                    return true;
                }
                return false;
            }
        });
        return parameters;
    }

    @Override
    protected EasyRandom random() {
        return super.random();
    }

    @Override
    public T getRandomValue() {
        return super.getRandomValue();
    }
}
