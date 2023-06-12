package com.github.jinahya.persistence.sakila;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.github.jinahya.persistence.sakila._DomainConstants.MAX_SMALLINT_UNSIGNED;

/**
 * An entity class for mapping {@value #TABLE_NAME} table.
 * <p>
 * <blockquote>
 * The {@value #TABLE_NAME} table contains one row for each rental of each inventory item with information about who
 * rented what item, when it was rented, and when it was returned.<br/>The {@value #TABLE_NAME} table refers to the
 * {@value Inventory#TABLE_NAME}, {@value Customer#TABLE_NAME}, and {@value Staff#TABLE_NAME} tables and is referred to
 * by the {@value Payment#TABLE_NAME} table.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-rental.html">5.1.14 The rental Table</a>
 */
@Entity
@Table(
        name = Rental.TABLE_NAME,
        indexes = {
                @Index(columnList = Rental.COLUMN_NAME_INVENTORY_ID),
                @Index(columnList = Rental.COLUMN_NAME_CUSTOMER_ID),
                @Index(columnList = Rental.COLUMN_NAME_STAFF_ID)
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        Rental.COLUMN_NAME_RENTAL_DATE,
                        Rental.COLUMN_NAME_INVENTORY_ID,
                        Rental.COLUMN_NAME_CUSTOMER_ID
                })
        }
)
public class Rental
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "rental";

    public static final String COLUMN_NAME_RENTAL_ID = "rental_id";

    public static final String COLUMN_NAME_RENTAL_DATE = "rental_date";

    /**
     * The name of the table column to which {@link Rental_#inventoryId inventoryId} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_INVENTORY_ID = Inventory.COLUMN_NAME_INVENTORY_ID;

    public static final String COLUMN_NAME_CUSTOMER_ID = Customer.COLUMN_NAME_CUSTOMER_ID;

    public static final String COLUMN_NAME_STAFF_ID = Staff.COLUMN_NAME_STAFF_ID;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public Rental() {
        super();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Rental that)) return false;
        return Objects.equals(rentalDate, that.rentalDate) &&
               Objects.equals(inventoryId, that.inventoryId) &&
               Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                rentalDate,
                inventoryId,
                customerId
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    Integer identifier() {
        return getRentalId();
    }

    // -------------------------------------------------------------------------------------------------------- rentalId
    public Integer getRentalId() {
        return rentalId;
    }

    /**
     * Replaces current value of {@link Rental_#rentalId rentalId} attribute with specified value.
     *
     * @param rentalId new value for the {@link Rental_#rentalId rentalId} attribute.
     * @deprecated for removal; The {@value #COLUMN_NAME_RENTAL_ID} column is an auto-increment column.
     */
    @Deprecated(forRemoval = true)
    private void setRentalId(final Integer rentalId) {
        this.rentalId = rentalId;
    }

    // ------------------------------------------------------------------------------------------------------ rentalDate
    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(final LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }

    // ------------------------------------------------------------------------------------------- inventoryId/inventory
    public Integer getInventoryId() {
        return inventoryId;
    }

    protected void setInventoryId(final Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(final Inventory inventory) {
        this.inventory = inventory;
        setInventoryId(
                Optional.ofNullable(this.inventory)
                        .map(Inventory::getInventoryId)
                        .orElse(null)
        );
    }

    // --------------------------------------------------------------------------------------------- customerId/customer
    public Integer getCustomerId() {
        return customerId;
    }

    void setCustomerId(final Integer customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    /**
     * Replaces current value of {@link Rental_#customer customer} attribute with specified value.
     *
     * @param customer new value for the {@link Rental_#customer customer} attribute.
     * @apiNote This method also replaces current value of {@link Rental_#customerId customerId} with
     * {@code customer?.customerId}.
     */
    public void setCustomer(final Customer customer) {
        this.customer = customer;
        setCustomerId(
                Optional.ofNullable(this.customer)
                        .map(Customer::getCustomerId)
                        .orElse(null)
        );
    }

    // ------------------------------------------------------------------------------------------------------ returnDate
    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(final LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    // --------------------------------------------------------------------------------------------------- staffId/staff
    public Integer getStaffId() {
        return staffId;
    }

    void setStaffId(final Integer staffId) {
        this.staffId = staffId;
    }

    /**
     * Returns current value of {@link Rental_#staff staff} attribute.
     *
     * @return current value of the {@link Rental_#staff staff} attribute.
     */
    public Staff getStaff() {
        return staff;
    }

    /**
     * Replaces current value of {@link Rental_#staff staff} attribute with specified value.
     *
     * @param staff new value for the {@link Rental_#staff staff} attribute.
     * @apiNote This method also replaces current value of {@link Rental_#staffId staffId} attribute with
     * {@code staff?.staffId}.
     */
    public void setStaff(final Staff staff) {
        this.staff = staff;
        setStaffId(
                Optional.ofNullable(this.staff)
                        .map(Staff::getStaffId)
                        .orElse(null)
        );
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-rental.html">
     * A surrogate primary key that uniquely identifies the rental.
     * </blockquote>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_RENTAL_ID, nullable = false,
            insertable = true, // EclipseLink
            updatable = false)
    private Integer rentalId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-rental.html">
     * The date and time that the item was rented.
     * </blockquote>
     */
    @PastOrPresent
    @NotNull
    @Basic(optional = false)
    @Column(name = "rental_date", nullable = false, insertable = true, updatable = false)
    private LocalDateTime rentalDate;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-rental.html">
     * The item being rented.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_MEDIUMINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_INVENTORY_ID, nullable = false, insertable = true, updatable = false)
    private Integer inventoryId;

    /**
     * 이 대여 항목에 매핑된 재고.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_INVENTORY_ID, nullable = false, insertable = false, updatable = false)
    private Inventory inventory;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-rental.html">
     * The customer renting the item.
     * </blockquote>
     */
    @Max(MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_CUSTOMER_ID, nullable = false, insertable = true, updatable = false)
    private Integer customerId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_CUSTOMER_ID, nullable = false, insertable = false, updatable = false)
    private Customer customer;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-rental.html">
     * The date and time the item was returned.
     * </blockquote>
     */
    @PastOrPresent
    @Basic(optional = true)
    @Column(name = "return_date", nullable = true, insertable = false)
    private LocalDateTime returnDate;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-rental.html">
     * The staff member who processed the rental.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_STAFF_ID, nullable = false, updatable = false)
    private Integer staffId;

    /**
     * 이 대여 항목을 처리(process)한 직원.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_STAFF_ID, nullable = false, insertable = false, updatable = false)
    private Staff staff;
}
