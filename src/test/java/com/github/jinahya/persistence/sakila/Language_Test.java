package com.github.jinahya.persistence.sakila;

import com.github.jinahya.persistence.sakila.util.LocaleUtilsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.stream.Stream;

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

    @DisplayName("NameAsLocale(locale)")
    @Nested
    class NameAsLocaleTest {

        private static Stream<Locale> getLocaleStream() {
            return LocaleUtilsTest.getLocaleStream();
        }

        private static Stream<Locale> localeWithNonBlankDisplayLanguageStream() {
            return LocaleUtilsTest.getLocaleWithNonBlankDisplayLanguageStream();
        }

        @DisplayName("getNameAsLocale()null")
        @Test
        void getNameAsLocale_Null_New() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            final var nameAsLocale = instance.getNameAsLocale();
            // THEN
            assertThat(nameAsLocale).isNull();
            verify(instance, times(1)).getName();
        }

        @DisplayName("getNameAsLocale() <- getName()")
        @MethodSource({"localeWithNonBlankDisplayLanguageStream"})
        @ParameterizedTest
        void getNameAsLocale__(final Locale locale) {
            // GIVEN
            final var instance = newEntitySpy();
            when(instance.getName()).thenReturn(locale.getDisplayLanguage(Locale.ENGLISH));
            // WHEN
            final var nameAsLocale = instance.getNameAsLocale();
            // THEN
            assertThat(nameAsLocale.getDisplayLanguage(Locale.ENGLISH))
                    .isEqualTo(instance.getName());
        }

        @DisplayName("setNameAsLocale(locale) -> setName(locale.displayLanguage(ENGLISH))")
        @MethodSource("getLocaleStream")
        @ParameterizedTest
        void setNameAsLocale_InvokeNameWithLocaleDisplayLanguage_(final Locale locale) {
            // GIVEN
            final var spy = spy(entityClass);
            // WHEN
            spy.setNameAsLocale(locale);
            // THEN
            verify(spy, times(1)).setName(locale.getDisplayLanguage(Locale.ENGLISH));
        }
    }
}
