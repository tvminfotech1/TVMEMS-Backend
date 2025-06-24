package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.exception.AttendanceNotFound;
import com.tvm.internal.tvm_internal_project.model.Attendance;
import com.tvm.internal.tvm_internal_project.repo.AttendanceRepository;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepo;

    @Override
    public ResponseEntity<ResponseStructure<Attendance>> saveAttendance(Attendance attendance) {
        Attendance created = attendanceRepo.save(attendance);
        ResponseStructure<Attendance> response = new ResponseStructure<>();
        response.setBody(created);
        response.setMessage("Created Successfully");
        response.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<Attendance>>> getAllAttendance() {
        List<Attendance> allAttendance = attendanceRepo.findAll();
        ResponseStructure<List<Attendance>> response = new ResponseStructure<>();
        response.setBody(allAttendance);
        response.setMessage("List of Attendance");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<Attendance>> findAttendanceById(Long id) {
        Optional<Attendance> optionalAttendance = attendanceRepo.findById(id);
        if (optionalAttendance.isEmpty()) {
            throw new AttendanceNotFound("Attendance ID not found: " + id);
        }

        ResponseStructure<Attendance> response = new ResponseStructure<>();
        response.setBody(optionalAttendance.get());
        response.setMessage("Attendance Found");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<Attendance>> updateAttendanceById(Long id, Attendance attendance) {
        Optional<Attendance> optional = attendanceRepo.findById(id);
        if (optional.isEmpty()) {
            throw new AttendanceNotFound("Attendance ID not found: " + id);
        }

        Attendance existing = optional.get();
        existing.setDate(attendance.getDate());
        existing.setOfficeHours(attendance.getOfficeHours());
        existing.setSystemHours(attendance.getSystemHours());
        existing.setTotal(attendance.getTotal());
        existing.setBreakTime(attendance.getBreakTime());
        existing.setWorkingTime(attendance.getWorkingTime());
        existing.setStatus(attendance.getStatus());

        Attendance saved = attendanceRepo.save(existing);

        ResponseStructure<Attendance> response = new ResponseStructure<>();
        response.setBody(saved);
        response.setMessage("Attendance updated successfully");
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<String>> deleteAttendanceById(Long id) {
        Optional<Attendance> optional = attendanceRepo.findById(id);

        if (optional.isEmpty()) {
            throw new AttendanceNotFound("Attendance ID not found: " + id);
        }

        attendanceRepo.deleteById(id);

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setBody("Attendance deleted successfully with ID: " + id);
        response.setMessage("Deleted Successfully");
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
