package com.tvm.internal.tvm_internal_project.repo;

import com.tvm.internal.tvm_internal_project.model.WorkFromHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WFHRepo extends JpaRepository<WorkFromHome, Long> {

    List<WorkFromHome> findByEmployeeId(Long employeeId);

    @Query("SELECT w FROM WorkFromHome w WHERE MONTH(w.fromDate) = :month AND YEAR(w.fromDate) = :year")
    List<WorkFromHome> findAllByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT w FROM WorkFromHome w WHERE w.employeeId = :employeeId AND MONTH(w.fromDate) = :month AND YEAR(w.fromDate) = :year")
    List<WorkFromHome> findByEmployeeIdAndMonthAndYear(@Param("employeeId") Long employeeId,
                                                       @Param("month") int month,
                                                       @Param("year") int year);

    List<WorkFromHome> findByEmployeeIdAndStatus(Long employeeId, String status);

    List<WorkFromHome> findByEmployeeIdAndStatusIn(Long employeeId, List<String> statuses);
}