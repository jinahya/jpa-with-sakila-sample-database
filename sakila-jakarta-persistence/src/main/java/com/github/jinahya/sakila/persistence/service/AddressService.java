package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence.Address;

class AddressService
        extends _BaseEntityService<Address, Integer> {

    AddressService() {
        super(Address.class, Integer.class);
    }
}
