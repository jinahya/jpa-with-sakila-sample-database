package com.github.jinahya.sakila.persistence;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.function.Function;

public final class _DomainConverters {

    public static class EnumConverter<E extends Enum<E>, Y>
            implements AttributeConverter<E, Y> {

        public static class OfString<E extends Enum<E>>
                extends EnumConverter<E, String> {

            public static class UsingName<E extends Enum<E>>
                    extends OfString<E> {

                public UsingName(final Class<E> enumClass) {
                    super(enumClass, Enum::name, c -> Enum.valueOf(enumClass, c));
                }
            }

            public OfString(final Class<E> enumClass, final Function<? super E, String> toColumnMapper,
                            final Function<? super String, E> toAttributeMapper) {
                super(enumClass, toColumnMapper, toAttributeMapper);
            }
        }

        public EnumConverter(final Class<E> enumClass, final Function<? super E, ? extends Y> toColumnMapper,
                             final Function<? super Y, E> toAttributeMapper) {
            super();
            this.enumClass = Objects.requireNonNull(enumClass, "enumClass is null");
            this.toColumnMapper = Objects.requireNonNull(toColumnMapper, "toColumnMapper is null");
            this.toAttributeMapper = Objects.requireNonNull(toAttributeMapper, "toAttributeMapper is null");
        }

        @Override
        public Y convertToDatabaseColumn(final E attribute) {
            return toColumnMapper.apply(attribute);
        }

        @Override
        public E convertToEntityAttribute(final Y dbData) {
            return toAttributeMapper.apply(dbData);
        }

        protected final Class<E> enumClass;

        protected final Function<? super E, ? extends Y> toColumnMapper;

        protected final Function<? super Y, E> toAttributeMapper;
    }

    /**
     * An attribute converter for converting from column values of {@code GEOMETRY} to {@link _DomainTypes.Geometry},
     * and vice versa.
     *
     * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
     */
    @Converter
    public static class GeometryConverter
            implements AttributeConverter<_DomainTypes.Geometry, byte[]> {

        @Override
        public byte[] convertToDatabaseColumn(final _DomainTypes.Geometry attribute) {
            if (attribute == null) {
                return null;
            }
            return attribute.toByteArray();
        }

        @Override
        public _DomainTypes.Geometry convertToEntityAttribute(final byte[] dbData) {
            if (dbData == null) {
                return null;
            }
            return _DomainTypes.Geometry.from(ByteBuffer.wrap(dbData));
        }
    }

    /**
     * An attribute converter for converting from integral column values to {@link Boolean}, and vice versa.
     *
     * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
     */
    @Converter
    public static class BooleanConverter
            implements AttributeConverter<Boolean, Integer> {

        public static int booleanToInt(final boolean value) {
            return value ? 1 : 0;
        }

        public static boolean intToBoolean(final int value) {
            return value != 0;
        }

        @Override
        public Integer convertToDatabaseColumn(final Boolean attribute) {
            if (attribute == null) {
                return null;
            }
            return booleanToInt(attribute);
        }

        @Override
        public Boolean convertToEntityAttribute(final Integer dbData) {
            if (dbData == null) {
                return null;
            }
            return intToBoolean(dbData);
        }
    }

    private _DomainConverters() {
        throw new AssertionError("instantiation is not allowed");
    }
}
