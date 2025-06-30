package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.PayRoleEmployee;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PayRoleEmployeeService {
    ResponseEntity<ResponseStructure<PayRoleEmployee>> SavePayRoleEmployee(PayRoleEmployee employee);

    ResponseEntity<ResponseStructure<List<PayRoleEmployee>>> getAllEmployees();
}