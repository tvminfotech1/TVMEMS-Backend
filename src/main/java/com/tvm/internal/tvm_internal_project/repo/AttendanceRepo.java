package com.tvm.internal.tvm_internal_project.repo;

import com.tvm.internal.tvm_internal_project.model.Attendance;
import com.tvm.internal.tvm_internal_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {

    List<Attendance> findByUser(User user);
    List<Attendance> findByUserEmployeeId(Long employeeId);
    Optional<Attendance> findByIdAndUser(Long id, User user);
     @Query("SELECT a FROM Attendance a WHERE a.user = :user AND a.date BETWEEN :start AND :end")
        List<Attendance> findAttendance(
                @Param("user") User user,
                @Param("start") Date start,
                @Param("end") Date end
        );

    @Query(value = "SELECT * FROM attendance WHERE employee_id = :empId AND date BETWEEN :start AND :end",
            nativeQuery = true)
    List<Attendance> getWeeklyAttendance(Long empId, Timestamp start, Timestamp end);
    boolean existsByUserEmployeeIdAndDate(Long employeeId, Date date);



}
