package com.tvm.internal.tvm_internal_project.repo;

import com.tvm.internal.tvm_internal_project.model.Attendance;
import com.tvm.internal.tvm_internal_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {

    List<Attendance> findByUser(User user);
    List<Attendance> findByUserEmployeeId(Long employeeId);
    Optional<Attendance> findByIdAndUser(Long id, User user);



}
