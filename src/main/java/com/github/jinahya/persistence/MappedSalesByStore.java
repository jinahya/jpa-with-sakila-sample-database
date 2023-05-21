package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;

import java.math.BigDecimal;

/**
 * <blockquote>
 * <p>The {@value #VIEW_NAME} view provides a list of total sales, broken down by store.</p>
 * <p>The view returns the store location, manager name, and total sales.</p>
 * <p>The {@value #VIEW_NAME} view incorporates data from the {@value City#TABLE_NAME}, {@value Country#TABLE_NAME},
 * {@value Payment#TABLE_NAME} {@value Rental#TABLE_NAME}, inventory, store, address, and staff tables.</p>
 * </blockquote>
 */
public class MappedSalesByStore {

    public static final String VIEW_NAME = "sales_by_store";

    @Basic(optional = true)
    @Column(name = "store", nullable = true, length = 101)
    private String store;

    @Basic(optional = true)
    @Column(name = "manager", nullable = true, length = 91)
    private String manager;

    @Basic(optional = true)
    @Column(name = "total_sales", nullable = true, precision = 27, scale = 2)
    private BigDecimal totalSales;
}
