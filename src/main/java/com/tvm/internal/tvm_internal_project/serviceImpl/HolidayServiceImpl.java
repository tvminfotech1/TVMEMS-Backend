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

    public Holiday createdHoliday(Holiday holiday) {
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

}
