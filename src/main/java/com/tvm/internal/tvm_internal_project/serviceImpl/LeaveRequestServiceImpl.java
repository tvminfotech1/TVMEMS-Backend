package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.exception.NoTaskFoundException;
import com.tvm.internal.tvm_internal_project.model.LeaveRequest;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.repo.LeaveRequestRepo;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.LeaveRequestservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestservice {

    @Autowired
    private LeaveRequestRepo leaveRequestRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public LeaveRequest createLeave(LeaveRequest leaveRequest, String email) {
        User user;
        if (leaveRequest.getUser() != null && leaveRequest.getUser().getEmployeeId() != null) {
            user = userRepo.findByEmployeeId(leaveRequest.getUser().getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found for ID: " + leaveRequest.getUser().getEmployeeId()));
        } else {
            user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found for email: " + email));
        }
        leaveRequest.setUser(user);
        leaveRequest.setDateOfRequest(LocalDate.now());
        leaveRequest.setStatus("Pending");
        leaveRequest.setId(null);
        return leaveRequestRepo.save(leaveRequest);
    }

    @Override
    public ResponseEntity<ResponseStructure<LeaveRequest>> createLeaveRequest(LeaveRequest leaveRequest, UserDetails userDetails) {
        String email = userDetails.getUsername();
        LeaveRequest savedLeave = createLeave(leaveRequest, email);
        ResponseStructure<LeaveRequest> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value()); // 💡 Use CREATED status for POST
        response.setMessage("Leave request created successfully");
        response.setBody(savedLeave);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<ResponseStructure<List<LeaveRequest>>> getLeaveRequest(UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<LeaveRequest> leaveRequests = leaveRequestRepo.findByUser(user);
        ResponseStructure<List<LeaveRequest>> response = new ResponseStructure<>();
        if (!leaveRequests.isEmpty()) {
            response.setBody(leaveRequests);
            response.setMessage("Leave requests fetched successfully");
            response.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setBody(leaveRequests); // Return empty list instead of null body for NOT_FOUND
            response.setMessage("No leave requests found");
            response.setStatusCode(HttpStatus.OK.value()); // Change to OK, as fetching an empty list is a success
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResponseStructure<LeaveRequest>> updateLeaveRequest(
            Long id, LeaveRequest leaveRequest, UserDetails userDetails) {
        User user = userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        LeaveRequest existingRequest;
        boolean isAdmin = user.getRoles() != null && user.getRoles().stream()
                .anyMatch(roleName -> "ROLE_ADMIN".equals(roleName));
        if (isAdmin) {
            existingRequest = leaveRequestRepo.findById(id)
                    .orElseThrow(() -> new NoTaskFoundException("LeaveRequest ID " + id + " not found"));
        } else {
            existingRequest = leaveRequestRepo.findByIdAndUser(id, user)
                    .orElseThrow(() -> new NoTaskFoundException(
                            "LeaveRequest ID " + id + " not found for user " + user.getEmail()));
        }
        if (leaveRequest.getStartDate() != null) existingRequest.setStartDate(leaveRequest.getStartDate());
        if (leaveRequest.getEndDate() != null) existingRequest.setEndDate(leaveRequest.getEndDate());
        if (leaveRequest.getReason() != null) existingRequest.setReason(leaveRequest.getReason());
        if (leaveRequest.getStatus() != null && isAdmin) existingRequest.setStatus(leaveRequest.getStatus());
        if (leaveRequest.getLeaveType() != null) existingRequest.setLeaveType(leaveRequest.getLeaveType());
        LeaveRequest savedLeave = leaveRequestRepo.save(existingRequest);
        ResponseStructure<LeaveRequest> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Leave request updated successfully");
        response.setBody(savedLeave);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<LeaveRequest>>> getAllLeaveRequests() {
        List<LeaveRequest> leaveRequests = leaveRequestRepo.findAll();
        ResponseStructure<List<LeaveRequest>> response = new ResponseStructure<>();
        response.setBody(leaveRequests);
        response.setMessage("All Leave Requests fetched successfully");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<ResponseStructure<LeaveRequest>> updateLeaveStatus(Long id, String status) {
        LeaveRequest existingRequest = leaveRequestRepo.findById(id)
                .orElseThrow(() -> new NoTaskFoundException("LeaveRequest ID " + id + " not found"));
        existingRequest.setStatus(status); // Approve/Reject
        LeaveRequest savedLeave = leaveRequestRepo.save(existingRequest);
        ResponseStructure<LeaveRequest> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Leave request " + status.toLowerCase() + " successfully");
        response.setBody(savedLeave);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<ResponseStructure<LeaveRequest>> applyLeaveForOtherUser(LeaveRequest leaveRequest, UserDetails adminDetails) {
        String adminEmail = adminDetails.getUsername();
        LeaveRequest savedLeave = createLeave(leaveRequest, adminEmail);

        ResponseStructure<LeaveRequest> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Leave request created successfully for employee: " + savedLeave.getUser().getEmployeeId());
        response.setBody(savedLeave);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}