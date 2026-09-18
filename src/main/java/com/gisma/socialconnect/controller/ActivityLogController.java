package com.gisma.socialconnect.controller;

import com.gisma.socialconnect.dto.ActivityLogResponse;
import com.gisma.socialconnect.service.ActivityLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activity-log")
@Tag(name = "Activity Log", description = "Read-only, system-generated audit trail of user activity")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @GetMapping
    public List<ActivityLogResponse> getAll() {
        return activityLogService.getAll();
    }

    @GetMapping("/user/{userId}")
    public List<ActivityLogResponse> getByUser(@PathVariable Integer userId) {
        return activityLogService.getByUserId(userId);
    }
}
