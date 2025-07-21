package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.ProjectAllocation;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.ProjectAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectAllocation")
public class ProjectAllocationController {

    @Autowired
    private ProjectAllocationService allocationService;

    @PostMapping
    public ResponseEntity<ResponseStructure<ProjectAllocation>> saveProjectAllocation(@RequestBody ProjectAllocation allocation) {
        return allocationService.saveProjectAllocation(allocation);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<ProjectAllocation>>> findAllPersonal() {
        return allocationService.findAllPersonal();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseStructure<String>> deleteById(@PathVariable Integer id) {
        return allocationService.deleteById(id);
    }
    @PutMapping("{id}")
    public ResponseEntity<ResponseStructure<ProjectAllocation>> updateProjectAllocation(@RequestBody ProjectAllocation allocation, @PathVariable Integer id) {
        return allocationService.updateProjectAllocation(allocation, id);
    }
}
