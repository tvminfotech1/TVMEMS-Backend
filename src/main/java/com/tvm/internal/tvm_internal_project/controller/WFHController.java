package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.WorkFromHome;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.WFHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("WFH")
public class WFHController {

    @Autowired
    private WFHService WFHservice;

    @PostMapping
    public ResponseEntity<ResponseStructure<WorkFromHome>> createWFH(@RequestBody WorkFromHome WFH) {
        return WFHservice.saveWFH(WFH);
    }


    @GetMapping("/all")
    public ResponseEntity<ResponseStructure<List<WorkFromHome>>> getWorkFromHome() {
        return WFHservice.getAllWorkFromHome();
    }

}
