package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.Language;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Stream;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;

class LanguageService_IT
        extends _BaseEntityServiceIT<LanguageService, Language, Integer> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    LanguageService_IT() {
        super(LanguageService.class, Language.class, Integer.class);
    }

    @DisplayName("findAll")
    @Nested
    class FindAllTest {

        @DisplayName("findAll(null)")
        @Test
        void __MaxResultsNull() {
//            final var found = applyServiceInstance(s -> s.findAll(null));
//            assertThat(found)
//                    .isNotEmpty()
//                    .doesNotContainNull();
        }

        @DisplayName("findAll(!null)")
        @Test
        void __() {
            final var maxResults = current().nextInt(1, 8);
//            final var found = applyServiceInstance(s -> s.findAll(maxResults));
//            assertThat(found)
//                    .isNotEmpty()
//                    .doesNotContainNull()
//                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @DisplayName("locateByName")
    @Nested
    class LocateByNameTest {

        private static Stream<Locale> localeWithNonBlankDisplayLanguageInEnglishStream() {
            return Arrays.stream(Locale.getAvailableLocales())
                    .filter(v -> !v.getDisplayLanguage(Locale.ENGLISH).isBlank());
        }

        @Disabled
        @ExplicitParamInjection
        @MethodSource({"localeWithNonBlankDisplayLanguageInEnglishStream"})
        @ParameterizedTest
        void __(final Locale locale) {
            final var name = locale.getDisplayLanguage(Locale.ENGLISH);
            if (name.length() > Language.COLUMN_LENGTH_NAME) {
                return;
            }
            final var located = applyServiceInstance(s -> s.locateByName(name));
            assertThat(located)
                    .isNotNull()
                    .extracting(Language::getName)
                    .isEqualTo(name);
        }
    }

    @DisplayName("locateByLocale")
    @Nested
    class LocateByLocaleTest {

        private static Stream<Locale> localeWithNonBlankDisplayLanguageInEnglishStream() {
            return Arrays.stream(Locale.getAvailableLocales())
                    .filter(v -> !v.getDisplayLanguage(Locale.ENGLISH).isBlank());
        }

        @Disabled
        @ExplicitParamInjection
        @MethodSource({"localeWithNonBlankDisplayLanguageInEnglishStream"})
        @ParameterizedTest
        void __(final Locale locale) {
            if (locale.getDisplayLanguage(Locale.ENGLISH).length() > Language.COLUMN_LENGTH_NAME) {
                return;
            }
            final var located = applyServiceInstance(s -> s.locateByLocale(locale));
            assertThat(located)
                    .isNotNull()
                    .extracting(Language::getName)
                    .isEqualTo(locale.getDisplayLanguage(Locale.ENGLISH));
        }
    }
}
