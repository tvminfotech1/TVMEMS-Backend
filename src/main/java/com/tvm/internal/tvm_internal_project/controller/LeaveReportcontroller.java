package com.tvm.internal.tvm_internal_project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvm.internal.tvm_internal_project.model.LeaveReport;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.LeaveReportservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/leave-reports")
public class LeaveReportcontroller {

        @Autowired
        private LeaveReportservice leaveReportService;

        @GetMapping("/leavereport")
        public ResponseEntity<ResponseStructure<List<LeaveReport>>> getLeaveReportById(@AuthenticationPrincipal UserDetails userDetails) {
            return leaveReportService.findLeaveReportById(userDetails);
        }

    @PostMapping(value = "/leaves", consumes = "multipart/form-data")
    public ResponseEntity<ResponseStructure<LeaveReport>> saveLeaveReport(@RequestParam(value = "file", required = false) MultipartFile image, @RequestParam("leaveReport") String leaveReportJson, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LeaveReport leaveReport = objectMapper.readValue(leaveReportJson, LeaveReport.class);

            if (image != null && !image.isEmpty()) {
                leaveReport.setProfilePicture(image.getBytes());
            }
            return leaveReportService.saveLeaveReport(leaveReport, userDetails);
            // LeaveReport savedReport = leaveReportService.saveLeaveReport(leaveReport,userDetails);
//            return ResponseEntity.status(HttpStatus.CREATED).body(savedReport);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        }

//    @GetMapping
//    public ResponseEntity<List<LeaveReport>> getAllLeaveReports() {
//        List<LeaveReport> leaveReports = leaveReportService.findAllLeaveReports();
//        return ResponseEntity.ok(leaveReports);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteLeaveReport(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {

        return leaveReportService.deleteLeaveReport(id, userDetails);
    }
}
