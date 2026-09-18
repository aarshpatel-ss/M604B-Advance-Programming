package com.gisma.socialconnect.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "User_Activity_Log")
public class UserActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "activity_type", length = 50)
    private String activityType;

    @Column(name = "activity_date")
    private LocalDateTime activityDate;

    protected UserActivityLog() {
    }

    public UserActivityLog(Integer userId, String activityType) {
        this.userId = userId;
        this.activityType = activityType;
        this.activityDate = LocalDateTime.now();
    }

    public Long getLogId() {
        return logId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getActivityType() {
        return activityType;
    }

    public LocalDateTime getActivityDate() {
        return activityDate;
    }
}
