package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
class CountryTest
        extends _BaseEntityTest<Country, Integer> {

    CountryTest() {
        super(Country.class, Integer.class);
    }

    @DisplayName("setCountryAsLocale")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class CountryAsLocaleTest {

        @DisplayName("getCountry()null -> getCountryAsLocale()null")
        @Test
        void getCountryAsLocale_Null_Null() {
            // GIVEN
            final var spy = newEntitySpy();
            // WHEN
            when(spy.getCountry()).thenReturn(null);
            // THEN
            try {
                final var locale = spy.getCountryAsLocale();
                assertThat(locale).isNull();
            } catch (final UnsupportedOperationException uoe) {
                log.warn("seems not implemented yet");
            }
        }

        @DisplayName("getCountry()unknown -> getCountryAsLocale()null")
        @Test
        void getCountryAsLocale_Null_Unknown() {
            // GIVEN
            final var spy = newEntitySpy();
            // WHEN
            when(spy.getCountry()).thenReturn("Wakanda");
            // THEN
            try {
                final var locale = spy.getCountryAsLocale();
                assertThat(locale).isNull();
            } catch (final UnsupportedOperationException uoe) {
                log.warn("seems not implemented yet");
            }
        }

        @DisplayName("getCountry()known -> getCountryAsLocale()!null")
        @Test
        void getCountryAsLocale_NonNull_Known() {
            // GIVEN
            final var spy = newEntitySpy();
            // WHEN
            when(spy.getCountry()).thenReturn(Locale.US.getDisplayCountry(Locale.ENGLISH)); // of country_id(103)
            // THEN
            try {
                final var locale = spy.getCountryAsLocale();
                assertThat(locale).isNotNull();
            } catch (final UnsupportedOperationException uoe) {
                log.warn("seems not implemented yet");
            }
        }

        @DisplayName("setCountryAsLocale(null) -> setCountry(null)")
        @Test
        void setCountryAsLocale_Null_Null() {
            // GIVEN
            final var spy = newEntitySpy();
            // WHEN
            spy.setCountryAsLocale(null);
            // THEN
            verify(spy, times(1)).setCountry(null);
        }

        private List<Locale> getLocales() {
            return Arrays.asList(Locale.getAvailableLocales());
        }

        @DisplayName("setCountryAsLocale(locale) -> setCountry(locale.getDisplayLanguage(ENGLISH))")
        @MethodSource({"getLocales"})
        @ParameterizedTest
        void setCountryAsLocale__(final Locale locale) {
            // GIVEN
            final var spy = newEntitySpy();
            // WHEN
            spy.setCountryAsLocale(locale);
            // THEN
            verify(spy, times(1)).setCountry(locale.getDisplayCountry(Locale.ENGLISH));
        }
    }
}
