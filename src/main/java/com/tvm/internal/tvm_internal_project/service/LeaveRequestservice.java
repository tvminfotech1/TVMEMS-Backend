package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.LeaveRequest;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface LeaveRequestservice {

    LeaveRequest createLeave(LeaveRequest leaveRequest, String email);

    ResponseEntity<ResponseStructure<List<LeaveRequest>>> getLeaveRequest(UserDetails userDetails);

    ResponseEntity<ResponseStructure<LeaveRequest>>  createLeaveRequest(LeaveRequest leaveRequest, UserDetails userDetails);

    ResponseEntity<ResponseStructure<LeaveRequest>> updateLeaveRequest(Long id, LeaveRequest leaveRequest, UserDetails userDetails);

    ResponseEntity<ResponseStructure<String>> deleteLeaveRequest(Long id, UserDetails userDetails);

    ResponseEntity<ResponseStructure<List<LeaveRequest>>> getAllLeaveRequests();

    ResponseEntity<ResponseStructure<LeaveRequest>> updateLeaveStatus(Long id, String status);
    // Add this method to the interface
    ResponseEntity<ResponseStructure<LeaveRequest>> applyLeaveForOtherUser(LeaveRequest leaveRequest, UserDetails adminDetails);


    List<LeaveRequest> getApprovedLeavesByUserId(Long userId);
}