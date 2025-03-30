package com.vaccine.tracker.service;

import com.vaccine.tracker.dto.request.PaymentRequest;
import com.vaccine.tracker.entity.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for managing payments.
 */
public interface PaymentService {
    
    /**
     * Find payment by ID.
     * 
     * @param id the payment ID
     * @return the payment
     */
    Payment findById(Long id);
    
    /**
     * Find payment by order ID.
     * 
     * @param orderId the order ID
     * @return the payment
     */
    Payment findByOrderId(Long orderId);
    
    /**
     * Process a payment.
     * 
     * @param paymentRequest the payment details
     * @return the processed payment
     */
    Payment processPayment(PaymentRequest paymentRequest);
    
    /**
     * Complete a payment.
     * 
     * @param id the payment ID
     * @param transactionReference the transaction reference
     * @return the completed payment
     */
    Payment completePayment(Long id, String transactionReference);
    
    /**
     * Mark a payment as failed.
     * 
     * @param id the payment ID
     * @param notes failure notes
     * @return the failed payment
     */
    Payment failPayment(Long id, String notes);
    
    /**
     * Refund a payment.
     * 
     * @param id the payment ID
     * @param notes refund notes
     * @return the refunded payment
     */
    Payment refundPayment(Long id, String notes);
    
    /**
     * Find all payments.
     * 
     * @return list of all payments
     */
    List<Payment> findAll();
    
    /**
     * Find payments by user ID.
     * 
     * @param userId the user ID
     * @return list of payments for the user
     */
    List<Payment> findByUserId(Long userId);
    
    /**
     * Find payments by payment method.
     * 
     * @param paymentMethod the payment method
     * @return list of payments using the method
     */
    List<Payment> findByPaymentMethod(String paymentMethod);
    
    /**
     * Find payments within a date range.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of payments in the date range
     */
    List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Calculate total revenue within a date range.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return the total revenue
     */
    BigDecimal calculateTotalRevenue(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find payment by transaction reference.
     * 
     * @param transactionReference the transaction reference
     * @return the payment
     */
    Payment findByTransactionReference(String transactionReference);
    
    /**
     * Check if the current user has permission to access a payment.
     * 
     * @param paymentId the payment ID
     * @return true if user has permission
     */
    boolean hasPermission(Long paymentId);
}
