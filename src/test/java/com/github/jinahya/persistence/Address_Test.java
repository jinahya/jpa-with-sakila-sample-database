package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalMatchers;

import java.nio.ByteOrder;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
class Address_Test
        extends _BaseEntityTest<Address, Integer> {

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

    @DisplayName("locationGeometryAsPoint")
    @Nested
    class LocationGeometryAsPointTest {

        @Test
        void getLocationGeometryAsPoint_Null_() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            final var locationGeometry = instance.getLocationGeometryAsPoint((x, y) -> new Object());
            assertThat(locationGeometry).isNull();
            verify(instance, times(1)).getLocationGeometry();
        }

        @DisplayName("getLocationGeometryAsPoint((+1.0d, -1.0d) -> null)")
        @Test
        void getLocationGeometryAsPoint_Expected_Point11Le() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            when(instance.getLocation()).thenReturn(point11Le());
            // THEN
            instance.getLocationGeometryAsPoint((x, y) -> {
                assertThat(x).isEqualTo(+1.0d);
                assertThat(x).isEqualTo(-1.0d);
                return null;
            });
        }

        @DisplayName("getLocationGeometryAsPoint((+2.0d, +4.0d) -> null)")
        @Test
        void getLocationGeometryAsPoint_Expected_Point24Le() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            when(instance.getLocation()).thenReturn(point24Le());
            // THEN
            instance.getLocationGeometryAsPoint((x, y) -> {
                assertThat(x).isEqualTo(+2.0d);
                assertThat(x).isEqualTo(+4.0d);
                return null;
            });
        }

        @DisplayName("setLocationGeometryAsPoint(+1.0d, -1.0d)")
        @Test
        void setLocationGeometryAsPoint_Expected_11() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setLocationGeometryAsPoint(+1.0d, -1.0d);
            // THEN
            verify(instance, times(1)).setLocation(AdditionalMatchers.aryEq(point11Le()));
        }

        @DisplayName("setLocationGeometryAsPoint(+2.0d, +4.0d)")
        @Test
        void setLocationGeometryAsPoint_Expected_24() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setLocationGeometryAsPoint(+2.0d, +4.0d);
            // THEN
            verify(instance, times(1)).setLocation(AdditionalMatchers.aryEq(point24Le()));
        }

        @Test
        void setLocationGeometryAsPoint__() {
            // GIVEN
            final var instance = newEntitySpy();
            final var xCoordinate = ThreadLocalRandom.current().nextDouble();
            final var yCoordinate = ThreadLocalRandom.current().nextDouble();
            final var srid = 0;
            // WHEN
            instance.setLocationGeometryAsPoint(0, xCoordinate, yCoordinate);
            // THEN
            verify(instance, times(1)).setLocationGeometry(argThat(g -> {
                assertThat(g).isNotNull();
                assertThat(g.getSrid()).isEqualTo(srid);
                assertThat(g.getBinary()).isNotNull().satisfies(b -> {
                    assertThat(b.getOrder()).isSameAs(ByteOrder.LITTLE_ENDIAN);
                    assertThat(b.getType()).isSameAs(_PersistenceTypes.Wkb.Type.POINT);
                    final var buffer = b.getDataBuffer();
                    assertThat(buffer.order()).isSameAs(ByteOrder.LITTLE_ENDIAN);
                    assertThat(buffer.remaining()).isEqualTo(Double.BYTES << 1);
                    assertThat(buffer.getDouble()).isEqualTo(xCoordinate);
                    assertThat(buffer.getDouble()).isEqualTo(yCoordinate);
                });
                return true;
            }));
        }
    }
}
