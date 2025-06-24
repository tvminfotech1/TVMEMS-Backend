package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.Holiday;

import java.util.List;
import java.util.Optional;


public interface HolidayService {
    Holiday createdHoliday(Holiday holiday);

    List<Holiday> getOfHolidays();

    Optional<Holiday> getbyId(Long id);

    Holiday update(Long id, Holiday holiday);

    void deleteById(Long id);
}
