package com.github.jinahya.persistence.sakila;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.math.BigDecimal;

/**
 * An entity class for mapping {@link #VIEW_NAME} view.
 * <p>
 * <cite><a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-views-sales_by_store.html">5.2.6 The
 * sales_by_store View</a></cite>
 * <blockquote>
 * <p>The {@value #VIEW_NAME} view provides a list of total sales, broken down by store.</p>
 * <p>The view returns the store location, manager name, and total sales.</p>
 * <p>The {@value #VIEW_NAME} view incorporates data from the {@value City#TABLE_NAME}, {@value Country#TABLE_NAME},
 * {@value Payment#TABLE_NAME}, {@value Rental#TABLE_NAME}, {@value Inventory#TABLE_NAME}, {@value Store#TABLE_NAME},
 * {@value Address#TABLE_NAME}, and {@link Staff#TABLE_NAME} tables.</p>
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-views-sales_by_store.html">5.2.6 The
 * sales_by_store View</a>
 */
@MappedSuperclass
public class SalesByStore {

    /**
     * The name of the database view to which this class maps. The value is {@value}.
     */
    public static final String VIEW_NAME = "sales_by_store";

    @Override
    public String toString() {
        return super.toString() + '{' +
               "store='" + store +
               ",manager='" + manager +
               ",totalSales=" + totalSales +
               '}';
    }

    /**
     * Returns current value of {@link SalesByStore_#store store} attribute.
     *
     * @return current value of the {@link SalesByStore_#store store} attribute.
     */
    public String getStore() {
        return store;
    }

    /**
     * Returns current value of {@link SalesByStore_#manager manager} attribute.
     *
     * @return current value of the {@link SalesByStore_#manager manager} attribute.
     */
    public String getManager() {
        return manager;
    }

    /**
     * Returns current value of {@link SalesByStore_#totalSales totalSales} attribute.
     *
     * @return current value of the {@link SalesByStore_#totalSales totalSales} attribute.
     */
    public BigDecimal getTotalSales() {
        return totalSales;
    }

    @Basic(optional = true)
    @Column(name = "store", nullable = true, length = 101, insertable = false, updatable = false)
    private String store;

    @Basic(optional = true)
    @Column(name = "manager", nullable = true, length = 91, insertable = false, updatable = false)
    private String manager;

    @Basic(optional = true)
    @Column(name = "total_sales", nullable = true, precision = 27, scale = 2, insertable = false, updatable = false)
    private BigDecimal totalSales;
}
