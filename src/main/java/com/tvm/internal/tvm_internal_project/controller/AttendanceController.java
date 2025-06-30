package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.Attendance;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;


    @PostMapping
    public ResponseEntity<ResponseStructure<Attendance>> createAttendance(@RequestBody Attendance attendance, @AuthenticationPrincipal UserDetails userDetails) {
        return attendanceService.saveAttendance(attendance, userDetails);
    }


    @GetMapping("/all")
    public ResponseEntity<ResponseStructure<List<Attendance>>> getAttendance(@AuthenticationPrincipal UserDetails userDetails) {
        return attendanceService.getAllAttendance(userDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<Attendance>> updateAttendanceById(@PathVariable Long id, @RequestBody Attendance attendance, @AuthenticationPrincipal UserDetails userDetails) {
        return attendanceService.updateAttendanceById(id, attendance, userDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteAttendanceById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return attendanceService.deleteAttendanceById(id, userDetails);
    }
}
