package com.github.jinahya.persistence.sakila;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = SalesByFilmCategory.VIEW_NAME)
public class SalesByFilmCategory {

    /**
     * The name of the database view to which this class maps. The value is {@value}.
     */
    public static final String VIEW_NAME = "sales_by_film_category";

    @NotNull
    @Id
    @Basic(optional = false)
    @Column(name = "category", nullable = false, length = 25)
    private String category;

    @Basic(optional = true)
    @Column(name = "total_sales", nullable = true, precision = 27, scale = 2)
    private BigDecimal totalSales;
}
