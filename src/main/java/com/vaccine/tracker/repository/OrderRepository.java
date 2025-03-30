package com.vaccine.tracker.repository;

import com.vaccine.tracker.entity.Order;
import com.vaccine.tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Order entities.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    /**
     * Find orders by user.
     * 
     * @param user the user
     * @return list of orders for the user
     */
    List<Order> findByUser(User user);
    
    /**
     * Find orders by user id.
     * 
     * @param userId the user id
     * @return list of orders for the user
     */
    List<Order> findByUserId(Long userId);
    
    /**
     * Find order by order number.
     * 
     * @param orderNumber the order number
     * @return the order if found
     */
    Optional<Order> findByOrderNumber(String orderNumber);
    
    /**
     * Find orders by paid status.
     * 
     * @param paid the paid status
     * @return list of orders with the paid status
     */
    List<Order> findByPaid(boolean paid);
    
    /**
     * Find orders created within a date range.
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
    @Query("SELECT o FROM Order o WHERE o.paid = true AND o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findPaidOrdersInDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Count orders by user.
     * 
     * @param user the user
     * @return count of orders for the user
     */
    long countByUser(User user);
    
    /**
     * Find recent orders for a user.
     * 
     * @param userId the user id
     * @param limit the maximum number of orders to return
     * @return list of recent orders
     */
    @Query(value = "SELECT * FROM orders WHERE user_id = ?1 ORDER BY created_at DESC LIMIT ?2", nativeQuery = true)
    List<Order> findRecentOrdersByUser(Long userId, int limit);
}
