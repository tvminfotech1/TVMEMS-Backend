package com.tvm.internal.tvm_internal_project.service;


import com.tvm.internal.tvm_internal_project.model.WorkFromHome;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WFHService {



    Long getEmployeeIdByEmail(String loggedInEmail);

    List<WorkFromHome> getAllByMonthAndYear(int month, int year);
    ResponseEntity<ResponseStructure<WorkFromHome>> saveWFH(WorkFromHome WFH);

    ResponseEntity<ResponseStructure<WorkFromHome>> updateWFH(Long id, WorkFromHome updatedWFH);


    Long findEmployeeIdByEmail(String email);

    List<WorkFromHome> getByEmployeeAndMonthAndYear(Long employeeId, int month, int year);
}