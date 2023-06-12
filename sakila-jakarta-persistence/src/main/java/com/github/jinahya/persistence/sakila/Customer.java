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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

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

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "customer";

    /**
     * The name of the table column to which {@link Customer_#customerId customerId} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_CUSTOMER_ID = "customer_id";

    /**
     * The name of the table column to which {@link Customer_#storeId storeId} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_STORE_ID = Store.COLUMN_NAME_STORE_ID;

    /**
     * The name of the table column to which {@link Customer_#address address} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_ADDRESS_ID = Address.COLUMN_NAME_ADDRESS_ID;

    /**
     * The name of the table column to which {@link Customer_#active active} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_ACTIVE = "active";

    /**
     * The name of the table column to which {@link Customer_#createDate createDate} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_CREATE_DATE = "create_date";

    public static Customer ofCustomerId(final int customerId) {
        final var instance = new Customer();
        instance.customerId = customerId;
        return instance;
    }

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
    Integer identifier() {
        return getCustomerId();
    }

    /**
     * Returns current value of {@link Customer_#customerId customerId} attribute.
     *
     * @return current value of the {@link Customer_#customerId customerId} attribute.
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * Replaces current value of {@link Customer_#customerId customerId} attribute with specified value.
     *
     * @param customerId new value for the {@link Customer_#customerId customerId} attribute.
     * @deprecated for removal; the {@link Customer_#customerId customerId} is an auto-increment column.
     */
    @Deprecated(forRemoval = true)
    private void setCustomerId(final Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * Returns current value of {@link Customer_#storeId storeId} attribute.
     *
     * @return current value of the {@link Customer_#storeId storeId} attribute.
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * Replaces current value of {@link Customer_#storeId storeId} attribute with specified value.
     *
     * @param storeId new value for the {@link Customer_#storeId storeId} attribute
     */
    protected void setStoreId(final Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * Returns current value of {@link Customer_#firstName firstName} attribute.
     *
     * @return current value of the {@link Customer_#firstName firstName} attribute.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Replaces current value of {@link Customer_#firstName firstName} attribute with specified value.
     *
     * @param firstName new value for the {@link Customer_#firstName firstName} attribute
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns current value of {@link Customer_#lastName lastName} attribute.
     *
     * @return current value of the {@link Customer_#lastName lastName} attribute.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Replaces current value of {@link Customer_#lastName lastName} attribute with specified value.
     *
     * @param lastName new value for the {@link Customer_#lastName lastName} attribute
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns current value of {@link Customer_#email email} attribute.
     *
     * @return current value of the {@link Customer_#email email} attribute.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Replaces current value of {@link Customer_#email email} attribute with specified value.
     *
     * @param email new value for the {@link Customer_#email email} attribute
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Returns current value of {@link Customer_#addressId addressId} attribute.
     *
     * @return current value of the {@link Customer_#addressId addressId} attribute.
     */
    public Integer getAddressId() {
        return addressId;
    }

    /**
     * Replaces current value of {@link Customer_#addressId addressId} attribute with specified value.
     *
     * @param addressId new value for the {@link Customer_#addressId addressId} attribute
     */
    void setAddressId(final Integer addressId) {
        this.addressId = addressId;
    }

    /**
     * Returns current value of {@link Customer_#active active} attribute.
     *
     * @return current value of the {@link Customer_#active active} attribute.
     */
    public Integer getActive() {
        return active;
    }

    /**
     * Replaces current value of {@link Customer_#active active} attribute with specified value.
     *
     * @param active new value for the {@link Customer_#active active} attribute
     */
    void setActive(final Integer active) {
        this.active = active;
    }

    /**
     * Returns current value of {@link Customer_#createDate createDate} attribute.
     *
     * @return current value of the {@link Customer_#createDate createDate} attribute.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Replaces current value of {@link Customer_#createDate createdDate} attribute with specified value.
     *
     * @param createDate new value for the {@link Customer_#createDate createdDate} attribute
     * @deprecated for removal; the {@value #COLUMN_NAME_CREATE_DATE} column is set while being inserted; see
     * {@code customer_create_date} trigger.
     */
    @Deprecated(forRemoval = true)
    private void setCreateDate(final LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-customer.html">
     * A surrogate primary key used to uniquely identify each customer in the table.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
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
    @Max(_DomainConstants.MAX_TINYINT_UNSIGNED)
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
    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
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
    @Column(name = "active", nullable = false) // TODO: Use COLUMN_NAME_ACTIVE for the name element!
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

    /**
     * Returns current value of {@link Customer_#store store} attribute.
     *
     * @return current value of the {@link Customer_#store store} attribute.
     */
    public Store getStore() {
        return store;
    }

    /**
     * Replaces current value of {@link Customer_#store store} attribute.
     *
     * @param store new value for the {@link Customer_#store store} attribute.
     */
    public void setStore(final Store store) {
        this.store = store;
        setStoreId(
                ofNullable(this.store)
                        .map(Store::getStoreId)
                        .orElse(null)
        );
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_STORE_ID, nullable = false, insertable = false, updatable = false)
    private Store store;

    /**
     * Returns current value of {@link Customer_#address address} attribute.
     *
     * @return current value of the {@link Customer_#address address} attribute.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Replaces current value of {@link Customer_#address address} attribute with specified value.
     *
     * @param address new value of the {@link Customer_#address address} attribute.
     * @apiNote This method also updates {@link Customer_#addressId} attribute with {@code address?.addressId}.
     */
    public void setAddress(final Address address) {
        this.address = address;
        setAddressId(
                ofNullable(this.address)
                        .map(Address::getAddressId)
                        .orElse(null)
        );
    }

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_ADDRESS_ID, nullable = false, insertable = false, updatable = false)
    private Address address;

    /**
     * Returns current value of {@link Customer_#active active} attribute as a boolean value.
     *
     * @return a boolean value represents current value of the {@link Customer_#active active} attribute.
     */
    @Transient
    public Boolean getActiveAsBoolean() {
        return ofNullable(getActive())
                .map(_DomainConverters.BooleanConverter::intToBoolean)
                .orElse(null);
    }

    /**
     * Replaces current value of {@link Customer_#active active} attribute with a value converted from specified boolean
     * value.
     *
     * @param activeAsBoolean the boolean value for the {@link Customer_#active active} attribute; {@link Boolean#FALSE}
     *                        for {@code 0}, {@link Boolean#TRUE} for {@code 1}.
     */
    public void setActiveAsBoolean(final Boolean activeAsBoolean) {
        setActive(
                ofNullable(activeAsBoolean)
                        .map(_DomainConverters.BooleanConverter::booleanToInt)
                        .orElse(null)
        );
    }

    /**
     * Activates this customer, and returns this customer.
     *
     * @return this customer.
     * @implNote This method invokes {@link #setActiveAsBoolean(Boolean)} method with {@link Boolean#TRUE}.
     */
    public Customer activate() {
        setActiveAsBoolean(Boolean.TRUE);
        return this;
    }

    /**
     * Deactivates this customer, and returns this customer.
     *
     * @return this customer.
     * @implNote This method invokes {@link #setActiveAsBoolean(Boolean)} method with {@link Boolean#FALSE}.
     */
    public Customer deactivate() {
        setActiveAsBoolean(Boolean.FALSE);
        return this;
    }
}
