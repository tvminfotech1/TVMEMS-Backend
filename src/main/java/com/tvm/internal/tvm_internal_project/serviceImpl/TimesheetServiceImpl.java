package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.DTO.TimesheetDTO;
import com.tvm.internal.tvm_internal_project.exception.ResourceNotFoundException;
import com.tvm.internal.tvm_internal_project.exception.TimeSheetNotFoundException;
import com.tvm.internal.tvm_internal_project.model.ChartData;
import com.tvm.internal.tvm_internal_project.model.Hours;
import com.tvm.internal.tvm_internal_project.model.Timesheet;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.WorkMode;
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
    public ResponseEntity<ResponseStructure<Timesheet>> getTimesheetById(Long id, UserDetails userDetails) {
        User user = getUserFromDetails(userDetails);
        Timesheet timesheet = timesheetRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TimeSheetNotFoundException("Timesheet not found for this user"));
        ResponseStructure<Timesheet> response = new ResponseStructure<>();
        response.setBody(timesheet);
        response.setMessage("Timesheet fetched successfully");
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

    @Override
    public ResponseEntity<ResponseStructure<Timesheet>> updateTimesheet(Long id, Timesheet newData, UserDetails userDetails) {
        User user = getUserFromDetails(userDetails);
        Timesheet existing = timesheetRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TimeSheetNotFoundException("Timesheet not found for this user"));
        existing.setProject(newData.getProject());
        existing.setDescription(newData.getDescription());
        existing.setWeekendDate(newData.getWeekendDate());
        existing.setHours(newData.getHours());
        Timesheet updated = timesheetRepository.save(existing);
        ResponseStructure<Timesheet> response = new ResponseStructure<>();
        response.setBody(updated);
        response.setMessage("Timesheet updated successfully");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<String>> deleteTimesheet(Long id, UserDetails userDetails) {
        User user = getUserFromDetails(userDetails);
        Timesheet timesheet = timesheetRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TimeSheetNotFoundException("Timesheet not found for this user"));
        timesheetRepository.delete(timesheet);
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setBody("Timesheet deleted successfully");
        response.setMessage("Success");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ChartData> getWorkHours(UserDetails userDetails) {
        User user = getUserFromDetails(userDetails);
        Timesheet timesheet = timesheetRepository.findTopByUserOrderByWeekendDateDesc(user)
                .orElseThrow(() -> new TimeSheetNotFoundException("No Timesheet found for this user"));
        Hours hours = timesheet.getHours();
        List<WorkMode> weekModes = List.of(hours.getMonday(), hours.getTuesday(), hours.getWednesday(),
                hours.getThursday(), hours.getFriday());
        Map<String, Integer> pieCountMap = new LinkedHashMap<>();
        for (WorkMode mode : WorkMode.values()) {
            pieCountMap.put(mode.toLabel(), 0);
        }
        for (WorkMode mode : weekModes) {
            pieCountMap.put(mode.toLabel(), pieCountMap.get(mode.toLabel()) + 1);
        }
        Map<String, Object> pieData = new LinkedHashMap<>();
        pieData.put("labels", new ArrayList<>(pieCountMap.keySet()));
        pieData.put("values", new ArrayList<>(pieCountMap.values()));
        List<String> barLabels = new ArrayList<>();
        List<Integer> barValues = new ArrayList<>();
        for (WorkMode mode : weekModes) {
            barLabels.add(mode.toLabel());
            barValues.add(mode.toWorkingHours());
        }
        Map<String, Object> barData = new LinkedHashMap<>();
        barData.put("labels", barLabels);
        barData.put("values", barValues);
        ChartData chartData = new ChartData();
        chartData.setPieData(pieData);
        chartData.setBarData(barData);
        return ResponseEntity.ok(chartData);
    }

    private User getUserFromDetails(UserDetails userDetails) {
        return userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new TimeSheetNotFoundException("User not found"));
    }
}
