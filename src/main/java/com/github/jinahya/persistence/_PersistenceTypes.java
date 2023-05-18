package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
public final class _PersistenceTypes {

    /**
     * Represents the Well-Known Binary Representation.
     *
     * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/gis-data-formats.html#gis-wkb-format">11.4.3 Supported
     * Spatial Data Formats</a>
     */
    public static final class Wkb {

        public static final int TYPE_VALUE_POINT = 1;

        /**
         * .
         *
         * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/gis-data-formats.html#gis-wkb-format">11.4.3 Supported
         * Spatial Data Formats</a>
         */
        public enum Type {

            POINT(TYPE_VALUE_POINT),

            LINE_STRING(2),

            POLYGON(3),

            MULTI_POINT(4),

            MULTI_LINE_STRING(5),

            MULTI_POLYGON(6),

            GEOMETRY_COLLECTION(7);

            public static Type valueOfType(final int type) {
                for (final var value : values()) {
                    if (value.value == type) {
                        return value;
                    }
                }
                throw new IllegalArgumentException("no value for type: " + type);
            }

            Type(final int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }

            private final int value;
        }

        public static final int ENDIAN_BIG = 0;

        public static final int ENDIAN_LITTLE = 1;

        /**
         * Returns a numeric value for specified byte order.
         *
         * @param byteOrder the byte order to convert.
         * @return {@code 0} if {@code order} is {@link ByteOrder#BIG_ENDIAN}; {@code 1} otherwise.
         */
        public static byte endianValue(final ByteOrder byteOrder) {
            Objects.requireNonNull(byteOrder, "order is null");
            return (byte) (byteOrder == ByteOrder.BIG_ENDIAN ? ENDIAN_BIG : ENDIAN_LITTLE);
        }

        public static ByteOrder byteOrder(final byte endianValue) {
            if (endianValue == ENDIAN_BIG) {
                return ByteOrder.BIG_ENDIAN;
            }
            assert endianValue == ENDIAN_LITTLE;
            return ByteOrder.LITTLE_ENDIAN;
        }

        public static Wkb from(final ByteBuffer buffer) {
            Objects.requireNonNull(buffer, "buffer is null");
            buffer.order(byteOrder(buffer.get()));
            final var type = Type.valueOfType(buffer.getInt());
            final var data = new byte[buffer.remaining()];
            buffer.get(data);
            return new Wkb(buffer.order(), type, data);
        }

        private Wkb(final ByteOrder order, final Type type, final byte[] data) {
            super();
            this.order = Objects.requireNonNull(order, "order is null");
            this.type = Objects.requireNonNull(type, "type is null");
            this.data = Objects.requireNonNull(data, "data is null");
        }

        @Override
        public String toString() {
            return super.toString() + '{' +
                   "order=" + order +
                   ",type=" + type +
//                   ",data=" + Arrays.toString(data) +
                   '}';
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Wkb that)) return false;
            return Objects.equals(order, that.order) &&
                   type == that.type &&
                   Arrays.equals(data, that.data);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(order, type);
            result = 31 * result + Arrays.hashCode(data);
            return result;
        }

        public ByteBuffer toByteBuffer(final ByteBuffer buffer) {
            Objects.requireNonNull(buffer, "buffer is null");
            return buffer
                    .order(order)
                    .put(endianValue(order))
                    .putInt(type.value)
                    .put(data)
                    ;
        }

        public ByteBuffer toByteBuffer() {
            return toByteBuffer(ByteBuffer.allocate(capacity())).flip();
        }

        public byte[] toByteArray() {
            return toByteBuffer().array();
        }

        public byte[] getDataArray() {
            return Arrays.copyOf(data, data.length);
        }

        public ByteBuffer getDataBuffer() {
            return ByteBuffer.wrap(data)
                    .asReadOnlyBuffer()
                    .order(order);
        }

        private int capacity() {
            return Byte.BYTES + Integer.BYTES + data.length;
        }

        public ByteOrder getOrder() {
            return order;
        }

        public Type getType() {
            return type;
        }

        private final ByteOrder order;

        private final Type type;

        private final byte[] data;
    }

    public static final class Geometry {

        public static Geometry from(final ByteBuffer buffer) {
            Objects.requireNonNull(buffer, "buffer is null");
            final var srid = buffer.getInt();
            final var wkb = Wkb.from(buffer);
            return of(srid, wkb);
        }

        public static Geometry of(final int srid, final Wkb binary) {
            return new Geometry(srid, binary);
        }

        private Geometry(final int srid, final Wkb binary) {
            super();
            this.srid = srid;
            this.binary = Objects.requireNonNull(binary, "binary is null");
        }

        @Override
        public String toString() {
            return super.toString() + '{' +
                   "srid=" + srid +
                   ",binary=" + binary +
                   '}';
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Geometry geometry)) return false;
            return srid == geometry.srid &&
                   Objects.equals(binary, geometry.binary);
        }

        @Override
        public int hashCode() {
            return Objects.hash(srid, binary);
        }

        public ByteBuffer toByteBuffer(final ByteBuffer buffer) {
            Objects.requireNonNull(buffer, "buffer is null");
            return binary.toByteBuffer(buffer.putInt(srid));
        }

        public ByteBuffer toByteBuffer() {
            return toByteBuffer(
                    ByteBuffer.allocate(Integer.BYTES + binary.capacity())
            ).flip();
        }

        public byte[] toByteArray() {
            return toByteBuffer().array();
        }

        public int getSrid() {
            return srid;
        }

        public Wkb getBinary() {
            return binary;
        }

        private final int srid; // spatial reference identifier

        private final Wkb binary;
    }

    private _PersistenceTypes() {
        throw new AssertionError("instantiation is not allowed");
    }
}
