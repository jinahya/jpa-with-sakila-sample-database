package com.github.jinahya.persistence;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A service class for {@link Language}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public class LanguageService
        extends _BaseEntityService<Language, Integer> {

    protected LanguageService() {
        super(Language.class, Integer.class);
    }

    @Override
    public @NotNull List<Language> findAll(Integer maxResults) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return super.findAll(maxResults);
        }
        if (ThreadLocalRandom.current().nextBoolean()) {
            return findAllByLanguageIdGreaterThan(0, maxResults);
        }
        return applyEntityManager(em -> {
            final var query = em.createNamedQuery(LanguageConstants.NAMED_QUERY_FIND_ALL, Language.class);
            if (maxResults != null) {
                query.setMaxResults(maxResults);
            }
            return query.getResultList();
        });
    }

    @NotNull
    public List<@Valid @NotNull Language> findAllByLanguageIdGreaterThan(
            @PositiveOrZero final int languageIdMinExclusive, @Positive final Integer maxResults) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return findAllByIdGreaterThan(
                    r -> r.get(Language_.languageId),
                    languageIdMinExclusive,
                    maxResults
            );
        }
        return applyEntityManager(
                em -> {
                    final var query = em.createNamedQuery(
                            LanguageConstants.NAMED_QUERY_FIND_ALL_BY_LANGUAGE_ID_GREATER_THAN,
                            Language.class);
                    query.setParameter("languageIdMinExclusive", languageIdMinExclusive);
                    if (maxResults != null) {
                        query.setMaxResults(maxResults);
                    }
                    return query.getResultList();
                }
        );
    }

    @NotNull
    public List<@Valid @NotNull Language> findAllByName(@NotBlank final String name) {
        return applyEntityManager(
                em -> em.createNamedQuery(LanguageConstants.NAMED_QUERY_FIND_ALL_BY_NAME, Language.class)
                        .setParameter("name", name)
                        .getResultList()
        );
    }

    @Valid
    @NotNull
    public Language locateByName(@NotBlank final String name) {
        return findAllByName(name)
                .stream()
                .findFirst()
                .orElseGet(() -> persist(Language.of(name)));
    }

    @Valid
    @NotNull
    public Language locateByLocale(@NotNull final Locale locale) {
        return locateByName(locale.getDisplayLanguage(Locale.ENGLISH));
    }
}
