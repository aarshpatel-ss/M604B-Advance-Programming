package com.gisma.socialconnect.dto;

import com.gisma.socialconnect.model.UserActivityLog;

import java.time.LocalDateTime;

public class ActivityLogResponse {

    private final Long logId;
    private final Integer userId;
    private final String activityType;
    private final LocalDateTime activityDate;

    private ActivityLogResponse(Long logId, Integer userId, String activityType, LocalDateTime activityDate) {
        this.logId = logId;
        this.userId = userId;
        this.activityType = activityType;
        this.activityDate = activityDate;
    }

    public static ActivityLogResponse fromEntity(UserActivityLog log) {
        return new ActivityLogResponse(log.getLogId(), log.getUserId(), log.getActivityType(), log.getActivityDate());
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
