package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.Attendance;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AttendanceService {

    ResponseEntity<ResponseStructure<Attendance>> saveAttendance(Attendance attendance);

    ResponseEntity<ResponseStructure<List<Attendance>>> getAllAttendance();

    ResponseEntity<ResponseStructure<Attendance>> findAttendanceById(Long id);

    ResponseEntity<ResponseStructure<Attendance>> updateAttendanceById(Long id, Attendance attendance);

    ResponseEntity<ResponseStructure<String>> deleteAttendanceById(Long id);
}
