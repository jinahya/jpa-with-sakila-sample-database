package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * .
 * <p>
 * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-customer.html">
 * The {@value #TABLE_NAME} table contains a list of all customers.<br/>The {@value #TABLE_NAME} table is referred to in
 * the {@value Payment#TABLE_NAME} and {@value Rental#TABLE_NAME} tables and refers to the {@value Address#TABLE_NAME}
 * and {@value Store#TABLE_NAME} tables using foreign keys.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-customer.html">5.1.6 The customer Table</a>
 */
@Entity
@Table(name = Customer.TABLE_NAME)
public class Customer
        extends _BaseEntity<Integer> {

    public static final String TABLE_NAME = "customer";

    public static final String COLUMN_NAME_CUSTOMER_ID = "customer_id";

    public static final String COLUMN_NAME_STORE_ID = Store.COLUMN_NAME_STORE_ID;

    public static final String COLUMN_NAME_ADDRESS_ID = Address.COLUMN_NAME_ADDRESS_ID;

    /**
     * Creates a new instance.
     */
    public Customer() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "customerId=" + customerId +
               ",storeId=" + storeId +
               ",firstName=" + firstName +
               ",lastName=" + lastName +
               ",email='" + email +
               ",addressId=" + addressId +
               ",active=" + active +
               ",createDate=" + createDate +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Customer)) return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    protected Integer identifier() {
        return getCustomerId();
    }

    public Integer getCustomerId() {
        return customerId;
    }

    @Deprecated
    private void setCustomerId(final Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @Deprecated
    private void setCreateDate(final LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-customer.html">
     * A surrogate primary key used to uniquely identify each customer in the table.
     * </blockquote>
     */
    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_CUSTOMER_ID, nullable = false,
            insertable = true, // EclipseLink
            updatable = false)
    private Integer customerId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-customer.html">
     * A foreign key identifying the customer “home store.” Customers are not limited to renting only from this store,
     * but this is the store at which they generally shop.
     * </blockquote>
     */
    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_STORE_ID, nullable = false)
    private Integer storeId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-customer.html">
     * The customer first name.
     * </blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-customer.html">
     * The customer last name.
     * </blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-customer.html">
     * The customer email address.
     * </blockquote>
     */
    @Email
    @Basic(optional = true)
    @Column(name = "email", nullable = true, length = 50)
    private String email;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-customer.html">
     * A foreign key identifying the customer address in the {@value Address#TABLE_NAME} table.
     * </blockquote>
     */
    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_ADDRESS_ID, nullable = false)
    private Integer addressId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-customer.html">
     * Indicates whether the customer is an active customer. Setting this to {@code FALSE} serves as an alternative to
     * deleting a customer outright. Most queries should have a {@code WHERE active = TRUE} clause.
     * </blockquote>
     */
    @Max(Byte.MAX_VALUE)
    @Min(Byte.MIN_VALUE)
    @NotNull
    @Basic(optional = false)
    @Column(name = "active", nullable = false)
    private Integer active;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-customer.html">
     * The date the customer was added to the system. This date is automatically set using a trigger during an
     * {@code INSERT}.
     * </blockquote>
     */
    @Basic(optional = false)
    @Column(name = "create_date", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createDate;

    public Store getStore() {
        return store;
    }

    public void setStore(final Store store) {
        this.store = store;
        setStoreId(
                Optional.ofNullable(this.store)
                        .map(Store::getStoreId)
                        .orElse(null)
        );
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_STORE_ID, nullable = false, insertable = false, updatable = false)
    private Store store;

    @Transient
    public Boolean getActiveAsBoolean() {
        return Optional.ofNullable(getActive())
                .map(_PersistenceConverters.BooleanConverter::intToBoolean)
                .orElse(null);
    }

    public void setActiveAsBoolean(final Boolean activeAsBoolean) {
        setActive(
                Optional.of(activeAsBoolean)
                        .map(_PersistenceConverters.BooleanConverter::booleanToInt)
                        .orElse(null)
        );
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
        setAddressId(
                Optional.ofNullable(this.address)
                        .map(Address::getAddressId)
                        .orElse(null)
        );
    }

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_ADDRESS_ID, nullable = false, insertable = false, updatable = false)
    private Address address;
}
