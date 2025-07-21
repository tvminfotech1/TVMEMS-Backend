package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.ProjectAllocation;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectAllocationService {
     ResponseEntity<ResponseStructure<ProjectAllocation>> saveProjectAllocation(ProjectAllocation  allocation);
     ResponseEntity<ResponseStructure<List<ProjectAllocation>>> findAllPersonal();
     ResponseEntity<ResponseStructure<String>> deleteById(Integer id);
     public ResponseEntity<ResponseStructure<ProjectAllocation>> updateProjectAllocation(ProjectAllocation allocation, Integer id);
}
