package com.vaccine.tracker.repository;

import com.vaccine.tracker.entity.Order;
import com.vaccine.tracker.entity.Payment;
import com.vaccine.tracker.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Payment entities.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    /**
     * Find payment by order.
     * 
     * @param order the order
     * @return the payment if found
     */
    Optional<Payment> findByOrder(Order order);
    
    /**
     * Find payment by order id.
     * 
     * @param orderId the order id
     * @return the payment if found
     */
    Optional<Payment> findByOrderId(Long orderId);
    
    /**
     * Find payments by status.
     * 
     * @param status the payment status
     * @return list of payments with the status
     */
    List<Payment> findByStatus(PaymentStatus status);
    
    /**
     * Find payments by payment method.
     * 
     * @param paymentMethod the payment method
     * @return list of payments using the method
     */
    List<Payment> findByPaymentMethod(String paymentMethod);
    
    /**
     * Find payments made within a date range.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of payments in the date range
     */
    List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find payments for a specific user.
     * 
     * @param userId the user id
     * @return list of payments for the user
     */
    @Query("SELECT p FROM Payment p JOIN p.order o WHERE o.user.id = :userId")
    List<Payment> findByUserId(Long userId);
    
    /**
     * Calculate total revenue within a date range.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return the total revenue
     */
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'COMPLETED' AND p.paymentDate BETWEEN :startDate AND :endDate")
    BigDecimal calculateTotalRevenue(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find payment by transaction reference.
     * 
     * @param transactionReference the transaction reference
     * @return the payment if found
     */
    Optional<Payment> findByTransactionReference(String transactionReference);
}
