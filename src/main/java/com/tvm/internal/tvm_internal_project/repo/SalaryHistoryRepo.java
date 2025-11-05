package com.tvm.internal.tvm_internal_project.repo;

import com.tvm.internal.tvm_internal_project.model.SalaryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaryHistoryRepo extends JpaRepository<SalaryHistory, Long> {

    @Query(value = "SELECT * FROM salary_history WHERE employee_id = :employeeId AND month = :month", nativeQuery = true)
    Optional<SalaryHistory> findSalaryDetailsByEmployeeIdAndMonth(@Param("employeeId") Long employeeId, @Param("month") String month);
}