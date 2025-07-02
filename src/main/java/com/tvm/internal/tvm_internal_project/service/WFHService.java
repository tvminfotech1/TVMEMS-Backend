package com.tvm.internal.tvm_internal_project.service;


import com.tvm.internal.tvm_internal_project.model.WorkFromHome;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WFHService {

    ResponseEntity<ResponseStructure<WorkFromHome>> saveWFH(WorkFromHome WFH);


    ResponseEntity<ResponseStructure<List<WorkFromHome>>> getAllWorkFromHome();

}
