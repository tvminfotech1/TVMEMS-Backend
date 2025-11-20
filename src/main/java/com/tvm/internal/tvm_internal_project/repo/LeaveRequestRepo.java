package com.tvm.internal.tvm_internal_project.repo;

import com.tvm.internal.tvm_internal_project.model.LeaveRequest;
import com.tvm.internal.tvm_internal_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, Long> {
    Optional<LeaveRequest> findByIdAndUser(Long id, User user);

    List<LeaveRequest> findByUser(User user);


    @Query("SELECT l FROM LeaveRequest l WHERE l.user.id = :userId AND l.status = 'Approved'")
    List<LeaveRequest> findApprovedLeavesByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(l) FROM LeaveRequest l " +
            "WHERE l.user.employeeId = :empId " +
            "AND l.status = 'APPROVED' " +
            "AND :date BETWEEN l.startDate AND l.endDate")
    int countApprovedLeave(Long empId, String date);
    
    List<LeaveRequest> findByUser_EmployeeId(Long employeeId);
}
