package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.WorkFromHome;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.WFHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
@RestController
@RequestMapping("WFH")
public class WFHController {

    @Autowired
    private WFHService WFHservice;

    @Autowired
    private UserRepo userRepo;

    // Both USER and ADMIN can create
    @PostMapping("/create")
    public ResponseEntity<ResponseStructure<WorkFromHome>> createWFH(@RequestBody WorkFromHome WFH) {
        return WFHservice.saveWFH(WFH);
    }



    // Only ADMIN can update status
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<ResponseStructure<WorkFromHome>> updateWFH(
            @PathVariable Long id,
            @RequestBody WorkFromHome updatedWFH) {
        return WFHservice.updateWFH(id, updatedWFH);
    }



    @GetMapping("/employeeId")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Long> getEmployeeIdByEmail(@RequestParam String email) {
        Long employeeId = WFHservice.findEmployeeIdByEmail(email);
        return ResponseEntity.ok(employeeId);
    }

    @GetMapping("/requests")
    @PreAuthorize(("hasAnyRole('ADMIN')"))
    public ResponseEntity<List<WorkFromHome>> getRequestsByMonthYear(
            @RequestParam int month,
            @RequestParam int year) {
        List<WorkFromHome> requests = WFHservice.getAllByMonthAndYear(month, year);
        return ResponseEntity.ok(requests);
    }

    // User: get their requests by month & year
    @GetMapping("/userrequests/{employeeId}")
    @PreAuthorize(("hasAnyRole('USER')"))
    public ResponseEntity<List<WorkFromHome>> getUserRequestsByMonthYear(
            @PathVariable Long employeeId,
            @RequestParam int month,
            @RequestParam int year) {
        List<WorkFromHome> requests = WFHservice.getByEmployeeAndMonthAndYear(employeeId, month, year);
        return ResponseEntity.ok(requests);
    }

}