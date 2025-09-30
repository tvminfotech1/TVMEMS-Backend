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

import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestservice {

    @Autowired
    private LeaveRequestRepo leaveRequestRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public ResponseEntity<ResponseStructure<LeaveRequest>> createLeaveRequest(LeaveRequest leaveRequest, UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        leaveRequest.setUser(user);
        LeaveRequest saved = leaveRequestRepo.save(leaveRequest);

        ResponseStructure<LeaveRequest> response = new ResponseStructure<>();
        response.setBody(saved);
        response.setMessage("Saved successfully");
        response.setStatusCode(HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<LeaveRequest>> getLeaveRequest(UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<LeaveRequest> leaverequests = leaveRequestRepo.findByUser(user);

        ResponseStructure<LeaveRequest> response = new ResponseStructure<>();
        if (!leaverequests.isEmpty()) {
            response.setBody(leaverequests.get(0));
            response.setMessage("LeaveRequest fetched successfully");
            response.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setBody(null);
            response.setMessage("LeaveRequest not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ResponseStructure<LeaveRequest>> updateLeaveRequest(Long id, LeaveRequest leaveRequest, UserDetails userDetails) throws NoTaskFoundException {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Optional<LeaveRequest> optional = leaveRequestRepo.findByIdAndUser(id, user);
        if (optional.isEmpty()) {
            throw new NoTaskFoundException("LeaveRequest ID is not found");
        }

        LeaveRequest existingRequest = optional.get();
        existingRequest.setLeaveType(leaveRequest.getLeaveType());
        existingRequest.setLeaveDate(leaveRequest.getLeaveDate());
        existingRequest.setTotalDays(leaveRequest.getTotalDays());
        existingRequest.setReason(leaveRequest.getReason());
        existingRequest.setStatus(leaveRequest.getStatus());
        existingRequest.setDateOfRequest(leaveRequest.getDateOfRequest());
        existingRequest.setBooked(leaveRequest.getBooked());
        existingRequest.setTeamEmail(leaveRequest.getTeamEmail());
        existingRequest.setColor(leaveRequest.getColor());

        LeaveRequest updated = leaveRequestRepo.save(existingRequest);

        ResponseStructure<LeaveRequest> response = new ResponseStructure<>();
        response.setBody(updated);
        response.setMessage("LeaveRequest updated successfully");
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<String>> deleteLeaveRequest(Long id, UserDetails userDetails) throws NoTaskFoundException {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Optional<LeaveRequest> optional = leaveRequestRepo.findByIdAndUser(id, user);
        if (optional.isEmpty()) {
            throw new NoTaskFoundException("LeaveRequest ID not present");
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

}
