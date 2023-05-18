package com.github.jinahya.persistence;

import com.github.jinahya.persistence._PersistenceTypes.Geometry;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.nio.ByteBuffer;

public final class _PersistenceConverters {

    /**
     * An attribute converter for converting from column values of {@code GEOMETRY} to {@link Geometry}, and vice
     * versa.
     *
     * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
     */
    @Converter
    public static class GeometryConverter
            implements AttributeConverter<Geometry, byte[]> {

        @Override
        public byte[] convertToDatabaseColumn(final Geometry attribute) {
            if (attribute == null) {
                return null;
            }
            return attribute.toByteArray();
        }

        @Override
        public Geometry convertToEntityAttribute(final byte[] dbData) {
            if (dbData == null) {
                return null;
            }
            return Geometry.from(ByteBuffer.wrap(dbData));
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

    private _PersistenceConverters() {
        throw new AssertionError("instantiation is not allowed");
    }
}
