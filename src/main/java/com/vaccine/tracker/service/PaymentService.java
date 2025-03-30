package com.vaccine.tracker.service;

import java.util.List;

import com.vaccine.tracker.entity.Payment;

public interface PaymentService {
    
    Payment createPayment(Payment payment);
    
    Payment getPaymentById(Long id);
    
    List<Payment> getAllPayments();
    
    List<Payment> getPaymentsByParentId(Long parentId);
    
    List<Payment> getPaymentsByChildId(Long childId);
    
    List<Payment> getPaymentsByVaccinationId(Long vaccinationId);
    
    Payment processPayment(Payment payment);
    
    Payment updatePayment(Long id, Payment paymentDetails);
    
    void deletePayment(Long id);
    
    List<Payment> getUnpaidPayments(Long parentId);
}