package com.tvm.internal.tvm_internal_project.serviceImpl;


import com.tvm.internal.tvm_internal_project.model.LeaveReport;
import com.tvm.internal.tvm_internal_project.repo.LeaveReportRepo;
import com.tvm.internal.tvm_internal_project.service.LeaveReportservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveReportServiceImpl implements LeaveReportservice {


    @Autowired
    LeaveReportRepo leaveReportRepo;

    @Override
    public LeaveReport saveLeaveReport(LeaveReport leaveReport) {
        return leaveReportRepo.save(leaveReport);
    }

    @Override
    public Optional<LeaveReport> findLeaveReportById(Long id) {
        return leaveReportRepo.findById(id);
    }

    @Override
    public List<LeaveReport> findAllLeaveReports() {
        return leaveReportRepo.findAll();
    }

    @Override
    public void deleteLeaveReport(Long id) {
        leaveReportRepo.deleteById(id);
    }


}
