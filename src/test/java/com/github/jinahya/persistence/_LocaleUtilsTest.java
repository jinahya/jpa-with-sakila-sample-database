package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class _LocaleUtilsTest {

    static Stream<Locale> localeStream() {
        return Stream.of(Locale.getAvailableLocales());
    }

    static Stream<Locale> getLocaleWithNonBlankDisplayCountryStream() {
        return localeStream()
                .filter(l -> !l.getDisplayCountry(Locale.ENGLISH).isBlank());
    }

    static Stream<Locale> localeWithNonBlankDisplayLanguageStream() {
        return localeStream()
                .filter(l -> !l.getDisplayLanguage(Locale.ENGLISH).isBlank());
    }

    @Disabled
    @MethodSource({"localeStream"})
    @ParameterizedTest
    void valueOfDisplayCountry(final Locale inLocale) {
        final var displayCountry = inLocale.getDisplayCountry(inLocale);
        if (displayCountry.strip().isBlank()) {
            return;
        }
        final var value = _LocaleUtils.valueOfDisplayCountry(displayCountry, inLocale);
        assertThat(value).hasValueSatisfying(v -> {
            assertThat(v.getDisplayCountry(inLocale))
                    .isEqualTo(displayCountry);
        });
    }

    @Disabled
    @MethodSource({"localeStream"})
    @ParameterizedTest
    void valueOfDisplayCountryInEnglish(final Locale locale) {
        final var displayCountryInEnglish = locale.getDisplayCountry(Locale.ENGLISH);
        if (displayCountryInEnglish.isBlank()) {
            return;
        }
        final var value = _LocaleUtils.valueOfDisplayCountryInEnglish(displayCountryInEnglish);
        assertThat(value).hasValueSatisfying(v -> {
            assertThat(v.getDisplayCountry(Locale.ENGLISH))
                    .isEqualTo(displayCountryInEnglish);
        });
    }

    @Disabled
    @MethodSource({"localeStream"})
    @ParameterizedTest
    void valueOfDisplayLanguage(final Locale inLocale) {
        final var displayLanguage = inLocale.getDisplayLanguage(inLocale);
        if (displayLanguage.strip().isBlank()) {
            return;
        }
        final var value = _LocaleUtils.valueOfDisplayLanguage(displayLanguage, inLocale);
        assertThat(value).hasValueSatisfying(v -> {
            assertThat(v.getDisplayLanguage(inLocale))
                    .isEqualTo(displayLanguage);
        });
    }

    @Disabled
    @MethodSource({"localeStream"})
    @ParameterizedTest
    void valueOfDisplayLanguageInEnglish(final Locale locale) {
        final var displayLanguageInEnglish = locale.getDisplayLanguage(Locale.ENGLISH);
        if (displayLanguageInEnglish.isBlank()) {
            return;
        }
        final var value = _LocaleUtils.valueOfDisplayLanguageInEnglish(displayLanguageInEnglish);
        assertThat(value).hasValueSatisfying(v -> {
            assertThat(v.getDisplayLanguage(Locale.ENGLISH))
                    .isEqualTo(displayLanguageInEnglish);
        });
    }
}
