package com.github.jinahya.persistence;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class LocaleUtilsTest {

    private static Stream<Locale> getLocaleStream() {
        return Stream.of(Locale.getAvailableLocales());
    }

    @MethodSource({"getLocaleStream"})
    @ParameterizedTest
    void valueOfDisplayCountry(final Locale inLocale) {
        final var displayCountry = inLocale.getDisplayCountry(inLocale);
        final var got = _LocaleUtils.valueOfDisplayCountry(inLocale, displayCountry);
        assertThat(got).hasValueSatisfying(v -> {
            assertThat(v.getDisplayCountry(inLocale)).isEqualTo(displayCountry);
        });
    }

    @MethodSource({"getLocaleStream"})
    @ParameterizedTest
    void valueOfDisplayLanguage(final Locale inLocale) {
        final var displayLanguage = inLocale.getDisplayLanguage(inLocale);
        final var got = _LocaleUtils.valueOfDisplayLanguage(inLocale, displayLanguage);
        assertThat(got).hasValueSatisfying(v -> {
            assertThat(v.getDisplayLanguage(inLocale)).isEqualTo(displayLanguage);
        });
    }
}
