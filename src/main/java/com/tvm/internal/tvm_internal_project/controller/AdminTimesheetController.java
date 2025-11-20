package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.Timesheet;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/timesheet")
public class AdminTimesheetController {

    @Autowired
    private TimesheetService timesheetService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<ResponseStructure<Timesheet>> updateTimesheetStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String status = body.get("status");
        return timesheetService.updateTimesheetStatus(id, status);
    }

}
