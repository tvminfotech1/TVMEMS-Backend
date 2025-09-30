package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.LeaveRequest;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface LeaveRequestservice {

    ResponseEntity<ResponseStructure<LeaveRequest>> getLeaveRequest(UserDetails userDetails);

    ResponseEntity<ResponseStructure<LeaveRequest>> createLeaveRequest(LeaveRequest leaveRequest, UserDetails userDetails);

    ResponseEntity<ResponseStructure<LeaveRequest>> updateLeaveRequest(Long id, LeaveRequest leaveRequest, UserDetails userDetails);

    ResponseEntity<ResponseStructure<String>> deleteLeaveRequest(Long id, UserDetails userDetails);

    ResponseEntity<ResponseStructure<List<LeaveRequest>>> getAllLeaveRequests();
}