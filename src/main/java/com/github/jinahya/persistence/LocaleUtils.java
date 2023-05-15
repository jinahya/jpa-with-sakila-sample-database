package com.github.jinahya.persistence;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.WeakHashMap;

final class LocaleUtils {

    private static final Map<Locale, Map<String, Locale>> DISPLAY_COUNTRIES_AND_LOCALES = new WeakHashMap<>();

    static Optional<Locale> valueOfDisplayCountry(final Locale inLocale, final String displayCountry) {
        Objects.requireNonNull(displayCountry, "displayCountry is null");
        Objects.requireNonNull(inLocale, "inLocale is null");
        return Optional.ofNullable(
                DISPLAY_COUNTRIES_AND_LOCALES
                        .computeIfAbsent((Locale) inLocale.clone(), k -> new HashMap<>())
                        .computeIfAbsent(
                                displayCountry,
                                k -> Arrays.stream(Locale.getAvailableLocales())
                                        .filter(l -> Objects.equals(l.getDisplayCountry(inLocale), displayCountry))
                                        .findFirst()
                                        .orElse(null)
                        )
        );
    }

    private static final Map<Locale, Map<String, Locale>> DISPLAY_LANGUAGES_AND_LOCALES = new WeakHashMap<>();

    static Optional<Locale> valueOfDisplayLanguage(final Locale inLocale, final String displayLanguage) {
        Objects.requireNonNull(inLocale, "inLocale is null");
        Objects.requireNonNull(displayLanguage, "displayLanguage is null");
        return Optional.ofNullable(
                DISPLAY_LANGUAGES_AND_LOCALES
                        .computeIfAbsent((Locale) inLocale.clone(), k -> new HashMap<>())
                        .computeIfAbsent(
                                displayLanguage,
                                k -> Arrays.stream(Locale.getAvailableLocales())
                                        .filter(l -> Objects.equals(l.getDisplayLanguage(inLocale), displayLanguage))
                                        .findFirst()
                                        .orElse(null)
                        )
        );
    }

    private LocaleUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
