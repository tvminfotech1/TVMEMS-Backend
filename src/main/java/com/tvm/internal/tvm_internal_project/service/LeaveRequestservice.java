package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.LeaveRequest;

import java.util.List;
import java.util.Optional;

public interface LeaveRequestservice {

    List<LeaveRequest> getAllLeaveRequests();

    Optional<LeaveRequest> getLeaveRequestById(Long id);

    LeaveRequest createLeaveRequest(LeaveRequest leaveRequest);

    Optional<LeaveRequest> updateLeaveRequest(Long id, LeaveRequest leaveRequest);

    boolean deleteLeaveRequest(Long id);

}
