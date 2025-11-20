package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.Attendance;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface AttendanceService {
    ResponseEntity<ResponseStructure<Attendance>> saveAttendance(Attendance attendance, UserDetails userDetails );
    ResponseEntity<ResponseStructure<List<Attendance>>> getAllAttendance(UserDetails userDetails);
    ResponseEntity<ResponseStructure<String>> deleteAttendanceById(Long id,UserDetails userDetails);
    public List<Attendance> getAttendanceByEmployeeId(Long employeeId);
}
