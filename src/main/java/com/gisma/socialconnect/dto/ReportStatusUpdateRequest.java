package com.gisma.socialconnect.dto;

import com.gisma.socialconnect.model.ReportStatus;
import jakarta.validation.constraints.NotNull;

public class ReportStatusUpdateRequest {

    @NotNull(message = "status is required")
    private ReportStatus status;

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }
}
