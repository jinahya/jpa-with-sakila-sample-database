package com.github.jinahya.persistence.sakila;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.nio.ByteOrder;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.aryEq;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;

class Address_Test
        extends _BaseEntityTest<Address, Integer> {

    private static final Logger log = getLogger(lookup().lookupClass());

    // +1.0, -1.0
    private static byte[] point11Le() {
        return new byte[]{
                0x00, 0x00, 0x00, 0x00,                                       // srid
                0x01,                                                         // order
                0x01, 0x00, 0x00, 0x00,                                       // type
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xF0, 0x3F,        // xCoordinate
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xF0, (byte) 0xBF  // yCoordinate
        };
    }

    // +2.0, +4.0
    private static byte[] point24Be() {
        return new byte[]{
                0x00, 0x00, 0x00, 0x00,                         // srid
                0x00,                                           // order
                0x00, 0x00, 0x00, 0x01,                         // type
                0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // xCoordinate
                0x40, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 // yCoordinate
        };
    }

    // +2.0, +4.0
    private static byte[] point24Le() {
        return new byte[]{
                0x00, 0x00, 0x00, 0x00,                         // srid
                0x01,                                           // order
                0x01, 0x00, 0x00, 0x00,                         // type
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x40, // xCoordinate
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x10, 0x40 // yCoordinate
        };
    }

    Address_Test() {
        super(Address.class, Integer.class);
    }

    @DisplayName("applyLocationGeometryAsPoint((x, y) -> )")
    @Nested
    class ApplyLocationGeometryAsPointTest {

        @DisplayName("getLocationGeometry()null -> null")
        @Test
        void _Null_NewInstance() {
            // GIVEN
            final var instance = newEntitySpy();
            when(instance.getLocationGeometry()).thenReturn(null);
            clearInvocations(instance); // EclipseLink
            // WHEN
            final var locationGeometryAsPoint = instance.applyLocationGeometryAsPoint((x, y) -> new Object());
            // THEN
            assertThat(locationGeometryAsPoint).isNull();
            verify(instance, times(1)).getLocationGeometry();
        }

        @DisplayName("getLocationGeometry()point11Le -> (+1.0d, -1.0d)")
        @Test
        void _Expected_Point11Le() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            when(instance.getLocation()).thenReturn(point11Le());
            // THEN
            instance.applyLocationGeometryAsPoint((x, y) -> {
                assertThat(x).isEqualTo(+1.0d);
                assertThat(x).isEqualTo(-1.0d);
                return null;
            });
        }

        @DisplayName("getLocationGeometry()point24Le -> (+2.0d, +4.0d))")
        @Test
        void _Expected_Point24Le() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            when(instance.getLocation()).thenReturn(point24Le());
            // THEN
            instance.applyLocationGeometryAsPoint((x, y) -> {
                assertThat(x).isEqualTo(+2.0d);
                assertThat(x).isEqualTo(+4.0d);
                return null;
            });
        }
    }

    @DisplayName("setLocationGeometryAsPoint(x, y)")
    @Nested
    class SetLocationGeometryAsPointTest {

        @DisplayName("(+1.0d, -1.0d) -> setLocation(point11Le)")
        @Test
        void _Expected_11() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setLocationGeometryAsPoint(+1.0d, -1.0d);
            // THEN
            verify(instance, times(1)).setLocation(aryEq(point11Le()));
        }

        @DisplayName("(+2.0d, +4.0d) -> setLocation(point24Le)")
        @Test
        void setLocationGeometryAsPoint_Expected_24() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setLocationGeometryAsPoint(+2.0d, +4.0d);
            // THEN
            verify(instance, times(1)).setLocation(aryEq(point24Le()));
        }

        @Test
        void __() {
            // GIVEN
            final var instance = newEntitySpy();
            final var x = current().nextDouble();
            final var y = current().nextDouble();
            // WHEN
            instance.setLocationGeometryAsPoint(x, y);
            // THEN
            verify(instance, times(1)).setLocationGeometry(argThat(g -> {
                assertThat(g).isNotNull();
                assertThat(g.getSrid()).isZero();
                assertThat(g.getBinary()).isNotNull().satisfies(b -> {
                    assertThat(b.getOrder()).isSameAs(ByteOrder.LITTLE_ENDIAN);
                    assertThat(b.getType()).isSameAs(_DomainTypes.Wkb.Type.POINT);
                    final var buffer = b.getDataBuffer();
                    assertThat(buffer.order()).isSameAs(ByteOrder.LITTLE_ENDIAN);
                    assertThat(buffer.remaining()).isEqualTo(Double.BYTES << 1);
                    assertThat(buffer.getDouble()).isEqualTo(x);
                    assertThat(buffer.getDouble()).isEqualTo(y);
                });
                return true;
            }));
        }
    }
}
