package com.vaccine.tracker.service.impl;

import com.vaccine.tracker.dto.request.PaymentRequest;
import com.vaccine.tracker.entity.Order;
import com.vaccine.tracker.entity.Payment;
import com.vaccine.tracker.entity.User;
import com.vaccine.tracker.enums.PaymentStatus;
import com.vaccine.tracker.enums.Role;
import com.vaccine.tracker.exception.BadRequestException;
import com.vaccine.tracker.exception.ResourceNotFoundException;
import com.vaccine.tracker.exception.UnauthorizedException;
import com.vaccine.tracker.mapper.PaymentMapper;
import com.vaccine.tracker.repository.PaymentRepository;
import com.vaccine.tracker.service.OrderService;
import com.vaccine.tracker.service.PaymentService;
import com.vaccine.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of PaymentService interface.
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PaymentMapper paymentMapper;
    
    @Override
    public Payment findById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        
        // Check permission
        if (!hasPermission(id)) {
            throw new UnauthorizedException("You don't have permission to access this payment");
        }
        
        return payment;
    }
    
    @Override
    public Payment findByOrderId(Long orderId) {
        // Check if user has permission to access the order
        if (!orderService.hasPermission(orderId)) {
            throw new UnauthorizedException("You don't have permission to access this order's payment");
        }
        
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for order id: " + orderId));
    }
    
    @Override
    @Transactional
    public Payment processPayment(PaymentRequest paymentRequest) {
        // Validate the request
        validatePaymentRequest(paymentRequest);
        
        // Get the order
        Order order = orderService.findById(paymentRequest.getOrderId());
        
        // Check if user has permission to make payment for this order
        if (!orderService.hasPermission(order.getId())) {
            throw new UnauthorizedException("You don't have permission to process payment for this order");
        }
        
        // Check if order is already paid
        if (order.isPaid()) {
            throw new BadRequestException("This order has already been paid");
        }
        
        // Check if a payment already exists for this order
        Optional<Payment> existingPayment = paymentRepository.findByOrderId(order.getId());
        if (existingPayment.isPresent()) {
            // If payment exists and is pending, update it
            if (existingPayment.get().getStatus() == PaymentStatus.PENDING) {
                Payment payment = existingPayment.get();
                paymentMapper.updatePaymentFromRequest(payment, paymentRequest);
                return processPaymentLogic(payment);
            } else {
                throw new BadRequestException("A payment already exists for this order with status: " + 
                                            existingPayment.get().getStatus());
            }
        }
        
        // Create new payment
        Payment payment = paymentMapper.toPayment(paymentRequest, order);
        
        // Process the payment
        return processPaymentLogic(payment);
    }
    
    @Override
    @Transactional
    public Payment completePayment(Long id, String transactionReference) {
        Payment payment = findById(id);
        
        // Only admin, staff, or the order owner can complete a payment
        User currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN && 
            currentUser.getRole() != Role.ROLE_STAFF && 
            !payment.getOrder().getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You don't have permission to complete this payment");
        }
        
        // Check if payment can be completed
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new BadRequestException("Cannot complete payment with status: " + payment.getStatus());
        }
        
        // Complete the payment
        payment.complete(transactionReference);
        
        // Mark the order as paid
        payment.getOrder().setPaid(true);
        
        return paymentRepository.save(payment);
    }
    
    @Override
    @Transactional
    public Payment failPayment(Long id, String notes) {
        Payment payment = findById(id);
        
        // Only admin, staff, or the order owner can fail a payment
        User currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN && 
            currentUser.getRole() != Role.ROLE_STAFF && 
            !payment.getOrder().getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You don't have permission to fail this payment");
        }
        
        // Check if payment can be failed
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new BadRequestException("Cannot fail payment with status: " + payment.getStatus());
        }
        
        // Fail the payment
        payment.fail(notes);
        
        return paymentRepository.save(payment);
    }
    
    @Override
    @Transactional
    public Payment refundPayment(Long id, String notes) {
        Payment payment = findById(id);
        
        // Only admin can refund payments
        User currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedException("Only administrators can refund payments");
        }
        
        // Check if payment can be refunded
        if (payment.getStatus() != PaymentStatus.COMPLETED) {
            throw new BadRequestException("Cannot refund payment with status: " + payment.getStatus());
        }
        
        // Refund the payment
        payment.refund(notes);
        
        // Mark the order as unpaid
        payment.getOrder().setPaid(false);
        
        return paymentRepository.save(payment);
    }
    
    @Override
    public List<Payment> findAll() {
        User currentUser = userService.getCurrentUser();
        
        // Only admin can see all payments
        if (currentUser.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedException("Only administrators can access all payments");
        }
        
        return paymentRepository.findAll();
    }
    
    @Override
    public List<Payment> findByUserId(Long userId) {
        User currentUser = userService.getCurrentUser();
        
        // Users can only see their own payments, but admin/staff can see any user's payments
        if (currentUser.getRole() == Role.ROLE_ADMIN || 
            currentUser.getRole() == Role.ROLE_STAFF || 
            currentUser.getId().equals(userId)) {
            return paymentRepository.findByUserId(userId);
        } else {
            throw new UnauthorizedException("You don't have permission to access this user's payments");
        }
    }
    
    @Override
    public List<Payment> findByPaymentMethod(String paymentMethod) {
        User currentUser = userService.getCurrentUser();
        
        // Only admin/staff can search payments by method
        if (currentUser.getRole() != Role.ROLE_ADMIN && currentUser.getRole() != Role.ROLE_STAFF) {
            throw new UnauthorizedException("Only staff can search payments by method");
        }
        
        return paymentRepository.findByPaymentMethod(paymentMethod);
    }
    
    @Override
    public List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        User currentUser = userService.getCurrentUser();
        
        // Only admin/staff can search payments by date range
        if (currentUser.getRole() != Role.ROLE_ADMIN && currentUser.getRole() != Role.ROLE_STAFF) {
            throw new UnauthorizedException("Only staff can search payments by date range");
        }
        
        return paymentRepository.findByPaymentDateBetween(startDate, endDate);
    }
    
    @Override
    public BigDecimal calculateTotalRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        User currentUser = userService.getCurrentUser();
        
        // Only admin/staff can calculate revenue
        if (currentUser.getRole() != Role.ROLE_ADMIN && currentUser.getRole() != Role.ROLE_STAFF) {
            throw new UnauthorizedException("Only staff can calculate revenue");
        }
        
        BigDecimal revenue = paymentRepository.calculateTotalRevenue(startDate, endDate);
        return revenue != null ? revenue : BigDecimal.ZERO;
    }
    
    @Override
    public Payment findByTransactionReference(String transactionReference) {
        return paymentRepository.findByTransactionReference(transactionReference)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with transaction reference: " + 
                                                              transactionReference));
    }
    
    @Override
    public boolean hasPermission(Long paymentId) {
        User currentUser = userService.getCurrentUser();
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + paymentId));
        
        // Admin and staff can access any payment
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return true;
        }
        
        // Users can only access their own payments
        return payment.getOrder().getUser().getId().equals(currentUser.getId());
    }
    
    /**
     * Process the payment logic.
     * This would typically interact with a payment gateway, but for simplicity
     * we'll simulate processing here.
     * 
     * @param payment the payment to process
     * @return the processed payment
     */
    private Payment processPaymentLogic(Payment payment) {
        // Save the payment initially with PENDING status
        Payment savedPayment = paymentRepository.save(payment);
        
        // Simulate payment processing logic
        // In a real implementation, this would integrate with a payment gateway
        
        // For demo purposes, we'll simulate successful payment
        // In a real app, this would be based on the payment gateway response
        savedPayment.complete("SIMULATED-TRANS-" + System.currentTimeMillis());
        
        // Mark the order as paid
        savedPayment.getOrder().setPaid(true);
        
        return paymentRepository.save(savedPayment);
    }
    
    /**
     * Validate a payment request.
     * 
     * @param paymentRequest the payment request to validate
     */
    private void validatePaymentRequest(PaymentRequest paymentRequest) {
        if (paymentRequest.getOrderId() == null) {
            throw new BadRequestException("Order ID is required");
        }
        
        if (paymentRequest.getPaymentMethod() == null || paymentRequest.getPaymentMethod().trim().isEmpty()) {
            throw new BadRequestException("Payment method is required");
        }
        
        // Additional validation could be added for specific payment methods
        // For example, if credit card, validate card number, expiry, etc.
    }
}
