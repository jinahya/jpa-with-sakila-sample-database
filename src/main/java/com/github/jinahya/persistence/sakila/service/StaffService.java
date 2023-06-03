package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.Staff;

public class StaffService
        extends _BaseEntityService<Staff, Integer> {

    StaffService() {
        super(Staff.class, Integer.class);
    }
}
