package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.ChartData;
import com.tvm.internal.tvm_internal_project.model.Timesheet;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TimesheetService {


    ResponseEntity<ResponseStructure<List<Timesheet>>> getAllTimesheets();

    ResponseEntity<ResponseStructure<Timesheet>> getTimesheetById(Long id);

    ResponseEntity<ResponseStructure<Timesheet>> createTimesheet(Timesheet timesheet);

    ResponseEntity<ResponseStructure<Timesheet>> updateTimesheet(Long id, Timesheet timesheetDetails);

    ResponseEntity<ResponseStructure<String>> deleteTimesheet(Long id);

    ResponseEntity<ChartData> getWorkHours(Long id);

}


