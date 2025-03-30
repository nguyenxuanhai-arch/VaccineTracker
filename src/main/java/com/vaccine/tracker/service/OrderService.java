package com.vaccine.tracker.service;

import com.vaccine.tracker.entity.Order;
import com.vaccine.tracker.entity.Schedule;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for managing orders.
 */
public interface OrderService {
    
    /**
     * Find order by ID.
     * 
     * @param id the order ID
     * @return the order
     */
    Order findById(Long id);
    
    /**
     * Find order by order number.
     * 
     * @param orderNumber the order number
     * @return the order
     */
    Order findByOrderNumber(String orderNumber);
    
    /**
     * Create a new order with schedules.
     * 
     * @param schedules the schedules to include in the order
     * @return the created order
     */
    Order createOrder(List<Schedule> schedules);
    
    /**
     * Create a new order with schedules for a specific user.
     * 
     * @param userId the user ID
     * @param schedules the schedules to include in the order
     * @return the created order
     */
    Order createOrderForUser(Long userId, List<Schedule> schedules);
    
    /**
     * Add a schedule to an order.
     * 
     * @param orderId the order ID
     * @param scheduleId the schedule ID
     * @return the updated order
     */
    Order addScheduleToOrder(Long orderId, Long scheduleId);
    
    /**
     * Remove a schedule from an order.
     * 
     * @param orderId the order ID
     * @param scheduleId the schedule ID
     * @return the updated order
     */
    Order removeScheduleFromOrder(Long orderId, Long scheduleId);
    
    /**
     * Apply a discount to an order.
     * 
     * @param orderId the order ID
     * @param discountAmount the discount amount
     * @return the updated order
     */
    Order applyDiscount(Long orderId, BigDecimal discountAmount);
    
    /**
     * Update the order notes.
     * 
     * @param orderId the order ID
     * @param notes the notes
     * @return the updated order
     */
    Order updateNotes(Long orderId, String notes);
    
    /**
     * Find orders by user ID.
     * 
     * @param userId the user ID
     * @return list of orders for the user
     */
    List<Order> findByUserId(Long userId);
    
    /**
     * Find orders by paid status.
     * 
     * @param paid the paid status
     * @return list of orders with the status
     */
    List<Order> findByPaid(boolean paid);
    
    /**
     * Find orders within a date range.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of orders in the date range
     */
    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find paid orders within a date range.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of paid orders in the date range
     */
    List<Order> findPaidOrdersInDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find recent orders for a user.
     * 
     * @param userId the user ID
     * @param limit the maximum number of orders to return
     * @return list of recent orders
     */
    List<Order> findRecentOrdersByUser(Long userId, int limit);
    
    /**
     * Calculate the order total.
     * 
     * @param orderId the order ID
     * @return the order total
     */
    BigDecimal calculateOrderTotal(Long orderId);
    
    /**
     * Check if the current user has permission to access an order.
     * 
     * @param orderId the order ID
     * @return true if user has permission
     */
    boolean hasPermission(Long orderId);
}
