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

    @GetMapping("/my-leaves")
    public ResponseEntity<ResponseStructure<List<LeaveRequest>>> getMyLeaveRequests(
            @AuthenticationPrincipal UserDetails userDetails) {
        return leaveRequestService.getLeaveRequest(userDetails);
    }

    @PutMapping("/admin/{id}/status")
    public ResponseEntity<ResponseStructure<LeaveRequest>> updateLeaveStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return leaveRequestService.updateLeaveStatus(id, status);
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
}