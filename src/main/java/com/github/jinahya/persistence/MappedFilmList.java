package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "film_list", schema = "sakila", catalog = "")
public class MappedFilmList {

    @Basic
    @Column(name = "FID", nullable = false)
    private Object fid;

    @Basic
    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;

    @Basic
    @Column(name = "category", nullable = true, length = 25)
    private String category;

    @Basic
    @Column(name = "price", nullable = false, precision = 2)
    private BigDecimal price;

    @Basic
    @Column(name = "length", nullable = true)
    private Object length;

    @Basic
    @Column(name = "rating", nullable = true)
    private Object rating;

    @Basic
    @Column(name = "actors", nullable = true, length = -1)
    private String actors;

    public Object getFid() {
        return fid;
    }

    public void setFid(Object fid) {
        this.fid = fid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Object getLength() {
        return length;
    }

    public void setLength(Object length) {
        this.length = length;
    }

    public Object getRating() {
        return rating;
    }

    public void setRating(Object rating) {
        this.rating = rating;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        MappedFilmList that = (MappedFilmList) obj;

        if (fid != null ? !fid.equals(that.fid) : that.fid != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (length != null ? !length.equals(that.length) : that.length != null) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (actors != null ? !actors.equals(that.actors) : that.actors != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fid != null ? fid.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (actors != null ? actors.hashCode() : 0);
        return result;
    }
}
