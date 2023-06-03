package com.github.jinahya.persistence.sakila;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class Country_Test
        extends _BaseEntityTest<Country, Integer> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    Country_Test() {
        super(Country.class, Integer.class);
    }

    @DisplayName("getLocalesForCountry()List")
    @Nested
    class GetLocalesForCountryTest {

        @DisplayName("getCountry()null -> null")
        @Test
        void _Null_Null() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            when(instance.getCountry()).thenReturn(null);
            // THEN
            assertThat(instance.getLocalesForCountry()).isNull();
        }

        @DisplayName("getCountry()!null -> !null")
        @MethodSource("java.util.Locale#getAvailableLocales")
        @ParameterizedTest
        void _NotNull_NotNull(final Locale locale) {
            // GIVEN
            final var instance = newEntitySpy();
            final var displayLanguageInEnglish = locale.getDisplayLanguage(Locale.ENGLISH);
            if (displayLanguageInEnglish.isBlank()) {
                return;
            }
            // WHEN
            when(instance.getCountry()).thenReturn(displayLanguageInEnglish);
            // THEN
            assertThat(instance.getLocalesForCountry())
                    .satisfiesAnyOf(
                            l -> {
                                assertThat(l).isEmpty();
                            },
                            l -> {
                                assertThat(l).extracting(v -> v.getDisplayCountry(Locale.ENGLISH))
                                        .containsOnly(displayLanguageInEnglish);
                            }
                    );
        }
    }

    @DisplayName("setCountryAsLocale(Locale)")
    @Nested
    class SetCountryAsLocaleTest {

        @DisplayName("setCountryAsLocale(null) -> setCountry(null)")
        @Test
        void _Null_Null() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setCountryAsLocale(null);
            // THEN
            verify(instance, times(1)).setCountry(null);
        }

        @DisplayName("setCountryAsLocale(country) -> setCountry(locale.getDisplayLanguage(ENGLISH)")
        @MethodSource("java.util.Locale#getAvailableLocales")
        @ParameterizedTest
        void _NotNull_NotNull(final Locale locale) {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            instance.setCountryAsLocale(locale);
            // THEN
            verify(instance, times(1)).setCountry(locale.getDisplayCountry(Locale.ENGLISH));
        }
    }
}
