package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.DTO.TimesheetDTO;
import com.tvm.internal.tvm_internal_project.model.Timesheet;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user/timesheet")
public class TimesheetController {

    @Autowired
    private TimesheetService timesheetService;

    @GetMapping("/all")
    public ResponseEntity<ResponseStructure<List<TimesheetDTO>>> getAllTimesheetsForAdmin() {
        return timesheetService.getAllTimesheetsForAdmin();
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Timesheet>>> getAllTimesheets(@AuthenticationPrincipal UserDetails userDetails) {
        return timesheetService.getAllTimesheets(userDetails);
    }

    @PostMapping
    public ResponseEntity<ResponseStructure<Timesheet>> createTimesheet(@RequestBody Timesheet timesheet, @AuthenticationPrincipal UserDetails userDetails) {
        return timesheetService.createTimesheet(timesheet, userDetails);
    }

}
