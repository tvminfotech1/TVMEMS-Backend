package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.model.Holiday;
import com.tvm.internal.tvm_internal_project.repo.HolidayRepo;
import com.tvm.internal.tvm_internal_project.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HolidayServiceImpl implements HolidayService {
    @Autowired
    private HolidayRepo holidayRepo;

    @Override
    public Holiday createdHoliday(Holiday holiday) {
        // 🧠 Ensure Hibernate treats it as a new entity
        if (holiday.getId() != null && holiday.getId() == 0) {
            holiday.setId(null);
        }
        return holidayRepo.save(holiday);
    }

    public List<Holiday> getOfHolidays() {
        return holidayRepo.findAll();
    }

    public Optional<Holiday> getbyId(Long id) {
        return holidayRepo.findById(id);
    }

    public Holiday update(Long id, Holiday holiday) {
        Optional<Holiday> update = holidayRepo.findById(id);
        if (update.isPresent()) {
            Holiday holidays = update.get();
            holidays.setName(holiday.getName());
            holidays.setDate(holiday.getDate());
            holidays.setDay_Name(holiday.getDay_Name());
            return holidayRepo.save(holidays);
        } else {
            throw new RuntimeException("Holiday Not found" + id);
        }
    }

    public void deleteById(Long id) {
        holidayRepo.deleteById(id);
    }

    @Override
    public List<Holiday> saveAll(List<Holiday> holidays) {
        // Clean up invalid IDs before saving
        holidays.forEach(h -> {
            if (h.getId() != null && h.getId() == 0) {
                h.setId(null);
            }
        });
        return holidayRepo.saveAll(holidays);
    }

}