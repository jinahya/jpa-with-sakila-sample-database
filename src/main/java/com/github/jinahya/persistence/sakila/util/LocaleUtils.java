package com.github.jinahya.persistence.sakila.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utilities related to {@link Locale}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class LocaleUtils {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final Map<Locale, Map<String, Locale>> DISPLAY_COUNTRIES_AND_LOCALES
            = new ConcurrentHashMap<>(new WeakHashMap<>());

    /**
     * Returns the locale whose {@link Locale#getDisplayCountry(Locale) display country} in specified locale matches to
     * specified value.
     *
     * @param displayCountry the value to match.
     * @param inLocale       the locale to get the display language of candidates.
     * @return an optional of matched locale; {@link Optional#empty()} is not found.
     */
    static Optional<Locale> valueOfDisplayCountry(final String displayCountry, final Locale inLocale) {
        if (Objects.requireNonNull(displayCountry, "displayCountry is null").isBlank()) {
            throw new IllegalArgumentException("displayCountry is blank");
        }
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

    public static Optional<Locale> valueOfDisplayCountryInEnglish(final String displayCountryInEnglish) {
        return valueOfDisplayCountry(displayCountryInEnglish, Locale.ENGLISH);
    }

    private static final Map<Locale, Map<String, Locale>> DISPLAY_LANGUAGES_AND_LOCALES
            = new ConcurrentHashMap<>(new WeakHashMap<>());

    static Optional<Locale> valueOfDisplayLanguage(final String displayLanguage, final Locale inLocale) {
        if (Objects.requireNonNull(displayLanguage, "displayLanguage is null").isBlank()) {
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

    public static Optional<Locale> valueOfDisplayLanguageInEnglish(final String displayLanguageInEnglish) {
        return valueOfDisplayLanguage(displayLanguageInEnglish, Locale.ENGLISH);
    }

    private LocaleUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
