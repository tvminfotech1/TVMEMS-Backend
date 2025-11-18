package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.exception.AttendanceNotFound;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.WorkFromHome;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.repo.WFHRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.WFHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class WFHServiceImpl implements WFHService {

    @Autowired
    private WFHRepo WFHrepo;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepo userRepo;


    @Override
    public Long getEmployeeIdByEmail(String loggedInEmail) {
        return userRepo.findIdByEmail(loggedInEmail);
    }



    public List<WorkFromHome> getAllByMonthAndYear(int month, int year) {
        return WFHrepo.findAllByMonthAndYear(month, year);
    }

    public List<WorkFromHome> getByEmployeeAndMonthAndYear(Long employeeId, int month, int year) {
        return WFHrepo.findByEmployeeIdAndMonthAndYear(employeeId, month, year);
    }

    @Override
    public List<WorkFromHome> getAllApprovalRequests() {
        return WFHrepo.findAll();
    }

    @Override
    public List<WorkFromHome> getApprovedRequestsByEmployee(Long employeeId) {
        return WFHrepo.findByEmployeeIdAndStatus(employeeId, "approved");
    }

    public ResponseEntity<ResponseStructure<WorkFromHome>> saveWFH(WorkFromHome workFromHome) {

        String email = workFromHome.getEmployeeEmail();

        String employeeName = userRepo.findNameByEmail(email);
        Long employeeId = userRepo.findIdByEmail(email);

        LocalDate fromDate=workFromHome.getFromDate();
        LocalDate toDate=workFromHome.getToDate();

        Long days= ChronoUnit.DAYS.between(fromDate, toDate);;

        workFromHome.setEmployeeName(employeeName);
        workFromHome.setEmployeeId(employeeId);
        workFromHome.setDays(days);


        // Save WFH request
        WorkFromHome created = WFHrepo.save(workFromHome);

        // Prepare response
        ResponseStructure<WorkFromHome> response = new ResponseStructure<>();
        response.setBody(created);
        response.setMessage("WFH Created Successfully");
        response.setStatusCode(HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<WorkFromHome>> updateWFH(Long id, WorkFromHome updatedWFH) {
        Optional<WorkFromHome> existingWFHOpt = WFHrepo.findById(id);
        ResponseStructure<WorkFromHome> structure = new ResponseStructure<>();

        if (existingWFHOpt.isPresent()) {
            WorkFromHome existingWFH = existingWFHOpt.get();

            // update all fields except id
            existingWFH.setEmployeeName(updatedWFH.getEmployeeName());
            existingWFH.setFromDate(updatedWFH.getFromDate());
            existingWFH.setToDate(updatedWFH.getToDate());
            existingWFH.setReason(updatedWFH.getReason());
            existingWFH.setApprover(updatedWFH.getApprover());
            existingWFH.setStatus(updatedWFH.getStatus());
            existingWFH.setAction(updatedWFH.getAction());

            System.out.println("It's worked perfectly");

            WorkFromHome saved = WFHrepo.save(existingWFH);

            sendStatusEmail(saved);


            structure.setBody(saved);
            structure.setStatusCode(HttpStatus.OK.value());
            structure.setMessage("WFH record updated successfully");

            return ResponseEntity.ok(structure);
        } else {
            structure.setStatusCode(HttpStatus.NOT_FOUND.value());
            structure.setMessage("WFH record not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(structure);
        }
    }

    private void sendStatusEmail(WorkFromHome wfh) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(wfh.getEmployeeEmail()); // Replace with actual employee email from WFH entity
        message.setSubject("WFH Request Status Updated");
        message.setText("Hello " + wfh.getEmployeeName() + ",\n\n" +
                "Your WFH request from " + wfh.getFromDate() + " to " + wfh.getToDate() +
                " has been " + wfh.getStatus().toUpperCase() + ".\n\n" +
                "Reason: " + wfh.getReason() + "\n" +
                "Approved/Rejected by: " + wfh.getApprover() + "\n\n" +
                "Regards,\nWFH Management Team");

        mailSender.send(message);
    }

    @Override
    public Long findEmployeeIdByEmail(String email) {

        Long employeeId = userRepo.findIdByEmail(email);
        if (employeeId == null) {
            throw new RuntimeException("Employee with email " + email + " not found");
        }
        return employeeId;
    }





}