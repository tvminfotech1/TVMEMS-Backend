package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.model.LeaveRequest;
import com.tvm.internal.tvm_internal_project.repo.LeaveRequestRepo;
import com.tvm.internal.tvm_internal_project.service.LeaveRequestservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestservice {
    @Autowired
    private LeaveRequestRepo leaveRequestRepo;

    @Override
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepo.findAll();
    }

    @Override
    public Optional<LeaveRequest> getLeaveRequestById(Long id) {
        return leaveRequestRepo.findById(id);
    }

    @Override
    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        return leaveRequestRepo.save(leaveRequest);
    }

    @Override
    public Optional<LeaveRequest> updateLeaveRequest(Long id, LeaveRequest leaveRequest) {
        return leaveRequestRepo.findById(id).map(existingRequest -> {
            existingRequest.setLeaveType(leaveRequest.getLeaveType());
            existingRequest.setLeaveDate(leaveRequest.getLeaveDate());
            existingRequest.setTotalDays(leaveRequest.getTotalDays());
            existingRequest.setReason(leaveRequest.getReason());
            existingRequest.setStatus(leaveRequest.getStatus());
            existingRequest.setDateOfRequest(leaveRequest.getDateOfRequest());
            existingRequest.setBooked(leaveRequest.getBooked());
            existingRequest.setTeamEmail(leaveRequest.getTeamEmail());
            existingRequest.setColor(leaveRequest.getColor());
            return leaveRequestRepo.save(existingRequest);
        });
    }

    @Override
    public boolean deleteLeaveRequest(Long id) {

        if (leaveRequestRepo.existsById(id)) {
            leaveRequestRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
