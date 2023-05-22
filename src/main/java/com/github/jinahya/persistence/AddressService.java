package com.github.jinahya.persistence;

public class AddressService
        extends _BaseEntityService<Address, Integer> {

    protected AddressService() {
        super(Address.class, Integer.class);
    }
}
