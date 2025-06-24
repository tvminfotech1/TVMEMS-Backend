package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.AddTimeLog;
import com.tvm.internal.tvm_internal_project.service.AddTimeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/addTimeLog")
public class AddTimelogController {
    @Autowired
    private AddTimeLogService addTimeLogService;

    @PostMapping(value = "/timesheet", consumes = "multipart/form-data")
    public ResponseEntity<AddTimeLog> addTime(@RequestPart("addTimeLog") String addTimeLogJson, @RequestPart(value = "attachment", required = false) MultipartFile attachment) throws IOException {

        AddTimeLog addedTimeLog = addTimeLogService.addtime(addTimeLogJson, attachment);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedTimeLog);
    }

    @GetMapping("/timesheet")
    public ResponseEntity<List<AddTimeLog>> getbyall() {
        return ResponseEntity.ok(addTimeLogService.getbyall());
    }

    @GetMapping("/timesheet/{id}")
    public ResponseEntity<AddTimeLog> getbyid(@PathVariable Long id) {
        Optional<AddTimeLog> addTimeLog = addTimeLogService.getbyid(id);
        return addTimeLog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/timesheet/{id}", consumes = "multipart/form-data")
    public ResponseEntity<AddTimeLog> updateTime(@PathVariable Long id, @RequestPart("addTimeLog") String addTimeLogJson, @RequestPart(value = "attachment", required = false) MultipartFile attachment) {
        AddTimeLog updatedLog = addTimeLogService.updateTime(id, addTimeLogJson, attachment);
        return ResponseEntity.ok(updatedLog);
    }

    @DeleteMapping("/timesheet/{id}")
    public ResponseEntity<Void> deletebyid(@PathVariable Long id) {
        addTimeLogService.deletebyid(id);
        return ResponseEntity.noContent().build();
    }
}
