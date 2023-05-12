package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "sales_by_film_category", schema = "sakila", catalog = "")
public class MappedSalesByFilmCategory {

    @Basic
    @Column(name = "category", nullable = false, length = 25)
    private String category;

    @Basic
    @Column(name = "total_sales", nullable = true, precision = 2)
    private BigDecimal totalSales;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        MappedSalesByFilmCategory that = (MappedSalesByFilmCategory) obj;

        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (totalSales != null ? !totalSales.equals(that.totalSales) : that.totalSales != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (totalSales != null ? totalSales.hashCode() : 0);
        return result;
    }
}
