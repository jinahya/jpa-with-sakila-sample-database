package com.github.jinahya.persistence.sakila;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * .
 * <p>
 * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-payment.html">
 * The {@code payment} table records each payment made by a customer, with information such as the amount and the rental
 * being paid for (when applicable).<br/>The {@code payment} table refers to the {@value Customer#TABLE_NAME},
 * {@value Rental#TABLE_NAME}, and {@value Staff#TABLE_NAME} tables.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-payment.html">5.1.13 The payment Table</a>
 */
@Entity
@Table(name = Payment.TABLE_NAME)
public class Payment
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "payment";

    public static final String COLUMN_NAME_PAYMENT_ID = "payment_id";

    public static final String COLUMN_NAME_CUSTOMER_ID = Customer.COLUMN_NAME_CUSTOMER_ID;

    public static final String COLUMN_NAME_STAFF_ID = Staff.COLUMN_NAME_STAFF_ID;

    public static final String COLUMN_NAME_RENTAL_ID = Rental.COLUMN_NAME_RENTAL_ID;

    public static final String COLUMN_NAME_AMOUNT = "amount";

    public static final int COLUMN_PRECISION_AMOUNT = 5;

    public static final int COLUMN_SCALE_AMOUNT = 2;

    /**
     * Creates a new instance.
     */
    public Payment() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "paymentId=" + paymentId +
               ",customerId=" + customerId +
               ",staffId=" + staffId +
               ",rentalId=" + rentalId +
               ",amount=" + amount +
               ",paymentDate=" + paymentDate +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Payment)) return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    Integer identifier() {
        return getPaymentId();
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    @Deprecated
    private void setPaymentId(final Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(final Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(final Integer rentalId) {
        this.rentalId = rentalId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    @Deprecated
    private void setPaymentDate(final LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-payment.html">
     * A surrogate primary key used to uniquely identify each payment.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_PAYMENT_ID, nullable = false,
            insertable = /*false*/true, // EclipseLink
            updatable = false)
    private Integer paymentId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-payment.html">
     * The customer whose balance the payment is being applied to. This is a foreign key reference to the
     * {@value Customer#TABLE_NAME} table.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_CUSTOMER_ID, nullable = false)
    private Integer customerId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-payment.html">
     * The staff member who processed the payment. This is a foreign key reference to the {@value Staff#TABLE_NAME}
     * table.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_STAFF_ID, nullable = false, updatable = false)
    private Integer staffId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-payment.html">
     * The rental that the payment is being applied to. This is optional because some payments are for outstanding fees
     * and may not be directly related to a rental.
     * </blockquote>
     */
    @Basic(optional = true)
    @Column(name = COLUMN_NAME_RENTAL_ID, nullable = true, updatable = false)
    private Integer rentalId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-payment.html">
     * The amount of the payment.
     * </blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_AMOUNT, nullable = false, scale = COLUMN_SCALE_AMOUNT,
            precision = COLUMN_PRECISION_AMOUNT, updatable = false)
    private BigDecimal amount;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-payment.html">
     * The date the payment was processed.
     * </blockquote>
     */
    @Basic(optional = false)
    @Column(name = "payment_date", nullable = false, insertable = false, updatable = false)
    private LocalDateTime paymentDate;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
        setCustomerId(
                Optional.ofNullable(this.customer)
                        .map(Customer::getCustomerId)
                        .orElse(null)
        );
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_CUSTOMER_ID, nullable = false, insertable = false, updatable = false)
    private Customer customer;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(final Staff staff) {
        this.staff = staff;
        setStaffId(
                Optional.ofNullable(this.staff)
                        .map(Staff::getStaffId)
                        .orElse(null)
        );
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_STAFF_ID, nullable = false, insertable = false, updatable = false)
    private Staff staff;

    public Rental getRental() {
        return rental;
    }

    public void setRental(final Rental rental) {
        this.rental = rental;
        setRentalId(
                Optional.ofNullable(this.rental)
                        .map(Rental::getRentalId)
                        .orElse(null)
        );
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_RENTAL_ID, nullable = true, insertable = false, updatable = false)
    private Rental rental;
}
