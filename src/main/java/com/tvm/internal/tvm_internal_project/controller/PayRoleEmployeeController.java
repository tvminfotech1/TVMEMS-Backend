package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.DTO.PayRunsDTO;
import com.tvm.internal.tvm_internal_project.model.PayRoleEmployee;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.response.UserPaySlipDto;
import com.tvm.internal.tvm_internal_project.service.PayRoleEmployeeService;
import com.tvm.internal.tvm_internal_project.serviceImpl.PayRoleEmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/employeePayRole")
public class PayRoleEmployeeController {

    @Autowired
    private  PayRoleEmployeeService employeeService;

@Autowired
private UserRepo userRepo;

    @Autowired
    private PayRoleEmployeeServiceImpl payRoleServiceImpl;
    @PostMapping
    public ResponseEntity<ResponseStructure<PayRoleEmployee>> savePayRoleEmployee(@RequestBody PayRoleEmployee employee) {
        return employeeService.SavePayRoleEmployee(employee);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<PayRoleEmployee>>> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<PayRoleEmployee>> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<PayRoleEmployee>> updateEmployee(
            @PathVariable Long id,
            @RequestBody PayRoleEmployee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseStructure<PayRoleEmployee>> updateEmployeeStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        String status = request.get("status");
        ResponseStructure<PayRoleEmployee> response = new ResponseStructure<>();

        return employeeService.updateEmployeeStatus(id, status);

    }

    @GetMapping("/getPayRunData")
    public List<PayRunsDTO> getPayRuns(@RequestParam String month) {
        return employeeService.getPayRunsData(month);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.deletePayrole(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/joiningDate/{id}")
    public ResponseEntity<ResponseStructure<UserPaySlipDto>> getEmployeeJoiningDate(@PathVariable Long id){
        return employeeService.getPayRunsUser(id);
    }

}