package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.Employee;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

public interface EmployeeService {
    ResponseEntity<ResponseStructure<Employee>> addUser(Employee employee);

    ResponseEntity<ResponseStructure<Employee>> loginByEmail(String email, String password);

    ResponseEntity<ResponseStructure<Employee>> loginByPhone(Long mobile, String password);

    ResponseEntity<ResponseStructure<String>> deleteById(Integer id);

}
