package com.vaccine.tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing an order for vaccine schedules in the system.
 */
@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "order_number", nullable = false)
    private String orderNumber;
    
    @NotNull
    @Column(nullable = false)
    private BigDecimal totalAmount;
    
    @Column(name = "discount_amount")
    private BigDecimal discountAmount = BigDecimal.ZERO;
    
    @Column(name = "final_amount", nullable = false)
    private BigDecimal finalAmount;
    
    @Column(nullable = false)
    private boolean paid = false;
    
    @Size(max = 500)
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Schedule> schedules = new HashSet<>();
    
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        // Generate unique order number
        orderNumber = "ORD-" + System.currentTimeMillis() + "-" + id;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Order() {
    }
    
    public Order(User user, BigDecimal totalAmount) {
        this.user = user;
        this.totalAmount = totalAmount;
        this.finalAmount = totalAmount;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getOrderNumber() {
        return orderNumber;
    }
    
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        recalculateFinalAmount();
    }
    
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }
    
    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount != null ? discountAmount : BigDecimal.ZERO;
        recalculateFinalAmount();
    }
    
    public BigDecimal getFinalAmount() {
        return finalAmount;
    }
    
    public boolean isPaid() {
        return paid;
    }
    
    public void setPaid(boolean paid) {
        this.paid = paid;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public Set<Schedule> getSchedules() {
        return schedules;
    }
    
    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
    
    public Payment getPayment() {
        return payment;
    }
    
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    /**
     * Adds a schedule to this order.
     * 
     * @param schedule the schedule to add
     */
    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
        schedule.setOrder(this);
    }
    
    /**
     * Removes a schedule from this order.
     * 
     * @param schedule the schedule to remove
     */
    public void removeSchedule(Schedule schedule) {
        schedules.remove(schedule);
        schedule.setOrder(null);
    }
    
    /**
     * Recalculates the final amount based on total and discount.
     */
    private void recalculateFinalAmount() {
        this.finalAmount = totalAmount.subtract(discountAmount);
        if (this.finalAmount.compareTo(BigDecimal.ZERO) < 0) {
            this.finalAmount = BigDecimal.ZERO;
        }
    }
    
    /**
     * Apply a discount to the order.
     * 
     * @param discount the discount amount
     */
    public void applyDiscount(BigDecimal discount) {
        setDiscountAmount(discount);
    }
    
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", totalAmount=" + totalAmount +
                ", finalAmount=" + finalAmount +
                ", paid=" + paid +
                '}';
    }
}
