package com.tvm.internal.tvm_internal_project.controller;


import com.tvm.internal.tvm_internal_project.model.Employee;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<ResponseStructure<Employee>> addUser(@RequestBody Employee employee) {
        return employeeService.addUser(employee);
    }

    @PostMapping("/verifyByEmail")
    public ResponseEntity<ResponseStructure<Employee>> verifyByEmail(@RequestBody Employee employee) {
        return employeeService.loginByEmail(employee.getEmail(), employee.getPassword());
    }

    @PostMapping("/verifyByPhone")
    public ResponseEntity<ResponseStructure<Employee>> verifyByPhone(@RequestBody Employee employee) {
        return employeeService.loginByPhone(employee.getMobile(), employee.getPassword());

    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseStructure<String>> deleteById(@PathVariable Integer id) {
        return employeeService.deleteById(id);
    }

}


