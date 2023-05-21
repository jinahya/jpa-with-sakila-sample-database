package com.github.jinahya.persistence;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.WeakHashMap;

/**
 * Utilities related to {@link Locale} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
final class _LocaleUtils {

    private static final Map<Locale, Map<String, Locale>> DISPLAY_COUNTRIES_AND_LOCALES = new WeakHashMap<>();

    /**
     * Returns the locale whose {@link Locale#getDisplayCountry(Locale) display country} in specified locale matches to
     * specified value.
     *
     * @param displayCountry the value to match.
     * @param inLocale       the locale to get the display language of candidates.
     * @return an optional of matched locale; {@link Optional#empty()} is not found.
     */
    static Optional<Locale> valueOfDisplayCountry(final String displayCountry, final Locale inLocale) {
        Objects.requireNonNull(displayCountry, "displayCountry is null");
        Objects.requireNonNull(inLocale, "inLocale is null");
        return Optional.ofNullable(
                DISPLAY_COUNTRIES_AND_LOCALES
                        .computeIfAbsent((Locale) inLocale.clone(), k -> new HashMap<>())
                        .computeIfAbsent(
                                displayCountry,
                                k -> Arrays.stream(Locale.getAvailableLocales())
                                        .filter(l -> Objects.equals(l.getDisplayCountry(inLocale), k))
                                        .findFirst()
                                        .orElse(null)
                        )
        );
    }

    static Optional<Locale> valueOfDisplayCountryInEnglish(final String displayCountryInEnglish) {
        return valueOfDisplayCountry(displayCountryInEnglish, Locale.ENGLISH);
    }

    private static final Map<Locale, Map<String, Locale>> DISPLAY_LANGUAGES_AND_LOCALES = new WeakHashMap<>();

    static Optional<Locale> valueOfDisplayLanguage(final String displayLanguage, final Locale inLocale) {
        if (Objects.requireNonNull(displayLanguage, "displayLanguage is null").strip().isBlank()) {
            throw new IllegalStateException("displayLanguage is blank");
        }
        Objects.requireNonNull(inLocale, "inLocale is null");
        return Optional.ofNullable(
                DISPLAY_LANGUAGES_AND_LOCALES
                        .computeIfAbsent((Locale) inLocale.clone(), k -> new HashMap<>())
                        .computeIfAbsent(
                                displayLanguage,
                                k -> Arrays.stream(Locale.getAvailableLocales())
                                        .filter(l -> {
                                            final var displayLanguageInLocale = l.getDisplayLanguage(inLocale);
                                            if (displayLanguageInLocale.isBlank()) {
                                                return false;
                                            }
                                            return Objects.equals(displayLanguageInLocale, k);
                                        })
                                        .findFirst()
                                        .orElse(null)
                        )
        );
    }

    static Optional<Locale> valueOfDisplayLanguageInEnglish(final String displayLanguageInEnglish) {
        return valueOfDisplayLanguage(displayLanguageInEnglish, Locale.ENGLISH);
    }

    private _LocaleUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
