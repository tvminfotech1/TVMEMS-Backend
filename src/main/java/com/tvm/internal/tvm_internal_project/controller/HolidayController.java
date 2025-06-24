package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.Holiday;
import com.tvm.internal.tvm_internal_project.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class HolidayController {
    @Autowired
    private HolidayService holidayService;

    @PostMapping("/Holiday")
    public ResponseEntity<Holiday> create(@RequestBody Holiday holiday) {
        return ResponseEntity.ok(holidayService.createdHoliday(holiday));
    }

    @GetMapping("/Holiday")
    public ResponseEntity<List<Holiday>> getbyall() {
        return ResponseEntity.ok(holidayService.getOfHolidays());
    }

    @GetMapping("/Holiday/{id}")
    public ResponseEntity<Holiday> getbyid(@PathVariable Long id) {
        Optional<Holiday> holiday = holidayService.getbyId(id);
        return holiday.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/Holiday/{id}")
    public ResponseEntity<Holiday> updatebyid(@PathVariable Long id, @RequestBody Holiday holiday) {
        return ResponseEntity.ok(holidayService.update(id, holiday));
    }

    @DeleteMapping("/Holiday/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        holidayService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
