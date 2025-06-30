package com.tvm.internal.tvm_internal_project.repo;


import com.tvm.internal.tvm_internal_project.model.LeaveReport;
import com.tvm.internal.tvm_internal_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveReportRepo extends JpaRepository<LeaveReport ,Long> {
    List<LeaveReport> findByUser(User user);
    Optional<LeaveReport> findByEmployeeIdAndUser(Long employeeId, User user);



}
