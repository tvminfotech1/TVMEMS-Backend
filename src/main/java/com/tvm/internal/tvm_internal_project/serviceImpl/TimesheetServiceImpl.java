package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.exception.TimeSheetNotFoundException;
import com.tvm.internal.tvm_internal_project.model.ChartData;
import com.tvm.internal.tvm_internal_project.model.Hours;
import com.tvm.internal.tvm_internal_project.model.Timesheet;
import com.tvm.internal.tvm_internal_project.model.WorkMode;
import com.tvm.internal.tvm_internal_project.repo.TimesheetRepository;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

@Service
public class TimesheetServiceImpl implements TimesheetService {


    @Autowired
    private TimesheetRepository timesheetRepository;

    public ResponseEntity<ResponseStructure<List<Timesheet>>> getAllTimesheets() {
        List<Timesheet> timesheets = timesheetRepository.findAll();
        ResponseStructure<List<Timesheet>> timesheetDto = new ResponseStructure<>();
        timesheetDto.setBody(timesheets);
        timesheetDto.setMessage("List of Timesheet");
        timesheetDto.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(timesheetDto, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseStructure<Timesheet>> getTimesheetById(Long id) {
        Optional<Timesheet> optional = timesheetRepository.findById(id);
        if (optional.isEmpty()) {
            throw new TimeSheetNotFoundException("TImesheet Id is Not Found");
        }
        ResponseStructure<Timesheet> timesheetDto = new ResponseStructure<>();
        timesheetDto.setBody(optional.get());
        timesheetDto.setMessage("TimeSheet Fetch Successfully");
        timesheetDto.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(timesheetDto, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Timesheet>> createTimesheet(Timesheet timesheet) {
        Timesheet saved = timesheetRepository.save(timesheet);
        ResponseStructure<Timesheet> timesheetDto = new ResponseStructure<>();
        timesheetDto.setBody(saved);
        timesheetDto.setMessage("Timesheet Saved Successfully");
        timesheetDto.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(timesheetDto, HttpStatus.OK);

    }

    public ResponseEntity<ResponseStructure<Timesheet>> updateTimesheet(Long id, Timesheet timesheetDetails) {
        Optional<Timesheet> optional = timesheetRepository.findById(id);
        if (optional.isEmpty()) {
            throw new TimeSheetNotFoundException("Timesheet Id is Not Found");
        }
        Timesheet timesheet = optional.get();
        timesheet.setDescription(timesheetDetails.getDescription());
        timesheet.setProject(timesheetDetails.getProject());
        timesheet.setWeekendDate(timesheetDetails.getWeekendDate());
        timesheet.setWfol(timesheetDetails.isWfol());
        timesheet.setHours(timesheetDetails.getHours());
        ResponseStructure<Timesheet> timesheetDto = new ResponseStructure<>();
        timesheetDto.setBody(timesheetRepository.save(timesheet));
        timesheetDto.setMessage("Timesheet is Updated Successfully");
        timesheetDto.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(timesheetDto, HttpStatus.OK);
    }

    public ResponseEntity<ChartData> getWorkHours(Long id) {
        Timesheet timesheet = timesheetRepository.findById(id).orElseThrow(() -> new TimeSheetNotFoundException("Workhours Not Found for ID: " + id));
        Hours hours = timesheet.getHours();
        List<WorkMode> weekModes = List.of(hours.getMonday(), hours.getTuesday(), hours.getWednesday(), hours.getThursday(), hours.getFriday());
        Map<String, Integer> pieCountMap = new LinkedHashMap<>();
        for (WorkMode mode : WorkMode.values()) {
            pieCountMap.put(mode.toLabel(), 0);
        }
        for (WorkMode mode : weekModes) {
            String label = mode.toLabel();
            pieCountMap.put(label, pieCountMap.get(label) + 1);
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

    public ResponseEntity<ResponseStructure<String>> deleteTimesheet(Long id) {
        Optional<Timesheet> optional = timesheetRepository.findById(id);
        if (optional.isEmpty()) {
            throw new TimeSheetNotFoundException("TimeSheet Id Is Not Found");
        }
        ResponseStructure<String> timesheetDto = new ResponseStructure<>();
        timesheetDto.setBody("TimeSheet Deleted Successfully");
        timesheetDto.setMessage("Sucess");
        timesheetDto.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(timesheetDto, HttpStatus.OK);
    }
}



