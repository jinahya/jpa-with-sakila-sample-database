package com.github.jinahya.persistence.sakila;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * A class for identifying {@link FilmCategory} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see FilmCategory
 */
@Embeddable
public class FilmCategoryId
        implements Serializable,
                   Comparable<FilmCategoryId> {

    @Serial
    private static final long serialVersionUID = 3359964322700774044L;

    private static final Comparator<FilmCategoryId> NATUAL_ORDER =
            Comparator.comparing(FilmCategoryId::getFilmId)
                    .thenComparing(FilmCategoryId::getCategoryId);

    /**
     * Creates a new instance with specified values.
     *
     * @param filmId     a value for {@code filmId} property.
     * @param categoryId a value for {@code categoryId} property.
     * @return a new instance.
     */
    public static FilmCategoryId of(final Integer filmId, final Integer categoryId) {
        final var instance = new FilmCategoryId();
        instance.setFilmId(filmId);
        instance.setCategoryId(categoryId);
        return instance;
    }

    /**
     * Creates a new instance.
     */
    public FilmCategoryId() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "filmId=" + filmId +
               ",categoryId=" + categoryId +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FilmCategoryId that)) return false;
        return Objects.equals(filmId, that.filmId) &&
               Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, categoryId);
    }

    @Override
    public int compareTo(final FilmCategoryId o) {
        return NATUAL_ORDER.compare(this, o);
    }

    /**
     * Returns current value of {@code filmId} property.
     *
     * @return current value of the {@code filmId} property.
     */
    public Integer getFilmId() {
        return filmId;
    }

    /**
     * Replaces current value of {@code filmId} property with specified value.
     *
     * @param filmId new value for the {@code filmId} property.
     */
    void setFilmId(final Integer filmId) {
        this.filmId = filmId;
    }

    /**
     * Returns current value of {@code categoryId} property.
     *
     * @return current value of the {@code categoryId} property.
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * Replaces current value of {@code categoryId} property with specified value.
     *
     * @param categoryId new value for the {@code categoryId} property.
     */
    void setCategoryId(final Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    private Integer filmId;

    @Max(_DomainConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    private Integer categoryId;
}
