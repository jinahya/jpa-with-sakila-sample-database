package com.github.jinahya.persistence;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class FilmListId
        implements Serializable {

    @Serial
    private static final long serialVersionUID = -5606838902199850066L;

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
        return Objects.equals(fid, that.fid) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fid, category);
    }

    public Integer getFid() {
        return fid;
    }

    void setFid(final Integer fid) {
        this.fid = fid;
    }

    public String getCategory() {
        return category;
    }

    void setCategory(final String category) {
        this.category = category;
    }

    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    private Integer fid;

    private String category;
}
