package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.DTO.TimesheetDTO;
import com.tvm.internal.tvm_internal_project.model.ChartData;
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

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Timesheet>> getTimesheetById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return timesheetService.getTimesheetById(id, userDetails);
    }

    @PostMapping
    public ResponseEntity<ResponseStructure<Timesheet>> createTimesheet(@RequestBody Timesheet timesheet, @AuthenticationPrincipal UserDetails userDetails) {
        return timesheetService.createTimesheet(timesheet, userDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<Timesheet>> updateTimesheet(@PathVariable Long id, @RequestBody Timesheet timesheet, @AuthenticationPrincipal UserDetails userDetails) {
        return timesheetService.updateTimesheet(id, timesheet, userDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteTimesheet(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return timesheetService.deleteTimesheet(id, userDetails);
    }

    @GetMapping("/workhours")
    public ResponseEntity<ChartData> getWorkHoursForUser(@AuthenticationPrincipal UserDetails userDetails) {
        return timesheetService.getWorkHours(userDetails);
    }
}
