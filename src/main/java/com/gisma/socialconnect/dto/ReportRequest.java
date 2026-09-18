package com.gisma.socialconnect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReportRequest {

    @NotNull(message = "reporterId is required")
    private Integer reporterId;

    @NotNull(message = "reportedUserId is required")
    private Integer reportedUserId;

    @NotBlank(message = "reason is required")
    @Size(max = 255, message = "reason must be at most 255 characters")
    private String reason;

    public Integer getReporterId() {
        return reporterId;
    }

    public void setReporterId(Integer reporterId) {
        this.reporterId = reporterId;
    }

    public Integer getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(Integer reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
