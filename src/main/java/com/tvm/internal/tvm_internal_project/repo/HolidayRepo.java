package com.tvm.internal.tvm_internal_project.repo;

import com.tvm.internal.tvm_internal_project.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepo extends JpaRepository<Holiday, Long> {
        @Query("SELECT h FROM Holiday h WHERE h.date BETWEEN :start AND :end")
        List<Holiday> findHolidaysBetween(
                @Param("start") LocalDate start,
                @Param("end") LocalDate end
        );
}
