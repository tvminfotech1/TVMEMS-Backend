package com.tvm.internal.tvm_internal_project.service.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.Family;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FamilyService {
    ResponseEntity<ResponseStructure<Family>> saveFamily(Family family);

    ResponseEntity<ResponseStructure<Family>> getbyid(Long id);

    ResponseEntity<ResponseStructure<List<Family>>> getall();

    ResponseEntity<ResponseStructure<Family>> updateall(Long id, Family family);

    ResponseEntity<ResponseStructure<String>> deletebyid(Long id);
}
