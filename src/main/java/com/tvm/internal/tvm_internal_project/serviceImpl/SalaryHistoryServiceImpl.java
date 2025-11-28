package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.exception.DuplicateException;
import com.tvm.internal.tvm_internal_project.model.PayRoleEmployee;
import com.tvm.internal.tvm_internal_project.model.SalaryHistory;
import com.tvm.internal.tvm_internal_project.repo.PayRoleEmployeeRepo;
import com.tvm.internal.tvm_internal_project.repo.SalaryHistoryRepo;
import com.tvm.internal.tvm_internal_project.request.SalaryHistoryRequestDTO;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.SalaryHistoryService;
import com.tvm.internal.tvm_internal_project.util.PayslipGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryHistoryServiceImpl implements SalaryHistoryService {
    @Autowired
    private SalaryHistoryRepo salaryHistoryRepo;

    @Autowired
    private PayRoleEmployeeRepo payRoleEmployeeRepo;

    public ResponseEntity<ResponseStructure<SalaryHistory>> SaveSalaryHistory(SalaryHistoryRequestDTO dto) {
        String salaryId = dto.getSalaryId();
        if (salaryHistoryRepo.existsBySalaryId(salaryId)) {
            throw new DuplicateException("Salary already generated for this employee for this month.");
        }
        SalaryHistory salaryHistory = mapToEntity(dto);
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

    @Override
    public String generatePayslip(Long employeeId, String month) {
        try {
            PayRoleEmployee emp = payRoleEmployeeRepo.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
            SalaryHistory salary = salaryHistoryRepo.findSalaryDetailsByEmployeeIdAndMonth(employeeId, month).orElseThrow(() -> new RuntimeException("Salary record not found"));
            String filePath = PayslipGenerator.generatePayslip(emp, salary);
            return filePath;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate payslip: " + e.getMessage());
        }
    }

    private SalaryHistory mapToEntity(SalaryHistoryRequestDTO dto) {
        PayRoleEmployee employee = payRoleEmployeeRepo.findById(dto.getPayRoleEmployee())
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + dto.getPayRoleEmployee()));
        SalaryHistory salaryHistory = new SalaryHistory();
        salaryHistory.setSalaryId(dto.getSalaryId());
        salaryHistory.setMonth(dto.getMonth());
        salaryHistory.setYear(dto.getYear());
        salaryHistory.setBasicSalary(dto.getBasicSalary());
        salaryHistory.setHra(dto.getHra());
        salaryHistory.setMedicalAllowance(dto.getMedicalAllowance());
        salaryHistory.setConveyanceAllowance(dto.getConveyanceAllowance());
        salaryHistory.setFlexiBenefit(dto.getFlexiBenefit());
        salaryHistory.setLeaveTravel(dto.getLeaveTravel());
        salaryHistory.setSpecialAllowance(dto.getSpecialAllowance());
        salaryHistory.setPf(dto.getPf());
        salaryHistory.setEsi(dto.getEsi());
        salaryHistory.setProfessionalTax(dto.getProfessionalTax());
        salaryHistory.setIncomeTax(dto.getIncomeTax());
        salaryHistory.setLeaveDeduction(dto.getLeaveDeduction());
        salaryHistory.setOtherDeduction(dto.getOtherDeduction());
        salaryHistory.setNetPay(dto.getNetPay());
        salaryHistory.setCtc(dto.getCtc());
        salaryHistory.setRemainingCtc(dto.getRemainingCtc());
        salaryHistory.setNwd(dto.getNwd());
        salaryHistory.setNol(dto.getNol());
        salaryHistory.setPayRoleEmployee(employee);
        return salaryHistory;
    }

    @Override
    public List<SalaryHistory> getSalaryHistoryByEmployeeId(Long employeeId) {
        return salaryHistoryRepo.findByPayRoleEmployeeId(employeeId);
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseStructure<String>> deleteSalary(String salaryId) {
        Optional<SalaryHistory> existingSalary = salaryHistoryRepo.findBySalaryId(salaryId);

        if (existingSalary.isEmpty()) {
            throw new RuntimeException("Salary with id " + salaryId + " not found.");
        }

        salaryHistoryRepo.deleteBySalaryId(salaryId);

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setBody("Salary deleted successfully.");
        response.setMessage("Salary removed");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}