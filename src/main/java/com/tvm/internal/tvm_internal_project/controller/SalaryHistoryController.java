package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.SalaryHistory;
import com.tvm.internal.tvm_internal_project.request.SalaryHistoryRequestDTO;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.SalaryHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/api/salaryHistory")
public class SalaryHistoryController {

    @Autowired
    private SalaryHistoryService salaryHistoryService;

    @PostMapping
    public ResponseEntity<ResponseStructure<SalaryHistory>> savePayRoleEmployee(@RequestBody SalaryHistoryRequestDTO salaryHistory) {
        return salaryHistoryService.SaveSalaryHistory(salaryHistory);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<SalaryHistory>>> getAllEmployees() {
        return salaryHistoryService.getAllHistory();
    }

    @PostMapping("/send-salary-mail/{employeeId}/{month}")
    public ResponseEntity<ResponseStructure<String>> sendSalaryMail(@PathVariable Long employeeId, @PathVariable String month) {
        ResponseStructure<String> response = salaryHistoryService.sendSalaryEmail(employeeId, month);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/generate-payslip/{employeeId}")
    public ResponseEntity<byte[]> generatePayslip(@PathVariable Long employeeId, @RequestParam String month) {
        try {
            String filePath = String.valueOf(salaryHistoryService.generatePayslip(employeeId, month));

            File file = new File(filePath);
            if (!file.exists()) {
                throw new RuntimeException("Generated file not found");
            }
            byte[] pdfBytes = Files.readAllBytes(file.toPath());

            String filename = "Payslip_" + employeeId + "_" + month + ".pdf";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_PDF).body(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<SalaryHistory>> getSalaryByEmployeeId(@PathVariable Long employeeId) {
        List<SalaryHistory> salaryList = salaryHistoryService.getSalaryHistoryByEmployeeId(employeeId);
        if (salaryList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(salaryList);
    }

    @DeleteMapping("/{salaryId}")
    public ResponseEntity<ResponseStructure<String>> deleteSalary(@PathVariable String salaryId){
        return salaryHistoryService.deleteSalary(salaryId);
    }

}