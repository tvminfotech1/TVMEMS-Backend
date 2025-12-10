package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.exception.ResourceNotFound;
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
                    .orElseThrow(() -> new ResourceNotFound("Employee not found for ID: " + leaveRequest.getUser().getEmployeeId()));
        } else {
            user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFound("User not found for email: " + email));
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
    public ResponseEntity<ResponseStructure<LeaveRequest>> updateLeaveRequest(
            Long id, LeaveRequest leaveRequest, UserDetails userDetails) {
        User user = userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        LeaveRequest existingRequest;
        boolean isAdmin = user.getRoles() != null && user.getRoles().stream()
                .anyMatch(roleName -> "ROLE_ADMIN".equals(roleName));
        if (isAdmin) {
            existingRequest = leaveRequestRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFound("LeaveRequest ID " + id + " not found"));
        } else {
            existingRequest = leaveRequestRepo.findByIdAndUser(id, user)
                    .orElseThrow(() -> new ResourceNotFound(
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
                .orElseThrow(() -> new ResourceNotFound("LeaveRequest ID " + id + " not found"));
        existingRequest.setStatus(status); // Approve/Reject
        LeaveRequest savedLeave = leaveRequestRepo.save(existingRequest);
        ResponseStructure<LeaveRequest> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Leave request " + status.toLowerCase() + " successfully");
        response.setBody(savedLeave);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<LeaveRequest>>> getLeavesByEmployeeId(Long employeeId) {
        if (!userRepo.existsById(employeeId)) {
            throw new ResourceNotFound("Wrong Employee ID: " + employeeId);
        }
        List<LeaveRequest> leaves = leaveRequestRepo.findByUser_EmployeeId(employeeId);
        ResponseStructure<List<LeaveRequest>> response = new ResponseStructure<>();
        if (leaves == null || leaves.isEmpty()) {
            response.setStatusCode(404);
            response.setMessage("No leave requests found for employee ID: " + employeeId);
            response.setBody(leaves);
            return ResponseEntity.status(404).body(response);
        }
        response.setStatusCode(200);
        response.setMessage("Leave requests fetched successfully.");
        response.setBody(leaves);
        return ResponseEntity.ok(response);
    }

    @Override
    public List<LeaveRequest> getApprovedLeavesByUserId(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new ResourceNotFound("Wrong Employee ID: " + userId);
        }
        return leaveRequestRepo.findApprovedLeavesByUserId(userId);
    }

    @Override
    public ResponseEntity<ResponseStructure<Boolean>> isOnApprovedLeave(Long empId, LocalDate date) {
        int count = leaveRequestRepo.countApprovedLeave(empId, date.toString());
        ResponseStructure<Boolean> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Leave status checked");
        response.setBody(count > 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}