package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.LeaveRequest;
import com.tvm.internal.tvm_internal_project.model.LeaveType;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.LeaveRequestservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestcontroller {

    @Autowired
    private LeaveRequestservice leaveRequestService;


    @GetMapping("/leaves")
    public ResponseEntity<ResponseStructure<List<LeaveRequest>>> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests();
    }

    @PutMapping("/admin/{id}/status")
    public ResponseEntity<ResponseStructure<LeaveRequest>> updateLeaveStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return leaveRequestService.updateLeaveStatus(id, status);
    }

    @GetMapping("/leavetype")
    public List<String> getLeaveTypes() {
        return Arrays.stream(LeaveType.values())
                .map(LeaveType::getDescription)
                .toList();
    }

    @PostMapping("/leaves")
    public ResponseEntity<ResponseStructure<LeaveRequest>> createLeaveRequest(
            @RequestBody LeaveRequest leaveRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        return leaveRequestService.createLeaveRequest(leaveRequest, userDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<LeaveRequest>> updateLeaveRequest(
            @PathVariable Long id,
            @RequestBody LeaveRequest leaveRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        return leaveRequestService.updateLeaveRequest(id, leaveRequest, userDetails);
    }


    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ResponseStructure<List<LeaveRequest>>> getLeavesByEmployeeId(
            @PathVariable Long employeeId) {
        return leaveRequestService.getLeavesByEmployeeId(employeeId);
    }

    @GetMapping("/approved/{userId}")
    public ResponseEntity<List<LeaveRequest>> getApprovedLeaves(@PathVariable Long userId) {
        List<LeaveRequest> approvedLeaves = leaveRequestService.getApprovedLeavesByUserId(userId);
        if (approvedLeaves.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(approvedLeaves);
    }

    @GetMapping("/check-leave-status/{empId}")
    public ResponseEntity<ResponseStructure<Boolean>> isEmployeeOnLeave(
            @PathVariable Long empId,
            @RequestParam String date,@AuthenticationPrincipal UserDetails userDetails) {
        LocalDate parsedDate = LocalDate.parse(date); // Convert string to LocalDate
        return leaveRequestService.isOnApprovedLeave(empId, parsedDate);
    }
}