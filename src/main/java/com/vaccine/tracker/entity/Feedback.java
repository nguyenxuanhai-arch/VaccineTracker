package com.vaccine.tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Entity representing feedback from a user in the system.
 */
@Entity
@Table(name = "feedbacks")
public class Feedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer rating;
    
    @Size(max = 1000)
    @Column(columnDefinition = "TEXT")
    private String comment;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    
    @Column(name = "staff_response", columnDefinition = "TEXT")
    private String staffResponse;
    
    @Column(name = "responded_at")
    private LocalDateTime respondedAt;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Feedback() {
    }
    
    public Feedback(User user, Integer rating, String comment) {
        this.user = user;
        this.rating = rating;
        this.comment = comment;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Schedule getSchedule() {
        return schedule;
    }
    
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    
    public String getStaffResponse() {
        return staffResponse;
    }
    
    public void setStaffResponse(String staffResponse) {
        this.staffResponse = staffResponse;
        this.respondedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getRespondedAt() {
        return respondedAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    /**
     * Checks if the feedback has a staff response.
     * 
     * @return true if responded
     */
    public boolean hasResponse() {
        return staffResponse != null && !staffResponse.isEmpty();
    }
    
    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getId() : null) +
                ", rating=" + rating +
                ", hasResponse=" + hasResponse() +
                '}';
    }
}
