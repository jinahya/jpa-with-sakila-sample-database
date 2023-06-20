package com.github.jinahya.sakila.persistence;

import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public final class _DomainTypes {

    private static final Logger log = getLogger(lookup().lookupClass());

    interface AttributeEnum<E extends Enum<E> & AttributeEnum<E, A>, A> {

        @NotNull A attribute();
    }

    /**
     * Represents the Well-Known Binary Representation.
     *
     * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/gis-data-formats.html#gis-wkb-format">11.4.3 Supported
     * Spatial Data Formats</a>
     */
    public static final class Wkb {

        public static final int TYPE_VALUE_POINT = 1;

        /**
         * Constants of supported spatial data types.
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
                    if (value.type == type) {
                        return value;
                    }
                }
                throw new IllegalArgumentException("no value for type: " + type);
            }

            Type(final int type) {
                this.type = type;
            }

            public int type() {
                return type;
            }

            private final int type;
        }

        public static final int ENDIAN_BIG = 0;

        public static final int ENDIAN_LITTLE = 1;

        /**
         * Returns a endian value which is corresponding to specified byte order.
         *
         * @param order the byte order to convert.
         * @return {@value #ENDIAN_BIG} if {@code order} is {@link ByteOrder#BIG_ENDIAN}; {@value #ENDIAN_LITTLE}
         * otherwise.
         */
        public static byte orderToEndian(final ByteOrder order) {
            Objects.requireNonNull(order, "order is null");
            return (byte) (order == ByteOrder.BIG_ENDIAN ? ENDIAN_BIG : ENDIAN_LITTLE);
        }

        /**
         * Returns a byte order which is corresponding to specified endian value.
         *
         * @param endian the endian value.
         * @return {@link ByteOrder#BIG_ENDIAN} if {@code endian} is {@value #ENDIAN_BIG};
         * {@link ByteOrder#LITTLE_ENDIAN} otherwise.
         */
        public static ByteOrder endianToOrder(final byte endian) {
            if (endian == ENDIAN_BIG) {
                return ByteOrder.BIG_ENDIAN;
            }
            return ByteOrder.LITTLE_ENDIAN;
        }

        public static Wkb newPoint(final ByteOrder order, final double x, final double y) {
            Objects.requireNonNull(order, "order is null");
            final var buffer = ByteBuffer.allocate(Double.BYTES << 1).order(order);
            buffer.putDouble(x);
            buffer.putDouble(y);
            return new Wkb(order, Type.POINT, buffer.array());
        }

        public static Wkb newLineString(final ByteOrder order, final Wkb... points) {
            Objects.requireNonNull(order, "order is null");
            Objects.requireNonNull(points, "points is null");
            // TODO: check; should points.length be positive?
            final var buffer = ByteBuffer.allocate((Double.BYTES << 1) * points.length).order(order);
            for (var point : points) {
                buffer.putDouble(point.getDataBuffer().getDouble());
                buffer.putDouble(point.getDataBuffer().getDouble());
            }
            return new Wkb(order, Type.LINE_STRING, buffer.array());
        }

        public static Wkb from(final ByteBuffer buffer) {
            Objects.requireNonNull(buffer, "buffer is null");
            buffer.order(endianToOrder(buffer.get()));
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

        public ByteBuffer put(final ByteBuffer buffer) {
            Objects.requireNonNull(buffer, "buffer is null");
            return buffer
                    .order(order)
                    .put(orderToEndian(order))
                    .putInt(type.type)
                    .put(data)
                    ;
        }

        public ByteBuffer toByteBuffer() {
            return put(ByteBuffer.allocate(capacity())).flip();
        }

        @Deprecated(forRemoval = true) // not used
        public byte[] toByteArray() {
            return toByteBuffer().array();
        }

        @Deprecated(forRemoval = true) // not used
        public byte[] getDataArray() {
            return Arrays.copyOf(data, data.length);
        }

        public ByteBuffer getDataBuffer() {
            return ByteBuffer.wrap(data)
                    .asReadOnlyBuffer() // -> BIG_ENDIAN
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

        public ByteBuffer put(final ByteBuffer buffer) {
            Objects.requireNonNull(buffer, "buffer is null");
            return binary.put(buffer.putInt(srid));
        }

        public ByteBuffer toByteBuffer() {
            return put(
                    ByteBuffer.allocate(Integer.BYTES + binary.capacity())
            ).flip();
        }

        byte[] toByteArray() {
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

    private _DomainTypes() {
        throw new AssertionError("instantiation is not allowed");
    }
}
