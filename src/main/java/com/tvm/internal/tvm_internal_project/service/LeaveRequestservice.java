package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.LeaveRequest;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestservice {

    LeaveRequest createLeave(LeaveRequest leaveRequest, String email);

    ResponseEntity<ResponseStructure<LeaveRequest>>  createLeaveRequest(LeaveRequest leaveRequest, UserDetails userDetails);

    ResponseEntity<ResponseStructure<LeaveRequest>> updateLeaveRequest(Long id, LeaveRequest leaveRequest, UserDetails userDetails);

    ResponseEntity<ResponseStructure<List<LeaveRequest>>> getAllLeaveRequests();

    ResponseEntity<ResponseStructure<LeaveRequest>> updateLeaveStatus(Long id, String status);

    ResponseEntity<ResponseStructure<List<LeaveRequest>>> getLeavesByEmployeeId(Long employeeId);

    List<LeaveRequest> getApprovedLeavesByUserId(Long userId);

    ResponseEntity<ResponseStructure<Boolean>> isOnApprovedLeave(Long empId, LocalDate date);
}