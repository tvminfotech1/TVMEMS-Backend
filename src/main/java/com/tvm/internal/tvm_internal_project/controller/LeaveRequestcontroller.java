package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.LeaveRequest;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.LeaveRequestservice;
import com.tvm.internal.tvm_internal_project.model.LeaveType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestcontroller {

    @Autowired
    private LeaveRequestservice leaveRequestService;

    @GetMapping("/leaves")
    public ResponseEntity<ResponseStructure<List<LeaveRequest>>> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests(); // Implement this method in the service
    }


    @GetMapping("/leavetype")
    public List<String> getLeaveTypes() {
        return Arrays.stream(LeaveType.values())
                .map(LeaveType::getDescription)
                .toList();
    }

    @PostMapping("/leaves")
    public ResponseEntity<ResponseStructure<LeaveRequest>> createLeaveRequest(@RequestBody LeaveRequest leaveRequest, @AuthenticationPrincipal UserDetails userDetails) {

        return leaveRequestService.createLeaveRequest(leaveRequest, userDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<LeaveRequest>> updateLeaveRequest(@PathVariable Long id, @RequestBody LeaveRequest leaveRequest, @AuthenticationPrincipal UserDetails userDetails) {

        return leaveRequestService.updateLeaveRequest(id, leaveRequest, userDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteLeaveRequest(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {

        return leaveRequestService.deleteLeaveRequest(id, userDetails);
    }
}
