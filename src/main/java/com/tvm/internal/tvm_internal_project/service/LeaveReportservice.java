package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.LeaveReport;

import java.util.List;
import java.util.Optional;

public interface LeaveReportservice {

    LeaveReport saveLeaveReport(LeaveReport leaveReport);

    Optional<LeaveReport> findLeaveReportById(Long id);

    List<LeaveReport> findAllLeaveReports();

    void deleteLeaveReport(Long id);
}
