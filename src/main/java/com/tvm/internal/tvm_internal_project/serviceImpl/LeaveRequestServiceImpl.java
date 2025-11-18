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

        // 1. Check if the payload contains an employeeId (used by Admin)
        if (leaveRequest.getUser() != null && leaveRequest.getUser().getEmployeeId() != null) {
            user = userRepo.findByEmployeeId(leaveRequest.getUser().getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found for ID: " + leaveRequest.getUser().getEmployeeId()));
        } else {
            // 2. Default to the currently logged-in user (User or Admin applying for self)
            user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found for email: " + email));
        }

        leaveRequest.setUser(user);
        leaveRequest.setDateOfRequest(LocalDate.now());
        leaveRequest.setStatus("Pending");
        // Ensure ID is null for creation
        leaveRequest.setId(null);

        return leaveRequestRepo.save(leaveRequest);
    }

    @Override
    public ResponseEntity<ResponseStructure<LeaveRequest>> createLeaveRequest(LeaveRequest leaveRequest, UserDetails userDetails) {
        String email = userDetails.getUsername();

        // Use the core logic to create and save the leave request
        LeaveRequest savedLeave = createLeave(leaveRequest, email);

        // Build response
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

        // Fetch the user from DB
        User user = userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        LeaveRequest existingRequest;

        // Check if user is admin
        boolean isAdmin = user.getRoles() != null && user.getRoles().stream()
                .anyMatch(roleName -> "ROLE_ADMIN".equals(roleName));

        if (isAdmin) {
            // Admin can update any leave request
            existingRequest = leaveRequestRepo.findById(id)
                    .orElseThrow(() -> new NoTaskFoundException("LeaveRequest ID " + id + " not found"));
        } else {
            // Normal user can only update their own leave request
            existingRequest = leaveRequestRepo.findByIdAndUser(id, user)
                    .orElseThrow(() -> new NoTaskFoundException(
                            "LeaveRequest ID " + id + " not found for user " + user.getEmail()));
        }

        // Apply partial updates
        if (leaveRequest.getStartDate() != null) existingRequest.setStartDate(leaveRequest.getStartDate());
        if (leaveRequest.getEndDate() != null) existingRequest.setEndDate(leaveRequest.getEndDate());
        if (leaveRequest.getReason() != null) existingRequest.setReason(leaveRequest.getReason());
        if (leaveRequest.getStatus() != null && isAdmin) existingRequest.setStatus(leaveRequest.getStatus());
        if (leaveRequest.getLeaveType() != null) existingRequest.setLeaveType(leaveRequest.getLeaveType());

        // Save updated leave request
        LeaveRequest savedLeave = leaveRequestRepo.save(existingRequest);

        // Build response
        ResponseStructure<LeaveRequest> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Leave request updated successfully");
        response.setBody(savedLeave);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<String>> deleteLeaveRequest(Long id, UserDetails userDetails) throws NoTaskFoundException {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Allow Admin to delete any request
        boolean isAdmin = user.getRoles() != null && user.getRoles().stream()
                .anyMatch(roleName -> "ROLE_ADMIN".equals(roleName));

        Optional<LeaveRequest> optional;
        if (isAdmin) {
            optional = leaveRequestRepo.findById(id);
        } else {
            optional = leaveRequestRepo.findByIdAndUser(id, user);
        }

        if (optional.isEmpty()) {
            throw new NoTaskFoundException("LeaveRequest ID not present or access denied.");
        }

        leaveRequestRepo.deleteById(id);

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setBody("LeaveRequest deleted successfully");
        response.setMessage("Success");
        response.setStatusCode(HttpStatus.OK.value());

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
        // Admin can update any leave request directly
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

    @Override
    public ResponseEntity<ResponseStructure<List<LeaveRequest>>> getLeavesByEmployeeId(Long employeeId) {

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