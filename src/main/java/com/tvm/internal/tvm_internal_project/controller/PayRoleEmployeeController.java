package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.PayRoleEmployee;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.PayRoleEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employeePayRole")
public class PayRoleEmployeeController {

    @Autowired
    private PayRoleEmployeeService employeeService;

    @PostMapping
    public ResponseEntity<ResponseStructure<PayRoleEmployee>> savePayRoleEmployee(@RequestBody PayRoleEmployee employee) {
        return employeeService.SavePayRoleEmployee(employee);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<PayRoleEmployee>>> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
}