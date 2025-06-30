package com.tvm.internal.tvm_internal_project.serviceImpl;


import com.tvm.internal.tvm_internal_project.exception.NoTaskFoundException;
import com.tvm.internal.tvm_internal_project.model.LeaveReport;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.repo.LeaveReportRepo;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.LeaveReportservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveReportServiceImpl implements LeaveReportservice {


    @Autowired
    LeaveReportRepo leaveReportRepo;
    @Autowired
    UserRepo userRepo;

    @Override
    public ResponseEntity<ResponseStructure<LeaveReport>> saveLeaveReport(LeaveReport leaveReport, UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not found"));
        leaveReport.setUser(user);
        LeaveReport saved = leaveReportRepo.save(leaveReport);
        ResponseStructure<LeaveReport> response = new ResponseStructure<>();
        response.setBody(saved);
        response.setMessage("Saved Successfully");
        response.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<LeaveReport>>> findLeaveReportById(UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<LeaveReport> reports = leaveReportRepo.findByUser(user);
        ResponseStructure<List<LeaveReport>> response = new ResponseStructure<>();
        response.setBody(reports);
        response.setMessage("Saved Successfully");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ResponseStructure<String>> deleteLeaveReport(Long id, UserDetails userDetails) throws NoTaskFoundException {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        Optional<LeaveReport> leave = leaveReportRepo.findByEmployeeIdAndUser(id, user);
        if (leave.isEmpty()) {
            throw new NoTaskFoundException("LeaveReport id is not found");
        }
        leaveReportRepo.deleteById(id);
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setBody(null);
        response.setMessage("Deleted Successfully");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
