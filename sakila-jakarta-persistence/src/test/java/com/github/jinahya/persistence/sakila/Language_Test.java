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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class Language_Test
        extends _BaseEntityTest<Language, Integer> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    Language_Test() {
        super(Language.class, Integer.class);
    }

    @DisplayName("getLocalesForName()List<Locale>")
    @Nested
    class GetLocalesForNameTest {

        @DisplayName("getName()null -> empty")
        @Test
        void _Empty_Null() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            when(instance.getName()).thenReturn(null);
            // THEN
            assertThat(instance.getLocalesForName()).isEmpty();
        }

        @DisplayName("getName()blank -> empty")
        @Test
        void _Empty_Blank() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            when(instance.getName()).thenReturn("");
            // THEN
            assertThat(instance.getLocalesForName()).isEmpty();
        }

        @DisplayName("getName()!blank -> !empty")
        @MethodSource({"java.util.Locale#getAvailableLocales"})
        @ParameterizedTest
        void __(final Locale locale) {
            // GIVEN
            final var displayLanguageInEnglish = locale.getDisplayLanguage(Locale.ENGLISH);
            if (displayLanguageInEnglish.isBlank()) {
                return;
            }
            final var instance = newEntitySpy();
            // WHEN
            when(instance.getName()).thenReturn(displayLanguageInEnglish);
            // THEN
            final var localesForName = instance.getLocalesForName();
            assertThat(localesForName)
                    .isNotEmpty()
                    .extracting(v -> v.getDisplayLanguage(Locale.ENGLISH))
                    .containsOnly(displayLanguageInEnglish);
        }
    }

    @DisplayName("setNameAsLocale(Locale)")
    @Nested
    class SetNameAsLocaleTest {

        @DisplayName("setNameAsLocale(null) -> setName(null)")
        @Test
        void _Null_Null() {
            // GIVEN
            final var spy = spy(entityClass);
            // WHEN
            spy.setNameAsLocale(null);
            // THEN
            verify(spy, times(1)).setName(null);
        }

        @DisplayName("setNameAsLocale(locale) -> setName(locale.displayLanguage(ENGLISH))")
        @MethodSource({"java.util.Locale#getAvailableLocales"})
        @ParameterizedTest
        void _setNameWithLocaleDisplayLanguage_NotNull(final Locale locale) {
            // GIVEN
            final var spy = spy(entityClass);
            // WHEN
            spy.setNameAsLocale(locale);
            // THEN
            verify(spy, times(1)).setName(locale.getDisplayLanguage(Locale.ENGLISH));
        }
    }
}
