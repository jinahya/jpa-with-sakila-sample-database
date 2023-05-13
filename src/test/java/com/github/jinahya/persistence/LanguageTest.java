package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.stream.Stream;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
class LanguageTest
        extends _BaseEntityTest<Language, Integer> {

    LanguageTest() {
        super(Language.class, Integer.class);
    }

    @DisplayName("setNameAsLocale(locale)")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class SetNameAsLocaleTest {

        private Stream<Locale> getLocaleStream() {
            return Stream.of(Locale.getAvailableLocales());
        }

        @Disabled("not implemented yet") // TODO: comment out this line when you implement the method.
        @DisplayName("setNameAsLocale(locale) -> setName(locale.displayLanguage)")
        @MethodSource("getLocaleStream")
        @ParameterizedTest
        void _InvokeNameWithLocaleDisplayLanguage_(final Locale locale) {
            // GIVEN
            final var spy = spy(entityClass);
            // WHEN
            spy.setNameAsLocale(locale);
            // THEN
            verify(spy, times(1)).setName(locale.getDisplayLanguage(Locale.ENGLISH));
        }
    }
}
