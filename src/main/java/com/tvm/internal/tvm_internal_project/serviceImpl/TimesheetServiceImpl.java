package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.DTO.TimesheetDTO;
import com.tvm.internal.tvm_internal_project.exception.ResourceNotFoundException;
import com.tvm.internal.tvm_internal_project.exception.TimeSheetNotFoundException;
import com.tvm.internal.tvm_internal_project.model.Timesheet;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.repo.TimesheetRepository;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TimesheetServiceImpl implements TimesheetService {

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    private UserRepo userRepo;

    public ResponseEntity<ResponseStructure<List<TimesheetDTO>>> getAllTimesheetsForAdmin() {
        List<TimesheetDTO> dtoList = timesheetRepository.findAll().stream().map(ts -> {
            TimesheetDTO dto = new TimesheetDTO();
            dto.setId(ts.getId());
            dto.setProject(ts.getProject());
            dto.setHours(ts.getHours());
            dto.setTotalhours(ts.getTotalhours());
            dto.setDescription(ts.getDescription());
            dto.setWeekendDate(ts.getWeekendDate());
            dto.setEmployeeId(ts.getUser().getEmployeeId());
            dto.setEmployeeName(ts.getUser().getFullName());
            dto.setStatus(ts.getStatus());
            return dto;
        }).toList();
        ResponseStructure<List<TimesheetDTO>> response = new ResponseStructure<>();
        response.setBody(dtoList);
        response.setMessage("List of all timesheets for Admin");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Timesheet>> updateTimesheetStatus(Long id, String status) {
        Timesheet timesheet = timesheetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Timesheet not found"));
        timesheet.setStatus(status.toUpperCase());
        Timesheet updated = timesheetRepository.save(timesheet);
        ResponseStructure<Timesheet> response = new ResponseStructure<>();
        response.setMessage("Timesheet status updated successfully");
        response.setStatusCode(HttpStatus.OK.value());
        response.setBody(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<Timesheet>>> getAllTimesheets(UserDetails userDetails) {
        User user = getUserFromDetails(userDetails);
        List<Timesheet> timesheets = timesheetRepository.findByUser(user);
        ResponseStructure<List<Timesheet>> response = new ResponseStructure<>();
        response.setBody(timesheets);
        response.setMessage("List of Timesheet for User");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<Timesheet>> createTimesheet(Timesheet timesheet, UserDetails userDetails) {
        User user = getUserFromDetails(userDetails);
        timesheet.setUser(user);
        Timesheet saved = timesheetRepository.save(timesheet);
        ResponseStructure<Timesheet> response = new ResponseStructure<>();
        response.setBody(saved);
        response.setMessage("Timesheet created successfully");
        response.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private User getUserFromDetails(UserDetails userDetails) {
        return userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new TimeSheetNotFoundException("User not found"));
    }
}
