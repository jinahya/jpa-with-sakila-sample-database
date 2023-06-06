package com.github.jinahya.persistence.sakila;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerList_IT
        extends __BaseEntityIT<CustomerList, Integer> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    CustomerList_IT() {
        super(CustomerList.class, Integer.class);
    }

    @DisplayName("select")
    @Test
    void select__() {
        final var list = applyEntityManager(
                em -> em.createQuery(
                        "SELECT e FROM CustomerList AS e",
                        CustomerList.class
                ).getResultList()
        );
        assertThat(list).isNotEmpty().doesNotContainNull();
    }
}
