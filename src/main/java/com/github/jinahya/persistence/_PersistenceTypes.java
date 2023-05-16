package com.github.jinahya.persistence;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

public final class _PersistenceTypes {

    /**
     * .
     *
     * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/gis-data-formats.html#gis-wkb-format">11.4.3 Supported
     * Spatial Data Formats</a>
     */
    public enum WkbType {
        POINT(1),
        LINE_STRING(2),
        POLYGON(3),
        MULTI_POINT(4),
        MULTI_LINE_STRING(5),
        MULTI_POLYGON(6),
        GEOMETRY_COLLECTION(7);

        public static WkbType valueOfType(final int type) {
            for (final var value : values()) {
                if (value.type == type) {
                    return value;
                }
            }
            throw new IllegalArgumentException("no value for type: " + type);
        }

        WkbType(final int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        private final int type;
    }

    /**
     * .
     *
     * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/gis-data-formats.html#gis-wkb-format">11.4.3 Supported
     * Spatial Data Formats</a>
     */
    public static final class WkbComponent {

        public static final int BYTES = 21;

        public static WkbComponent from(final byte[] array, final int offset) {
            if (Objects.requireNonNull(array, "array is null").length != BYTES) {
                throw new IllegalArgumentException("invalid array.length: " + array.length + " (!= " + BYTES + ")");
            }
            final var byteOrder = array[offset] == 0 ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
            final var byteBuffer = ByteBuffer.wrap(array, offset + 1, BYTES - 1).order(byteOrder);
            final var wkbType = WkbType.valueOfType(byteBuffer.getInt());
            final var xCoordinate = byteBuffer.getDouble();
            final var yCoordinate = byteBuffer.getDouble();
            return new WkbComponent(byteOrder, wkbType, xCoordinate, yCoordinate);
        }

        public static WkbComponent from(final byte[] array) {
            return from(array, 0);
        }

        private WkbComponent(final ByteOrder byteOrder, final WkbType wkbType, final double xCoordinate,
                             final double yCoordinate) {
            super();
            this.byteOrder = Objects.requireNonNull(byteOrder, "byteOrder is null");
            this.wkbType = Objects.requireNonNull(wkbType, "wkbType is null");
            this.xCoordinate = xCoordinate;
            this.yCoordinate = yCoordinate;
        }

        public byte[] toByteArray(final byte[] array, final int offset) {
            final var byteBuffer = ByteBuffer.wrap(array, offset, BYTES).order(byteOrder);
            byteBuffer.putInt(byteOrder == ByteOrder.BIG_ENDIAN ? 0 : 1);
            byteBuffer.putInt(wkbType.type);
            byteBuffer.putDouble(xCoordinate);
            byteBuffer.putDouble(yCoordinate);
            assert !byteBuffer.hasRemaining();
            return array;
        }

        public byte[] toByteArray(final byte[] array) {
            return toByteArray(array, 0);
        }

        public byte[] toByteArray() {
            return toByteArray(new byte[BYTES]);
        }

        public ByteOrder getByteOrder() {
            return byteOrder;
        }

        public WkbType getWkbType() {
            return wkbType;
        }

        public double getxCoordinate() {
            return xCoordinate;
        }

        public double getyCoordinate() {
            return yCoordinate;
        }

        private final ByteOrder byteOrder;

        private final WkbType wkbType;

        private final double xCoordinate;

        private final double yCoordinate;
    }

    public static class Geometry {

        public static final int BYTES = WkbComponent.BYTES + 4;

        public static Geometry from(final byte[] array, int offset) {
            if (Objects.requireNonNull(array, "array is null").length != BYTES) {
                throw new IllegalArgumentException("invalid array length: " + array.length + " (!= " + BYTES + ")");
            }
            final var srid = ((array[offset++] & 0xFF) << 24) |
                             ((array[offset++] & 0xFF) << 16) |
                             ((array[offset++] & 0xFF) << 8) |
                             (array[offset++] & 0xFF);
            final var component = WkbComponent.from(array, offset);
            return of(srid, component);
        }

        public static Geometry from(final byte[] array) {
            return from(array, 0);
        }

        public static Geometry of(final int srid, final WkbComponent component) {
            return new Geometry(srid, component);
        }

        private Geometry(final int srid, final WkbComponent component) {
            super();
            this.srid = srid;
            this.component = Objects.requireNonNull(component, "component is null");
        }

        public byte[] toByteArray(final byte[] array, int offset) {
            Objects.requireNonNull(array, "array is null");
            array[offset++] = (byte) ((srid >> 24) & 0xFF);
            array[offset++] = (byte) ((srid >> 16) & 0xFF);
            array[offset++] = (byte) ((srid >> 8) & 0xFF);
            array[offset++] = (byte) (srid & 0xFF);
            component.toByteArray(array, offset);
            return array;
        }

        public byte[] toByteArray(final byte[] array) {
            return toByteArray(array, 0);
        }

        public byte[] toByteArray() {
            return toByteArray(new byte[BYTES]);
        }

        public int getSrid() {
            return srid;
        }

        public WkbComponent getComponent() {
            return component;
        }

        private final int srid;

        private final WkbComponent component;
    }

    private _PersistenceTypes() {
        throw new AssertionError("instantiation is not allowed");
    }
}
