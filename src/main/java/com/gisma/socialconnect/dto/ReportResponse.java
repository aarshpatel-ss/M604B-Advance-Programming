package com.gisma.socialconnect.dto;

import com.gisma.socialconnect.model.Report;
import com.gisma.socialconnect.model.ReportStatus;

import java.time.LocalDate;

public class ReportResponse {

    private final Long reportId;
    private final Integer reporterId;
    private final Integer reportedUserId;
    private final String reason;
    private final ReportStatus status;
    private final LocalDate createdAt;

    private ReportResponse(Long reportId, Integer reporterId, Integer reportedUserId, String reason,
                            ReportStatus status, LocalDate createdAt) {
        this.reportId = reportId;
        this.reporterId = reporterId;
        this.reportedUserId = reportedUserId;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static ReportResponse fromEntity(Report report) {
        return new ReportResponse(report.getReportId(), report.getReporterId(), report.getReportedUserId(),
                report.getReason(), report.getStatus(), report.getCreatedAt());
    }

    public Long getReportId() {
        return reportId;
    }

    public Integer getReporterId() {
        return reporterId;
    }

    public Integer getReportedUserId() {
        return reportedUserId;
    }

    public String getReason() {
        return reason;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
