package com.github.jinahya.persistence.sakila.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LocaleUtilsTest {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static Stream<Locale> getLocaleStream() {
        return Stream.of(Locale.getAvailableLocales());
    }

    public static Stream<Locale> getLocaleWithNonBlankDisplayCountryStream() {
        return getLocaleStream()
                .filter(l -> !l.getDisplayCountry(Locale.ENGLISH).isBlank());
    }

    public static Stream<Locale> getLocaleWithNonBlankDisplayLanguageStream() {
        return getLocaleStream()
                .filter(l -> !l.getDisplayLanguage(Locale.ENGLISH).isBlank());
    }

    @MethodSource({"getLocaleWithNonBlankDisplayCountryStream"})
    @ParameterizedTest
    void valueOfDisplayCountry(final Locale inLocale) {
        final var displayCountry = inLocale.getDisplayCountry(inLocale);
        final var value = LocaleUtils.valueOfDisplayCountry(displayCountry, inLocale);
        assertThat(value).hasValueSatisfying(v -> {
            assertThat(v.getDisplayCountry(inLocale))
                    .isEqualTo(displayCountry);
        });
    }

    @MethodSource({"getLocaleWithNonBlankDisplayCountryStream"})
    @ParameterizedTest
    void valueOfDisplayCountryInEnglish(final Locale locale) {
        final var displayCountryInEnglish = locale.getDisplayCountry(Locale.ENGLISH);
        final var value = LocaleUtils.valueOfDisplayCountryInEnglish(displayCountryInEnglish);
        assertThat(value).hasValueSatisfying(v -> {
            assertThat(v.getDisplayCountry(Locale.ENGLISH))
                    .isEqualTo(displayCountryInEnglish);
        });
    }

    @MethodSource({"getLocaleWithNonBlankDisplayLanguageStream"})
    @ParameterizedTest
    void valueOfDisplayLanguage(final Locale inLocale) {
        final var displayLanguage = inLocale.getDisplayLanguage(inLocale);
        final var value = LocaleUtils.valueOfDisplayLanguage(displayLanguage, inLocale);
        assertThat(value).hasValueSatisfying(v -> {
            assertThat(v.getDisplayLanguage(inLocale))
                    .isEqualTo(displayLanguage);
        });
    }

    @MethodSource({"getLocaleWithNonBlankDisplayLanguageStream"})
    @ParameterizedTest
    void valueOfDisplayLanguageInEnglish(final Locale locale) {
        final var displayLanguageInEnglish = locale.getDisplayLanguage(Locale.ENGLISH);
        final var value = LocaleUtils.valueOfDisplayLanguageInEnglish(displayLanguageInEnglish);
        assertThat(value).hasValueSatisfying(v -> {
            assertThat(v.getDisplayLanguage(Locale.ENGLISH))
                    .isEqualTo(displayLanguageInEnglish);
        });
    }
}
