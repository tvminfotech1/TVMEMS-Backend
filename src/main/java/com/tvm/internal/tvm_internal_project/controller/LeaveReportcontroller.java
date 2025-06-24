package com.tvm.internal.tvm_internal_project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvm.internal.tvm_internal_project.model.LeaveReport;
import com.tvm.internal.tvm_internal_project.service.LeaveReportservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/leave-reports")
public class LeaveReportcontroller {
    @Autowired
    private LeaveReportservice leaveReportService;

    @GetMapping("/{id}")
    public ResponseEntity<LeaveReport> getLeaveReportById(@PathVariable Long id) {
        return leaveReportService.findLeaveReportById(id)
                .map(report -> ResponseEntity.ok(report))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/leaves", consumes = "multipart/form-data")
    public ResponseEntity<LeaveReport> createLeaveReport(
            @RequestParam(value = "file", required = false) MultipartFile image,
            @RequestParam("leaveReport") String leaveReportJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LeaveReport leaveReport = objectMapper.readValue(leaveReportJson, LeaveReport.class);

            if (image != null && !image.isEmpty()) {
                leaveReport.setProfilePicture(image.getBytes());
            }

            LeaveReport createdLeaveReport = leaveReportService.saveLeaveReport(leaveReport);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLeaveReport);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<LeaveReport>> getAllLeaveReports() {
        List<LeaveReport> leaveReports = leaveReportService.findAllLeaveReports();
        return ResponseEntity.ok(leaveReports);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeaveReport(@PathVariable Long id) {
        leaveReportService.deleteLeaveReport(id);
        return ResponseEntity.noContent().build();
    }
}
