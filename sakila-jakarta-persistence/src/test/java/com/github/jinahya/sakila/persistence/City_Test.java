package com.github.jinahya.sakila.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class City_Test
        extends _BaseEntityTest<City, Integer> {

    City_Test() {
        super(City.class, Integer.class);
    }

    @DisplayName("getCountry()Country")
    @Nested
    class GetCountryAttributeTest {

        @DisplayName("new Country().getCountry()null")
        @Test
        void _Null_New() {
            // GIVEN
            final var instance = newEntityInstance();
            // WHEN / THEN
            // TODO: Verify instance.getCountry() returned null
        }
    }

    @DisplayName("setCountry(Country)")
    @Nested
    class SetCountryAttributeTest {

        @DisplayName("setCountry(null) -> setCountryId(null)")
        @Test
        void _InvokeSetCountryIdWithNull_Null() {
            final var instance = newEntitySpy();
            // WHEN
            instance.setCountry(null);
            // THEN
            verify(instance, times(1)).setCountryId(null);
        }

        @DisplayName("setCountry(!nullWithNullCountryId) -> setCountryId(null)")
        @Test
        void _InvokeSetCountryIdWithNull_NotNullWithNullCountryId() {
            // GIVEN
            final var instance = newEntitySpy();
            final var country = spy(Country.class);
            when(country.getCountryId()).thenReturn(null);
            // WHEN
            // TODO: Call instance.setCountry(null);
            // THEN
            // TODO: Verify instance.setCountryId(null) invoked once
        }

        @DisplayName("setCountry(!null) -> setCountryId(!null)")
        @Test
        void _InvokeSetCountryIdWithNull_NotNull() {
            // GIVEN
            final var instance = newEntitySpy();
            final var country = spy(Country.class);
            final var countryId = 0;
            when(country.getCountryId()).thenReturn(countryId);
            // WHEN
            // TODO: Call instance.setCountry(country);
            // THEN
            // TODO: Verify instance.setCountryId(countryId) invoked once
        }
    }
}
