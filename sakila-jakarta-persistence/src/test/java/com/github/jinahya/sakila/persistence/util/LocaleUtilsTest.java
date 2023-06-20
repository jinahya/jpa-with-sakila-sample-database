package com.github.jinahya.sakila.persistence.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.stream.Stream;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class LocaleUtilsTest {

    private static final Logger log = getLogger(lookup().lookupClass());

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
    void valuesOfDisplayCountry(final Locale locale) {
        final var displayCountry = locale.getDisplayCountry(locale);
        final var values = LocaleUtils.valuesOfDisplayCountry(displayCountry, locale);
        assertThat(values)
                .extracting(v -> v.getDisplayCountry(locale))
                .containsOnly(displayCountry);
    }

    @MethodSource({"getLocaleWithNonBlankDisplayCountryStream"})
    @ParameterizedTest
    void valuesOfDisplayCountryInEnglish(final Locale locale) {
        final var displayCountryInEnglish = locale.getDisplayCountry(Locale.ENGLISH);
        final var values = LocaleUtils.valuesOfDisplayCountryInEnglish(displayCountryInEnglish);
        assertThat(values)
                .extracting(v -> v.getDisplayCountry(Locale.ENGLISH))
                .containsOnly(displayCountryInEnglish);
    }

    @MethodSource({"getLocaleWithNonBlankDisplayLanguageStream"})
    @ParameterizedTest
    void valuesOfDisplayLanguage(final Locale locale) {
        final var displayLanguage = locale.getDisplayLanguage(locale);
        final var values = LocaleUtils.valuesOfDisplayLanguage(displayLanguage, locale);
        assertThat(values)
                .extracting(v -> v.getDisplayLanguage(locale))
                .containsOnly(displayLanguage);
    }

    @MethodSource({"getLocaleWithNonBlankDisplayLanguageStream"})
    @ParameterizedTest
    void valuesOfDisplayLanguageInEnglish(final Locale locale) {
        final var displayLanguageInEnglish = locale.getDisplayLanguage(Locale.ENGLISH);
        final var values = LocaleUtils.valuesOfDisplayLanguageInEnglish(displayLanguageInEnglish);
        assertThat(values)
                .extracting(v -> v.getDisplayLanguage(Locale.ENGLISH))
                .containsOnly(displayLanguageInEnglish);
    }
}
