package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.exception.AttendanceNotFound;
import com.tvm.internal.tvm_internal_project.exception.NoTaskFoundException;
import com.tvm.internal.tvm_internal_project.model.Attendance;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.repo.AttendanceRepo;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceRepo attendanceRepo;
    @Autowired
    private UserRepo userRepo;
    @Override
    public ResponseEntity<ResponseStructure<Attendance>> saveAttendance(Attendance attendance, UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        attendance.setUser(user);
        Attendance created = attendanceRepo.save(attendance);
        ResponseStructure<Attendance> response = new ResponseStructure<>();
        response.setBody(created);
        response.setMessage("Attendance Created Successfully");
        response.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<Attendance>>> getAllAttendance(UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Attendance> lists = attendanceRepo.findAll();
        if (lists.isEmpty()) {
            throw new AttendanceNotFound("Attendance ID not found: ");
        }
        ResponseStructure<List<Attendance>> response = new ResponseStructure<>();
        response.setBody(lists);
        response.setMessage("Get Attendance successfully");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<Attendance>> updateAttendanceById(Long id, Attendance attendance, UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Optional<Attendance> optional = attendanceRepo.findByIdAndUser(id, user);
        if (optional.isEmpty()) {
            throw new NoTaskFoundException("Attendance ID not found");
        }
        Attendance existing = optional.get();
        existing.setDate(attendance.getDate());
        existing.setOfficeHours(attendance.getOfficeHours());
        existing.setSystemHours(attendance.getSystemHours());
        existing.setTotal(attendance.getTotal());
        existing.setBreakTime(attendance.getBreakTime());
        existing.setWorkingTime(attendance.getWorkingTime());
        existing.setName(attendance.getName());
        existing.setDepartment(attendance.getDepartment());
        existing.setDesignation(attendance.getDesignation());
        existing.setDepartment(attendance.getDepartment());
        existing.setRemarks(attendance.getRemarks());
        existing.setEntryTime(attendance.getEntryTime());
        Attendance updated = attendanceRepo.save(existing);
        ResponseStructure<Attendance> response = new ResponseStructure<>();
        response.setBody(updated);
        response.setMessage("Attendance updated successfully");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<String>> deleteAttendanceById(Long id, UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Optional<Attendance> optional = attendanceRepo.findByIdAndUser(id, user);
        if (optional.isEmpty()) {
            throw new NoTaskFoundException("Attendance ID not found: " + id);
        }
        attendanceRepo.delete(optional.get());
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setBody("Attendance deleted successfully with ID: " + id);
        response.setMessage("Success");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public List<Attendance> getAttendanceByEmployeeId(Long employeeId) {
        return attendanceRepo.findByUserEmployeeId(employeeId);
    }
}
