package com.vaccine.tracker.mapper;

import com.vaccine.tracker.dto.request.PaymentRequest;
import com.vaccine.tracker.dto.response.PaymentResponse;
import com.vaccine.tracker.entity.Order;
import com.vaccine.tracker.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for Payment entity and DTOs.
 */
@Component
public class PaymentMapper {
    
    /**
     * Converts Payment entity to PaymentResponse DTO.
     * 
     * @param payment the payment entity
     * @return the payment response
     */
    public PaymentResponse toPaymentResponse(Payment payment) {
        if (payment == null) {
            return null;
        }
        
        PaymentResponse response = new PaymentResponse();
        response.setId(payment.getId());
        response.setAmount(payment.getAmount());
        response.setPaymentDate(payment.getPaymentDate());
        response.setStatus(payment.getStatus());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setTransactionReference(payment.getTransactionReference());
        response.setNotes(payment.getNotes());
        
        // Set order info
        if (payment.getOrder() != null) {
            response.setOrderId(payment.getOrder().getId());
            response.setOrderNumber(payment.getOrder().getOrderNumber());
        }
        
        return response;
    }
    
    /**
     * Converts list of Payment entities to list of PaymentResponse DTOs.
     * 
     * @param payments the list of payment entities
     * @return the list of payment responses
     */
    public List<PaymentResponse> toPaymentResponseList(List<Payment> payments) {
        if (payments == null) {
            return null;
        }
        
        return payments.stream()
                .map(this::toPaymentResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Creates a new Payment entity from PaymentRequest DTO.
     * 
     * @param request the payment request
     * @param order the order entity
     * @return the payment entity
     */
    public Payment toPayment(PaymentRequest request, Order order) {
        if (request == null) {
            return null;
        }
        
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getFinalAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionReference(request.getTransactionReference());
        payment.setNotes(request.getNotes());
        
        return payment;
    }
    
    /**
     * Updates Payment entity from PaymentRequest DTO.
     * 
     * @param payment the payment entity to update
     * @param request the payment request with updated data
     */
    public void updatePaymentFromRequest(Payment payment, PaymentRequest request) {
        if (payment == null || request == null) {
            return;
        }
        
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionReference(request.getTransactionReference());
        payment.setNotes(request.getNotes());
    }
}
