package com.vaccine.tracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for payment processing requests.
 */
public class PaymentRequest {
    
    @NotNull(message = "Order ID is required")
    private Long orderId;
    
    @NotBlank(message = "Payment method cannot be blank")
    @Size(max = 100, message = "Payment method must be less than 100 characters")
    private String paymentMethod;
    
    @Size(max = 255, message = "Transaction reference must be less than 255 characters")
    private String transactionReference;
    
    @Size(max = 500, message = "Notes must be less than 500 characters")
    private String notes;
    
    // Additional payment information fields (card details, etc.)
    private String cardNumber;
    private String cardholderName;
    private String expiryDate;
    private String cvv;
    
    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public String getCardholderName() {
        return cardholderName;
    }
    
    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }
    
    public String getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public String getCvv() {
        return cvv;
    }
    
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
