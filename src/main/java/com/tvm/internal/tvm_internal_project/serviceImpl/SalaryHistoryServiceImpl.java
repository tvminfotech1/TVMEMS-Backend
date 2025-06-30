package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.model.SalaryHistory;
import com.tvm.internal.tvm_internal_project.repo.SalaryHistoryRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.SalaryHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryHistoryServiceImpl implements SalaryHistoryService {
    @Autowired
    private SalaryHistoryRepo salaryHistoryRepo;

    public ResponseEntity<ResponseStructure<SalaryHistory>> SaveSalaryHistory(SalaryHistory salaryHistory) {
        SalaryHistory history = salaryHistoryRepo.save(salaryHistory);
        ResponseStructure<SalaryHistory> salaryDTO = new ResponseStructure<>();
        salaryDTO.setBody(history);
        salaryDTO.setMessage("SalaryHistory Details Saved Successfully!!!");
        salaryDTO.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(salaryDTO, HttpStatus.CREATED);
    }


    public ResponseEntity<ResponseStructure<List<SalaryHistory>>> getAllHistory() {
        List<SalaryHistory> salaryHistories = salaryHistoryRepo.findAll();
        ResponseStructure<List<SalaryHistory>> salaryDTO = new ResponseStructure<>();
        salaryDTO.setBody(salaryHistories);
        salaryDTO.setMessage("List of all SalaryHistory Details");
        salaryDTO.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(salaryDTO, HttpStatus.OK);
    }
}