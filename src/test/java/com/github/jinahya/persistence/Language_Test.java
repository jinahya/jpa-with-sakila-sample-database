package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
class Language_Test
        extends _BaseEntityTest<Language, Integer> {

    Language_Test() {
        super(Language.class, Integer.class);
    }

    @DisplayName("NameAsLocale(locale)")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class NameAsLocaleTest {

        private Stream<Locale> localeStream() {
            return _LocaleUtilsTest.localeStream();
        }

        private Stream<Locale> localeWithNonBlankDisplayLanguageStream() {
            return _LocaleUtilsTest.localeWithNonBlankDisplayLanguageStream();
        }

        @DisplayName("getNameAsLocale()null")
        @Test
        void getNameAsLocale_Null_New() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            final var nameAsLocale = instance.getNameAsLocale();
            // THEN
            verify(instance, times(1)).getName();
            assertThat(nameAsLocale).isNull();
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
            verify(instance, times(1)).getName();
            assertThat(nameAsLocale.getDisplayLanguage(Locale.ENGLISH)).isEqualTo(instance.getName());
        }

        @DisplayName("setNameAsLocale(locale) -> setName(locale.displayLanguage(ENGLISH))")
        @MethodSource("localeStream")
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
