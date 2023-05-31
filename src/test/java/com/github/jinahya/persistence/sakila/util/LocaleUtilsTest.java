package com.github.jinahya.persistence.sakila.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class LocaleUtilsTest {

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

    @MethodSource({"getLocaleStream"})
    @ParameterizedTest
    void valueOfDisplayCountry(final Locale inLocale) {
        final var displayCountry = inLocale.getDisplayCountry(inLocale);
        if (displayCountry.isBlank()) {
            return;
        }
        final var value = LocaleUtils.valueOfDisplayCountry(displayCountry, inLocale);
        assertThat(value).hasValueSatisfying(v -> {
            assertThat(v.getDisplayCountry(inLocale))
                    .isEqualTo(displayCountry);
        });
    }

    @MethodSource({"getLocaleStream"})
    @ParameterizedTest
    void valueOfDisplayCountryInEnglish(final Locale locale) {
        final var displayCountryInEnglish = locale.getDisplayCountry(Locale.ENGLISH);
        if (displayCountryInEnglish.isBlank()) {
            return;
        }
        final var value = LocaleUtils.valueOfDisplayCountryInEnglish(displayCountryInEnglish);
        assertThat(value).hasValueSatisfying(v -> {
            assertThat(v.getDisplayCountry(Locale.ENGLISH))
                    .isEqualTo(displayCountryInEnglish);
        });
    }

    @MethodSource({"getLocaleStream"})
    @ParameterizedTest
    void valueOfDisplayLanguage(final Locale inLocale) {
        final var displayLanguage = inLocale.getDisplayLanguage(inLocale);
        if (displayLanguage.isBlank()) {
            return;
        }
        final var value = LocaleUtils.valueOfDisplayLanguage(displayLanguage, inLocale);
        assertThat(value).hasValueSatisfying(v -> {
            assertThat(v.getDisplayLanguage(inLocale))
                    .isEqualTo(displayLanguage);
        });
    }

    @MethodSource({"getLocaleStream"})
    @ParameterizedTest
    void valueOfDisplayLanguageInEnglish(final Locale locale) {
        final var displayLanguageInEnglish = locale.getDisplayLanguage(Locale.ENGLISH);
        if (displayLanguageInEnglish.isBlank()) {
            return;
        }
        final var value = LocaleUtils.valueOfDisplayLanguageInEnglish(displayLanguageInEnglish);
        assertThat(value).hasValueSatisfying(v -> {
            assertThat(v.getDisplayLanguage(Locale.ENGLISH))
                    .isEqualTo(displayLanguageInEnglish);
        });
    }
}
