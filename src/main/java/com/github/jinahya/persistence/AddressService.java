package com.github.jinahya.persistence;

class AddressService
        extends _BaseEntityService<Address, Integer> {

    AddressService() {
        super(Address.class, Integer.class);
    }
}
