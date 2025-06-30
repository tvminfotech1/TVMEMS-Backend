package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.LeaveReport;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface LeaveReportservice {
    ResponseEntity<ResponseStructure<LeaveReport>> saveLeaveReport(LeaveReport leaveReport, UserDetails userDetails);

    ResponseEntity<ResponseStructure<List<LeaveReport>>> findLeaveReportById(UserDetails userDetails);



    ResponseEntity<ResponseStructure<String>> deleteLeaveReport(Long id,UserDetails userDetails);

}
