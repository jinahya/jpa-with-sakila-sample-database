package com.github.jinahya.persistence;

import org.junit.jupiter.api.Test;

class StoreIT
        extends _BaseEntityIT<Store, Integer> {

    StoreIT() {
        super(Store.class, Integer.class);
    }

    @Test
    void persist__() {
        final var store = newEntityInstance();
        final var staff = new Staff();
        staff.setFirstName("firstNam");
        staff.setLastName("firstNam");
        staff.setAddress(new Address());
    }
}
