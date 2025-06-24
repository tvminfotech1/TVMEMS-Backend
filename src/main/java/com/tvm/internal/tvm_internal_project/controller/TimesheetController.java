package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.ChartData;
import com.tvm.internal.tvm_internal_project.model.Timesheet;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class TimesheetController {

    @Autowired
    private TimesheetService timesheetService;

    @GetMapping("/timesheets/{id}")
    public ResponseEntity<ResponseStructure<Timesheet>> getTimesheetById(@PathVariable Long id) {
        return timesheetService.getTimesheetById(id);
    }

    @GetMapping("/Workhours/{id}")
    public ResponseEntity<ChartData> getWorkHouse(@PathVariable Long id) {
        return timesheetService.getWorkHours(id);
    }

    @GetMapping("/timesheets")
    public ResponseEntity<ResponseStructure<List<Timesheet>>> getAllTimesheet() {
        return timesheetService.getAllTimesheets();
    }

    @PostMapping("/timesheets")
    public ResponseEntity<ResponseStructure<Timesheet>> createTimesheet(@RequestBody Timesheet timesheet) {
        return timesheetService.createTimesheet(timesheet);
    }

    @PutMapping("/timesheets/{id}")
    public ResponseEntity<ResponseStructure<Timesheet>> updateTimesheet(@PathVariable Long id, @RequestBody Timesheet timesheet) {
        return timesheetService.updateTimesheet(id, timesheet);
    }

    @DeleteMapping("/timesheets/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteTimesheet(@PathVariable Long id) {
        return timesheetService.deleteTimesheet(id);
    }
}



