package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.exception.ResourceNotFound;
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
import org.springframework.stereotype.Service;
import java.util.List;

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
                .orElseThrow(() -> new ResourceNotFound("User not found"));
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
        User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFound("User not found"));
        List<Attendance> lists = attendanceRepo.findAll();
        if (lists.isEmpty()) {
            throw new ResourceNotFound("Attendance not found");
        }
        ResponseStructure<List<Attendance>> response = new ResponseStructure<>();
        response.setBody(lists);
        response.setMessage("Get Attendance successfully");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public List<Attendance> getAttendanceByEmployeeId(Long employeeId) {
        if (!userRepo.existsById(employeeId)) {
            throw new ResourceNotFound("Wrong Employee ID: " + employeeId);
        }
        List<Attendance> list = attendanceRepo.findByUserEmployeeId(employeeId);
        if (list.isEmpty()) {
            throw new ResourceNotFound("No attendance history found for employeeId: " + employeeId);
        }
        return list;
    }
}
