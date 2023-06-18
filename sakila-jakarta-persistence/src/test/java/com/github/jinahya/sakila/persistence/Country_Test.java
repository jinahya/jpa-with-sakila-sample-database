package com.github.jinahya.sakila.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;

import java.util.Locale;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;

class Country_Test
        extends _BaseEntityTest<Country, Integer> {

    private static final Logger log = getLogger(lookup().lookupClass());

    Country_Test() {
        super(Country.class, Integer.class);
    }

    @DisplayName("getLocalesForCountry()List")
    @Nested
    class GetLocalesForCountryTest {

        @DisplayName("getCountry()null -> empty")
        @Test
        void _Empty_GetCountryNull() {
            final var instance = newEntitySpy();
            when(instance.getCountry()).thenReturn(null);
            assertThat(instance.getLocalesForCountry()).isEmpty();
        }

        @DisplayName("getCountry()!null -> !null")
        @MethodSource("java.util.Locale#getAvailableLocales")
        @ParameterizedTest
        void _NotNull_NotNull(final Locale locale) {
            final var instance = newEntitySpy();
            final var displayLanguageInEnglish = locale.getDisplayLanguage(Locale.ENGLISH);
            if (displayLanguageInEnglish.isBlank()) {
                return;
            }
            when(instance.getCountry()).thenReturn(displayLanguageInEnglish);
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

        @DisplayName("(null) -> setCountry(null)")
        @Test
        void _Null_Null() {
            final var instance = newEntitySpy();
            instance.setCountryAsLocale(null);
            verify(instance, times(1)).setCountry(null);
        }

        @DisplayName("(!null) -> setCountry(.displayLanguage(ENGLISH)")
        @MethodSource("java.util.Locale#getAvailableLocales")
        @ParameterizedTest
        void _NotNull_NotNull(final Locale locale) {
            final var instance = newEntitySpy();
            instance.setCountryAsLocale(locale);
            verify(instance, times(1)).setCountry(locale.getDisplayCountry(Locale.ENGLISH));
        }
    }
}
