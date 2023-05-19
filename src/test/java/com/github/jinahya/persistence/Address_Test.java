package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.ByteOrder;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
class Address_Test
        extends _BaseEntityTest<Address, Integer> {

    Address_Test() {
        super(Address.class, Integer.class);
    }

    @DisplayName("setLocation")
    @Nested
    class SetLocationTest {

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
