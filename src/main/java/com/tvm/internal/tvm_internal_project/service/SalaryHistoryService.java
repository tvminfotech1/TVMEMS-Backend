package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.SalaryHistory;
import com.tvm.internal.tvm_internal_project.request.SalaryHistoryRequestDTO;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SalaryHistoryService {
    ResponseEntity<ResponseStructure<SalaryHistory>> SaveSalaryHistory(SalaryHistoryRequestDTO salaryHistory);

    ResponseEntity<ResponseStructure<List<SalaryHistory>>> getAllHistory();

    ResponseStructure<String> sendSalaryEmail(Long employeeId, String month);

    String generatePayslip(Long employeeId, String month);

    List<SalaryHistory> getSalaryHistoryByEmployeeId(Long employeeId);
}