package com.vaccine.tracker.controller;

import com.vaccine.tracker.dto.request.PaymentRequest;
import com.vaccine.tracker.dto.response.MessageResponse;
import com.vaccine.tracker.dto.response.PaymentResponse;
import com.vaccine.tracker.entity.Order;
import com.vaccine.tracker.entity.Payment;
import com.vaccine.tracker.mapper.PaymentMapper;
import com.vaccine.tracker.service.OrderService;
import com.vaccine.tracker.service.PaymentService;
import com.vaccine.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for handling payment operations.
 */
@Controller
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentMapper paymentMapper;

    /**
     * Show the checkout page for an order.
     */
    @GetMapping("/checkout/{orderId}")
    public String showCheckoutPage(@PathVariable Long orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        model.addAttribute("paymentRequest", new PaymentRequest());
        return "payment/checkout";
    }

    /**
     * Process a payment for an order.
     *
     * @param orderId the order ID
     * @param paymentRequest the payment details
     * @return payment response
     */
    @PostMapping("/{orderId}")
    @ResponseBody
    public ResponseEntity<PaymentResponse> processPayment(
            @PathVariable Long orderId,
            @Valid @RequestBody PaymentRequest paymentRequest) {
        
        // Set the order ID in the payment request
        paymentRequest.setOrderId(orderId);
        
        // Process the payment
        Payment payment = paymentService.processPayment(paymentRequest);
        
        return ResponseEntity.ok(paymentMapper.toPaymentResponse(payment));
    }

    /**
     * Get a payment by ID.
     *
     * @param id the payment ID
     * @return the payment details
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long id) {
        Payment payment = paymentService.findById(id);
        return ResponseEntity.ok(paymentMapper.toPaymentResponse(payment));
    }

    /**
     * Get payment for an order.
     *
     * @param orderId the order ID
     * @return the payment details
     */
    @GetMapping("/order/{orderId}")
    @ResponseBody
    public ResponseEntity<PaymentResponse> getPaymentByOrder(@PathVariable Long orderId) {
        Payment payment = paymentService.findByOrderId(orderId);
        return ResponseEntity.ok(paymentMapper.toPaymentResponse(payment));
    }

    /**
     * Get all payments for the current user.
     *
     * @return list of payments
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<PaymentResponse>> getUserPayments() {
        Long userId = userService.getCurrentUser().getId();
        List<Payment> payments = paymentService.findByUserId(userId);
        return ResponseEntity.ok(paymentMapper.toPaymentResponseList(payments));
    }

    /**
     * Get all payments (admin only).
     *
     * @return list of all payments
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        List<Payment> payments = paymentService.findAll();
        return ResponseEntity.ok(paymentMapper.toPaymentResponseList(payments));
    }

    /**
     * Refund a payment (admin only).
     *
     * @param id the payment ID
     * @param notes refund notes
     * @return the updated payment
     */
    @PostMapping("/{id}/refund")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<PaymentResponse> refundPayment(
            @PathVariable Long id,
            @RequestParam String notes) {
        
        Payment payment = paymentService.refundPayment(id, notes);
        return ResponseEntity.ok(paymentMapper.toPaymentResponse(payment));
    }

    /**
     * Manual payment verification (admin only).
     *
     * @param id the payment ID
     * @param verified verification status
     * @param notes verification notes
     * @return the updated payment
     */
    @PostMapping("/{id}/verify")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<PaymentResponse> verifyPayment(
            @PathVariable Long id,
            @RequestParam boolean verified,
            @RequestParam(required = false) String notes) {
        
        Payment payment;
        if (verified) {
            payment = paymentService.completePayment(id, "Manual verification: " + notes);
        } else {
            payment = paymentService.failPayment(id, "Manual verification failed: " + notes);
        }
        
        return ResponseEntity.ok(paymentMapper.toPaymentResponse(payment));
    }

    /**
     * Get payments by date range (admin only).
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of payments in the date range
     */
    @GetMapping("/by-date-range")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<List<PaymentResponse>> getPaymentsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        
        List<Payment> payments = paymentService.findByPaymentDateBetween(startDate, endDate);
        return ResponseEntity.ok(paymentMapper.toPaymentResponseList(payments));
    }

    /**
     * Webhook for payment provider callbacks.
     * This would be customized based on the payment provider being used.
     */
    @PostMapping("/webhook")
    @ResponseBody
    public ResponseEntity<MessageResponse> paymentWebhook(@RequestBody String payload) {
        // Process webhook payload and update payment status
        // This is a placeholder - actual implementation would depend on the payment provider
        
        return ResponseEntity.ok(new MessageResponse("Webhook received"));
    }
}
