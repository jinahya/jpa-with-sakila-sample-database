package com.github.jinahya.sakila.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class City_Test
        extends _BaseEntityTest<City, Integer> {

    City_Test() {
        super(City.class, Integer.class);
    }

    @DisplayName("country")
    @Nested
    class CountryAttributeTest {

        @DisplayName("new Country().getCountry()null")
        @Test
        void getCountry_Null_New() {
            // GIVEN
            final var instance = newEntityInstance();
            // WHEN / THEN
            // TODO: Verify instance.getCountry() return null
        }

        @DisplayName("setCountry(null) -> setCountryId(null)")
        @Test
        void setCountry_InvokeSetCountryIdWithNull_Null() {
            final var instance = newEntitySpy();
            // WHEN
            // TODO: Call instance.setCountry(null);
            // THEN
            // TODO: Verify instance.setCountryId(null) invoked once
        }

        @DisplayName("setCountry(countryIdIsNull) -> setCountryId(null)")
        @Test
        void setCountry_InvokeSetCountryIdWithNull_CountryIdIsNull() {
            // GIVEN
            final var instance = newEntitySpy();
            final var country = spy(Country.class);
            when(country.getCountryId()).thenReturn(null);
            // WHEN
            // TODO: Call instance.setCountry(null);
            // THEN
            // TODO: Verify instance.setCountryId(null) invoked once
        }
    }
}
