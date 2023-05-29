package com.github.jinahya.persistence.sakila;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class CustomerList_IT
        extends __BaseEntityIT<CustomerList, Integer> {

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
