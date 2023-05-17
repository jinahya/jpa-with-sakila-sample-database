package com.github.jinahya.persistence;

import org.jeasy.random.api.Randomizer;

class _PersistenceTypeRandomizers {

    static class WkbComponentRandomizer
            implements Randomizer<_PersistenceTypes.Wkb> {

        @Override
        public _PersistenceTypes.Wkb getRandomValue() {
//            final var byteOrder = current().nextBoolean() ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
//            final var array = new byte[_PersistenceTypes.WkbComponent.BYTES];
//            final var buffer = ByteBuffer.wrap(array).order(byteOrder);
//            final var wkbType = _PersistenceTypes.WkbType.values()[current().nextInt(_PersistenceTypes.WkbType.values().length)];
//            buffer.putInt(wkbType.getType());

            return null;
        }
    }
}
