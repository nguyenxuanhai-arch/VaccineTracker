package com.vaccine.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vaccine.tracker.entity.Payment;
import com.vaccine.tracker.entity.Payment.PaymentStatus;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    List<Payment> findByParentId(Long parentId);
    
    List<Payment> findByChildId(Long childId);
    
    List<Payment> findByVaccinationId(Long vaccinationId);
    
    List<Payment> findByStatus(PaymentStatus status);
    
    List<Payment> findByTransactionId(String transactionId);
    
    @Query("SELECT p FROM Payment p WHERE p.parent.id = :parentId AND p.status = 'PENDING'")
    List<Payment> findUnpaidPaymentsByParentId(@Param("parentId") Long parentId);
}