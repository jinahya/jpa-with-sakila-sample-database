package com.github.jinahya.persistence;

import com.github.jinahya.persistence._PersistenceTypes.Geometry;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.nio.ByteBuffer;

public final class _PersistenceConverters {

    /**
     * An attribute converter for converting from column values of {@code GEOMETRY} to {@link Geometry}, and vice
     * versa.
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

    private _PersistenceConverters() {
        throw new AssertionError("instantiation is not allowed");
    }
}
