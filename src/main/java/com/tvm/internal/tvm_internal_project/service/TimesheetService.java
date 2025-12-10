package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.DTO.TimesheetDTO;
import com.tvm.internal.tvm_internal_project.model.Timesheet;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

public interface TimesheetService {

    ResponseEntity<ResponseStructure<List<Timesheet>>> getAllTimesheets(UserDetails userDetails);

    ResponseEntity<ResponseStructure<Timesheet>> createTimesheet(Timesheet timesheet, UserDetails userDetails);

    ResponseEntity<ResponseStructure<List<TimesheetDTO>>> getAllTimesheetsForAdmin();

    ResponseEntity<ResponseStructure<Timesheet>> updateTimesheetStatus(Long id, String status);

    ResponseEntity<ResponseStructure<List<Timesheet>>> getTimesheetsByUserId(Long userId);

}
