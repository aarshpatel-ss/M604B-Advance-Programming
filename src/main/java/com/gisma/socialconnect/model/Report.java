package com.gisma.socialconnect.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "reporter_id", nullable = false)
    private Integer reporterId;

    @Column(name = "reported_user_id", nullable = false)
    private Integer reportedUserId;

    @Column(name = "reason", nullable = false, length = 255)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private ReportStatus status;

    @Column(name = "created_at")
    private LocalDate createdAt;

    protected Report() {
    }

    public Report(Integer reporterId, Integer reportedUserId, String reason) {
        this.reporterId = reporterId;
        this.reportedUserId = reportedUserId;
        this.reason = reason;
        this.status = ReportStatus.PENDING;
        this.createdAt = LocalDate.now();
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

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
