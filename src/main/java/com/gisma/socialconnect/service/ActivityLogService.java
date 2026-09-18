package com.gisma.socialconnect.service;

import com.gisma.socialconnect.dto.ActivityLogResponse;
import com.gisma.socialconnect.model.UserActivityLog;
import com.gisma.socialconnect.repository.UserActivityLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityLogService {

    private final UserActivityLogRepository activityLogRepository;

    public ActivityLogService(UserActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    public void record(Integer userId, String activityType) {
        activityLogRepository.save(new UserActivityLog(userId, activityType));
    }

    public List<ActivityLogResponse> getAll() {
        return activityLogRepository.findAll().stream()
                .map(ActivityLogResponse::fromEntity)
                .toList();
    }

    public List<ActivityLogResponse> getByUserId(Integer userId) {
        return activityLogRepository.findByUserIdOrderByActivityDateDesc(userId).stream()
                .map(ActivityLogResponse::fromEntity)
                .toList();
    }
}
