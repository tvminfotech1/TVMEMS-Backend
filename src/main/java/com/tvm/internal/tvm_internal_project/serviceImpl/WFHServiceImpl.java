package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.exception.AttendanceNotFound;
import com.tvm.internal.tvm_internal_project.model.WorkFromHome;
import com.tvm.internal.tvm_internal_project.repo.WFHRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.WFHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WFHServiceImpl implements WFHService {

    @Autowired
    private WFHRepo WFHrepo;

    public ResponseEntity<ResponseStructure<WorkFromHome>> saveWFH(WorkFromHome workFromHome) {

        WorkFromHome created = WFHrepo.save(workFromHome);

        ResponseStructure<WorkFromHome> response = new ResponseStructure<>();
        response.setBody(created);
        response.setMessage("WFH Created Successfully");
        response.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<WorkFromHome>>> getAllWorkFromHome() {
        List<WorkFromHome> WFH = WFHrepo.findAll();
        if (WFH.isEmpty()) {
            throw new AttendanceNotFound("WFH  ID not found: ");
        }

        ResponseStructure<List<WorkFromHome>> response = new ResponseStructure<>();
        response.setBody(WFH);
        response.setMessage("WFH  saved successfully");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
