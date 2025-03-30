package com.vaccine.tracker.repository;

import com.vaccine.tracker.entity.Child;
import com.vaccine.tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for managing Child entities.
 */
@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
    
    /**
     * Find children by parent user.
     * 
     * @param parent the parent user
     * @return list of children for the parent
     */
    List<Child> findByParent(User parent);
    
    /**
     * Find children by parent user id.
     * 
     * @param parentId the parent user id
     * @return list of children for the parent
     */
    List<Child> findByParentId(Long parentId);
    
    /**
     * Find children by name containing the given keyword.
     * 
     * @param keyword the keyword to search for
     * @return list of children with matching names
     */
    List<Child> findByFullNameContainingIgnoreCase(String keyword);
    
    /**
     * Find children born between the given dates.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of children born in the date range
     */
    List<Child> findByDateOfBirthBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Find children by parent and full name.
     * 
     * @param parent the parent user
     * @param fullName the full name
     * @return list of matching children
     */
    List<Child> findByParentAndFullNameIgnoreCase(User parent, String fullName);
    
    /**
     * Find children who need vaccination based on age.
     * 
     * @param maxAgeMonths the maximum age in months
     * @return list of children needing vaccination
     */
    @Query("SELECT c FROM Child c WHERE DATEDIFF(MONTH, c.dateOfBirth, CURRENT_DATE) <= :maxAgeMonths")
    List<Child> findChildrenNeedingVaccination(int maxAgeMonths);
}
