package com.vaccine.tracker.repository;

import com.vaccine.tracker.entity.User;
import com.vaccine.tracker.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find a user by username.
     * 
     * @param username the username to search for
     * @return the user if found
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find a user by email.
     * 
     * @param email the email to search for
     * @return the user if found
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check if a username exists.
     * 
     * @param username the username to check
     * @return true if username exists
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if an email exists.
     * 
     * @param email the email to check
     * @return true if email exists
     */
    boolean existsByEmail(String email);
    
    /**
     * Find all users with a given role.
     * 
     * @param role the role to filter by
     * @return list of users with the specified role
     */
    List<User> findByRole(Role role);
    
    /**
     * Find users by name containing the given keyword.
     * 
     * @param keyword the keyword to search for
     * @return list of users with matching names
     */
    List<User> findByFullNameContainingIgnoreCase(String keyword);
}
