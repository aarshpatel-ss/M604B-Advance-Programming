package com.gisma.socialconnect.controller;

import com.gisma.socialconnect.dto.ReportRequest;
import com.gisma.socialconnect.dto.ReportResponse;
import com.gisma.socialconnect.dto.ReportStatusUpdateRequest;
import com.gisma.socialconnect.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@Tag(name = "Reports", description = "Moderation reports raised against a user account")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<ReportResponse> getAll() {
        return reportService.getAll();
    }

    @GetMapping("/{id}")
    public ReportResponse getById(@PathVariable Long id) {
        return reportService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReportResponse create(@Valid @RequestBody ReportRequest request) {
        return reportService.create(request.getReporterId(), request.getReportedUserId(), request.getReason());
    }

    @PutMapping("/{id}/status")
    public ReportResponse updateStatus(@PathVariable Long id, @Valid @RequestBody ReportStatusUpdateRequest request) {
        return reportService.updateStatus(id, request.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
