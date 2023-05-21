package com.github.jinahya.persistence;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * A class for identifying {@link FilmCategory} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public class FilmCategoryId
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 3359964322700774044L;

    /**
     * Creates a new instance with specified values.
     *
     * @param filmId     a value for {@code filmId} property.
     * @param categoryId a value for {@code categoryId} property.
     * @return a new instance.
     */
    public static FilmCategoryId of(final Integer filmId, final Integer categoryId) {
        final var instance = new FilmCategoryId();
        instance.filmId = filmId;
        instance.categoryId = categoryId;
        return instance;
    }

    /**
     * Creates a new instance.
     */
    protected FilmCategoryId() {
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

    /**
     * Returns current value of {@code filmId} property.
     *
     * @return current value of {@code filmId} property.
     */
    public Integer getFilmId() {
        return filmId;
    }

    /**
     * Returns current value of {@code categoryId} property.
     *
     * @return current value of {@code categoryId} property.
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    private Integer filmId;

    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    private Integer categoryId;
}
