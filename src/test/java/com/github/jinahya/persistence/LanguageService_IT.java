package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class LanguageService_IT
        extends _BaseEntityServiceIT<LanguageService, Language, Integer> {

    LanguageService_IT() {
        super(LanguageService.class);
    }

    @DisplayName("findAll")
    @Nested
    class FindAllTest {

        @DisplayName("findAll(null)")
        @Test
        void __MaxResultsNull() {
            final var found = applyServiceInstance(s -> s.findAll(null));
            assertThat(found)
                    .isNotEmpty()
                    .doesNotContainNull();
        }

        @DisplayName("findAll(!null)")
        @Test
        void __() {
            final var maxResults = current().nextInt(1, 8);
            final var found = applyServiceInstance(s -> s.findAll(maxResults));
            assertThat(found)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @DisplayName("findAllByLanguageIdGreaterThan")
    @Nested
    class FindAllByLanguageIdGreaterThanTest {

        @DisplayName("findAllByLanguageIdGreaterThan(0, null)")
        @Test
        void __MaxResultsNull() {
            final var languageIdMinExclusive = 0;
            final Integer maxResults = null;
            final var list = applyServiceInstance(
                    s -> s.findAllByLanguageIdGreaterThan(languageIdMinExclusive, maxResults)
            );
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull();
        }

        @DisplayName("findAllByLanguageIdGreaterThan(0, !null)")
        @Test
        void __MaxResultsNotNull() {
            final var languageIdMinExclusive = 0;
            final var maxResults = ThreadLocalRandom.current().nextInt(1, 8);
            final var list = applyServiceInstance(
                    s -> s.findAllByLanguageIdGreaterThan(languageIdMinExclusive, maxResults)
            );
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults);
        }

        @DisplayName("findAllByLanguageIdGreaterThan(++, !null)")
        @Test
        void __Pagination() {
            final var maxResults = ThreadLocalRandom.current().nextInt(1, 8);
            log.debug("masResults: {}", maxResults);
            for (final var i = new AtomicInteger(0); ; ) {
                final var list = applyServiceInstance(
                        s -> s.findAllByLanguageIdGreaterThan(i.get(), maxResults)
                );
                log.debug("languageIds: {}", list.stream().map(Language::getLanguageId).toList());
                assertThat(list)
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults);
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getLanguageId());
            }
        }
    }

    @DisplayName("locateByName")
    @Nested
    class LocateByNameTest {

        private static Stream<Locale> localeWithNonBlankDisplayLanguageStream() {
            return ____Utils_Locale_Test.localeWithNonBlankDisplayLanguageStream();
        }

        @Disabled
        @ExplicitParamInjection
        @MethodSource({"localeWithNonBlankDisplayLanguageStream"})
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

        private static Stream<Locale> localeWithNonBlankDisplayLanguageStream() {
            return ____Utils_Locale_Test.localeWithNonBlankDisplayLanguageStream();
        }

        @Disabled
        @ExplicitParamInjection
        @MethodSource({"localeWithNonBlankDisplayLanguageStream"})
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
