package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "sales_by_store", schema = "sakila", catalog = "")
public class MappedSalesByStore {

    @Basic
    @Column(name = "store", nullable = true, length = 101)
    private String store;

    @Basic
    @Column(name = "manager", nullable = true, length = 91)
    private String manager;

    @Basic
    @Column(name = "total_sales", nullable = true, precision = 2)
    private BigDecimal totalSales;

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
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

        MappedSalesByStore that = (MappedSalesByStore) obj;

        if (store != null ? !store.equals(that.store) : that.store != null) return false;
        if (manager != null ? !manager.equals(that.manager) : that.manager != null) return false;
        if (totalSales != null ? !totalSales.equals(that.totalSales) : that.totalSales != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = store != null ? store.hashCode() : 0;
        result = 31 * result + (manager != null ? manager.hashCode() : 0);
        result = 31 * result + (totalSales != null ? totalSales.hashCode() : 0);
        return result;
    }
}
