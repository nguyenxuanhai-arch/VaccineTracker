package com.vaccine.tracker.dto.response;

import com.vaccine.tracker.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DTO for payment data responses.
 */
public class PaymentResponse {
    
    private Long id;
    private Long orderId;
    private String orderNumber;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private PaymentStatus status;
    private String paymentMethod;
    private String transactionReference;
    private String notes;
    
    // Constructors
    public PaymentResponse() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public String getOrderNumber() {
        return orderNumber;
    }
    
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public PaymentStatus getStatus() {
        return status;
    }
    
    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getTransactionReference() {
        return transactionReference;
    }
    
    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    /**
     * Get formatted payment date.
     * 
     * @return formatted date
     */
    public String getFormattedPaymentDate() {
        if (paymentDate == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return paymentDate.format(formatter);
    }
    
    /**
     * Get status display name.
     * 
     * @return status display name
     */
    public String getStatusDisplayName() {
        return status != null ? status.getDisplayName() : "";
    }
    
    /**
     * Format amount with currency.
     * 
     * @return formatted amount
     */
    public String getFormattedAmount() {
        return amount != null ? "$" + amount.toString() : "";
    }
    
    /**
     * Check if payment is successful.
     * 
     * @return true if successful
     */
    public boolean isSuccessful() {
        return status != null && status.isSuccessful();
    }
    
    /**
     * Check if payment is pending.
     * 
     * @return true if pending
     */
    public boolean isPending() {
        return status == PaymentStatus.PENDING;
    }
}
