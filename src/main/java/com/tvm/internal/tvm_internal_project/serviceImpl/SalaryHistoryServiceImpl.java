package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.model.PayRoleEmployee;
import com.tvm.internal.tvm_internal_project.model.SalaryHistory;
import com.tvm.internal.tvm_internal_project.repo.PayRoleEmployeeRepo;
import com.tvm.internal.tvm_internal_project.repo.SalaryHistoryRepo;
import com.tvm.internal.tvm_internal_project.response.EmailContent;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.SalaryHistoryService;
import com.tvm.internal.tvm_internal_project.util.DateUtils;
import com.tvm.internal.tvm_internal_project.util.PayslipGenerator;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class SalaryHistoryServiceImpl implements SalaryHistoryService {
    @Autowired
    private SalaryHistoryRepo salaryHistoryRepo;

    @Autowired
    private PayRoleEmployeeRepo payRoleEmployeeRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${company.logo.path}")
    private String logoPath;

    public ResponseEntity<ResponseStructure<SalaryHistory>> SaveSalaryHistory(SalaryHistory salaryHistory) {
        salaryHistory.setId(null);
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
    public ResponseStructure<String> sendSalaryEmail(Long employeeId, String month) {
        ResponseStructure<String> response = new ResponseStructure<>();

        try {
            PayRoleEmployee emp = payRoleEmployeeRepo.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));

            SalaryHistory salary = salaryHistoryRepo.findSalaryDetailsByEmployeeIdAndMonth(employeeId, month).orElseThrow(() -> new RuntimeException("Salary record not found"));

            String formattedMonth = DateUtils.formatMonthYear(salary.getMonth());
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(emp.getEmail());
            helper.setSubject("Your Salary for " + formattedMonth + " has been credited");

            String htmlBody = EmailContent.generateSalaryMail(emp.getFullName() , formattedMonth, salary.getNetPay(), emp.getBankDetails().getAccountNumber());
            helper.setText(htmlBody, true);

            FileSystemResource logo = new FileSystemResource(new File(logoPath));
            helper.addInline("companyLogo", logo);

            String payslipPath = PayslipGenerator.generatePayslip(emp, salary);
            File payslipFile = new File(payslipPath);

            if (payslipFile.exists()) {
                FileSystemResource payslip = new FileSystemResource(payslipFile);
                helper.addAttachment("Payslip_" + formattedMonth + ".pdf", payslip);
            } else {
                throw new RuntimeException("Payslip generation failed — file not found");
            }

            mailSender.send(message);

            response.setBody("Salary mail sent to " + emp.getEmail());
            response.setMessage("Email with payslip sent successfully");
            response.setStatusCode(HttpStatus.OK.value());
            return response;

        } catch (Exception e) {
            response.setBody("Failed to send mail: " + e.getMessage());
            response.setMessage("Error while sending salary email");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return response;
        }
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
}