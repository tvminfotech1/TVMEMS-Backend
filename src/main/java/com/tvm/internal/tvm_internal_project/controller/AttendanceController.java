package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.Attendance;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;


    @PostMapping
    public ResponseEntity<ResponseStructure<Attendance>> createAttendance(@RequestBody Attendance attendance) {
        return attendanceService.saveAttendance(attendance);
    }


    @GetMapping("/all")
    public ResponseEntity<ResponseStructure<List<Attendance>>> getAllAttendance() {
        return attendanceService.getAllAttendance(); // HTTP 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Attendance>> getAttendanceById(@PathVariable Long id) {
        return attendanceService.findAttendanceById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<Attendance>> updateAttendanceById(@PathVariable Long id, @RequestBody Attendance attendance) {
        return attendanceService.updateAttendanceById(id, attendance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteAttendanceById(@PathVariable Long id) {
        return attendanceService.deleteAttendanceById(id);
    }
}
