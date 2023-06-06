package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.Address;

class AddressService
        extends _BaseEntityService<Address, Integer> {

    AddressService() {
        super(Address.class, Integer.class);
    }
}
