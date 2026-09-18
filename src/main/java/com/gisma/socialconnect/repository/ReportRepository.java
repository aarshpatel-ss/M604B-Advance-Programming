package com.gisma.socialconnect.repository;

import com.gisma.socialconnect.model.Report;
import com.gisma.socialconnect.model.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByStatus(ReportStatus status);
    List<Report> findByReportedUserId(Integer reportedUserId);
}
