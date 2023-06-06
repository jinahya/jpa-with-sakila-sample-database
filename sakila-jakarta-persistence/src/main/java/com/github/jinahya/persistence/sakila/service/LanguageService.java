package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.Language;
import com.github.jinahya.persistence.sakila.Language_;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.github.jinahya.persistence.sakila.LanguageConstants.PARAMETER_LANGUAGE_ID;
import static com.github.jinahya.persistence.sakila.LanguageConstants.QUERY_FIND_ALL;
import static com.github.jinahya.persistence.sakila.LanguageConstants.QUERY_FIND_BY_LANGUAGE_ID;
import static com.github.jinahya.persistence.sakila.LanguageConstants.QUERY_PARAM_LANGUAGE_ID_MIN_EXCLUSIVE;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A service class for {@link Language} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public class LanguageService
        extends _BaseEntityService<Language, Integer> {

    /**
     * Creates a new instance.
     */
    LanguageService() {
        super(Language.class, Integer.class);
    }

    /**
     * Finds the entity identified by specified value of {@link Language_#languageId languageId} attribute.
     *
     * @param languageId the value of {@link Language_#languageId languageId} attribute to identify.
     * @return an optional of found entity.
     */
    public Optional<@Valid Language> findByLanguageId(final @Positive int languageId) {
        if (current().nextBoolean()) {
            return findById(languageId);
        }
        return ofNullable(
                applyEntityManager(em -> {
                    try {
                        return em.createNamedQuery(QUERY_FIND_BY_LANGUAGE_ID, Language.class)
                                .setParameter(PARAMETER_LANGUAGE_ID, languageId).getSingleResult();
                    } catch (final NoResultException nre) {
                        return null;
                    }
                })
        );
    }

    public @NotNull List<@Valid @NotNull Language> findAll(final @PositiveOrZero int languageIdMinExclusive,
                                                           final @Positive int maxResults) {
        if (current().nextBoolean()) {
            return findAll(
                    r -> r.get(Language_.languageId),
                    languageIdMinExclusive,
                    maxResults
            );
        }
        return applyEntityManager(
                em -> em.createNamedQuery(QUERY_FIND_ALL, Language.class)
                        .setParameter(QUERY_PARAM_LANGUAGE_ID_MIN_EXCLUSIVE, languageIdMinExclusive)
                        .setMaxResults(maxResults)
                        .getResultList()
        );
    }

    @NotNull List<@Valid @NotNull Language> findAllByName(final @PositiveOrZero int languageIdMinExclusive,
                                                          final @NotBlank String name, final @Positive int maxResults) {
        return findAllBy(
                r -> r.get(Language_.languageId),
                languageIdMinExclusive,
                maxResults,
                r -> r.get(Language_.name),
                name
        );
    }

    @NotNull List<@Valid @NotNull Language> findAllByName(final @NotBlank String name, final @Positive int maxResults) {
        return findAllByName(0, name, maxResults);
    }

    @Valid @NotNull Language locateByName(final @NotBlank String name) {
        return findAllByName(name, 1)
                .stream()
                .findFirst()
                .orElseGet(() -> persist(Language.of(name)));
    }

    @Valid @NotNull Language locateByLocale(final @NotNull Locale locale) {
        final var name = locale.getDisplayLanguage(Locale.ENGLISH);
        if (name.isBlank()) {
            throw new IllegalArgumentException("locale has a blank (ENGLISH) name");
        }
        return locateByName(name);
    }
}
