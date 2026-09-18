package com.gisma.socialconnect.service;

import com.gisma.socialconnect.dto.ReportResponse;
import com.gisma.socialconnect.exception.InvalidOperationException;
import com.gisma.socialconnect.exception.ResourceNotFoundException;
import com.gisma.socialconnect.model.Report;
import com.gisma.socialconnect.model.ReportStatus;
import com.gisma.socialconnect.notification.NotificationDispatcher;
import com.gisma.socialconnect.notification.ReportNotification;
import com.gisma.socialconnect.repository.ReportRepository;
import com.gisma.socialconnect.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;
    private final NotificationDispatcher notificationDispatcher;

    public ReportService(ReportRepository reportRepository, UserRepository userRepository,
                          ActivityLogService activityLogService, NotificationDispatcher notificationDispatcher) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.activityLogService = activityLogService;
        this.notificationDispatcher = notificationDispatcher;
    }

    @Transactional(readOnly = true)
    public List<ReportResponse> getAll() {
        return reportRepository.findAll().stream().map(ReportResponse::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public ReportResponse getById(Long id) {
        return ReportResponse.fromEntity(findEntityOrThrow(id));
    }

    @Transactional
    public ReportResponse create(Integer reporterId, Integer reportedUserId, String reason) {
        if (reporterId.equals(reportedUserId)) {
            throw new InvalidOperationException("A user cannot report themselves");
        }
        if (!userRepository.existsById(reporterId)) {
            throw new ResourceNotFoundException("User not found with id: " + reporterId);
        }
        if (!userRepository.existsById(reportedUserId)) {
            throw new ResourceNotFoundException("User not found with id: " + reportedUserId);
        }

        Report saved = reportRepository.save(new Report(reporterId, reportedUserId, reason));
        activityLogService.record(reporterId, "REPORT_SUBMITTED");
        return ReportResponse.fromEntity(saved);
    }

    @Transactional
    public ReportResponse updateStatus(Long id, ReportStatus newStatus) {
        Report report = findEntityOrThrow(id);
        report.setStatus(newStatus);
        Report saved = reportRepository.save(report);
        activityLogService.record(report.getReportedUserId(), "REPORT_STATUS_" + newStatus);
        notificationDispatcher.dispatch(new ReportNotification(report.getReportedUserId(), id, newStatus.name()));
        return ReportResponse.fromEntity(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!reportRepository.existsById(id)) {
            throw new ResourceNotFoundException("Report not found with id: " + id);
        }
        reportRepository.deleteById(id);
    }

    private Report findEntityOrThrow(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with id: " + id));
    }
}
