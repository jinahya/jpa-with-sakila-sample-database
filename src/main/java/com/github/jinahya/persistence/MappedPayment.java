package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "payment", schema = "sakila", catalog = "")
public class MappedPayment {

    public static final String TABLE_NAME = "payment";

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "payment_id", nullable = false)
    private Object paymentId;

    @Basic
    @Column(name = "customer_id", nullable = false)
    private Object customerId;

    @Basic
    @Column(name = "staff_id", nullable = false)
    private Object staffId;

    @Basic
    @Column(name = "rental_id", nullable = true)
    private Integer rentalId;

    @Basic
    @Column(name = "amount", nullable = false, precision = 2)
    private BigDecimal amount;

    @Basic
    @Column(name = "payment_date", nullable = false)
    private Timestamp paymentDate;

    @Basic
    @Column(name = "last_update", nullable = true)
    private Timestamp lastUpdate;

    public Object getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Object paymentId) {
        this.paymentId = paymentId;
    }

    public Object getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Object customerId) {
        this.customerId = customerId;
    }

    public Object getStaffId() {
        return staffId;
    }

    public void setStaffId(Object staffId) {
        this.staffId = staffId;
    }

    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
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
        MappedPayment that = (MappedPayment) obj;
        return Objects.equals(paymentId, that.paymentId) && Objects.equals(customerId, that.customerId) && Objects.equals(staffId, that.staffId) && Objects.equals(rentalId, that.rentalId) && Objects.equals(amount, that.amount) && Objects.equals(paymentDate, that.paymentDate) && Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, customerId, staffId, rentalId, amount, paymentDate, lastUpdate);
    }
}
