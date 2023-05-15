package com.github.jinahya.persistence;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * A class for identifying {@link FilmList} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public class FilmListId
        implements Serializable {

    @Serial
    private static final long serialVersionUID = -5606838902199850066L;

    /**
     * Creates a new instance with specified values.
     *
     * @param fid      a value for {@code fid} property.
     * @param category a value for {@code category} property.
     * @return a new instance of {@code fid} and {@code category}.
     */
    static FilmListId of(final Integer fid, final String category) {
        final var instance = new FilmListId();
        instance.fid = fid;
        instance.category = category;
        return instance;
    }

    /**
     * Creates a new instance.
     */
    protected FilmListId() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "fid=" + fid +
               ",category=" + category +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FilmListId that)) return false;
        return Objects.equals(fid, that.fid) &&
               Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fid, category);
    }

    public Integer getFid() {
        return fid;
    }

    private void setFid(final Integer fid) {
        this.fid = fid;
    }

    public String getCategory() {
        return category;
    }

    private void setCategory(final String category) {
        this.category = category;
    }

    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    private Integer fid;

    private String category;
}
