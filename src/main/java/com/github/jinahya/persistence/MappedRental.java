package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "rental", schema = "sakila", catalog = "")
public class MappedRental {

public static final String TABLE_NAME = "rental";
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "rental_id", nullable = false)
    private Integer rentalId;

    @Basic
    @Column(name = "rental_date", nullable = false)
    private Timestamp rentalDate;

    @Basic
    @Column(name = "inventory_id", nullable = false)
    private Object inventoryId;

    @Basic
    @Column(name = "customer_id", nullable = false)
    private Object customerId;

    @Basic
    @Column(name = "return_date", nullable = true)
    private Timestamp returnDate;

    @Basic
    @Column(name = "staff_id", nullable = false)
    private Object staffId;

    @Basic
    @Column(name = "last_update", nullable = false)
    private Timestamp lastUpdate;

    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }

    public Timestamp getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Timestamp rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Object getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Object inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Object getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Object customerId) {
        this.customerId = customerId;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    public Object getStaffId() {
        return staffId;
    }

    public void setStaffId(Object staffId) {
        this.staffId = staffId;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MappedRental that = (MappedRental) obj;
        return Objects.equals(rentalId, that.rentalId) && Objects.equals(rentalDate, that.rentalDate) && Objects.equals(inventoryId, that.inventoryId) && Objects.equals(customerId, that.customerId) && Objects.equals(returnDate, that.returnDate) && Objects.equals(staffId, that.staffId) && Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalId, rentalDate, inventoryId, customerId, returnDate, staffId, lastUpdate);
    }
}
