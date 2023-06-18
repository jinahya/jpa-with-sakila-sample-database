package com.github.jinahya.sakila.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class Rental_Test
        extends _BaseEntityTest<Rental, Integer> {

    Rental_Test() {
        super(Rental.class, Integer.class);
    }

    @DisplayName("setInventory(Inventory)")
    @Nested
    class SetInventoryTest {

        @DisplayName("(null) -> setInventoryId(null)")
        @Test
        void _SetInventoryIdNull_Null() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setInventory(null);
            // THEN
            verify(instance, times(1)).setInventoryId(null);
        }

        @DisplayName("(!null) -> setInventoryId(.inventoryId)")
        @Test
        void _SetInventoryIdNotNull_NotNull() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setInventory(Inventory.ofInventoryId(0));
            // THEN
            verify(instance, times(1)).setInventoryId(0);
        }
    }

    @DisplayName("setCustomer(Customer)")
    @Nested
    class SetCustomerTest {

        @DisplayName("(null) -> setCustomerId(null)")
        @Test
        void _SetCustomerIdNull_Null() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setCustomer(null);
            // THEN
            verify(instance, times(1)).setCustomerId(null);
        }

        @DisplayName("(!null) -> setCustomerId(.customerId)")
        @Test
        void _SetCustomerIdNotNull_NotNull() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setCustomer(Customer.ofCustomerId(0));
            // THEN
            verify(instance, times(1)).setCustomerId(0);
        }
    }

    @DisplayName("setStaff(Staff)")
    @Nested
    class SetStaffTest {

        @DisplayName("(null) -> setStaffId(null)")
        @Test
        void _SetStaffIdNull_Null() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setStaff(null);
            // THEN
            verify(instance, times(1)).setStaffId(null);
        }

        @DisplayName("(!null) -> setStaffId(.staffId)")
        @Test
        void _SetStaffIdNotNull_NotNull() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setStaff(Staff.ofStaffId(0));
            // THEN
            verify(instance, times(1)).setStaffId(0);
        }
    }
}
