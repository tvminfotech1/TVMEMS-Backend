package com.tvm.internal.tvm_internal_project.service.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.Education;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EducationService {
    ResponseEntity<ResponseStructure<Education>> saveEducation(Education education);

    ResponseEntity<ResponseStructure<Education>> getbyid(Integer id);

    ResponseEntity<ResponseStructure<List<Education>>> getall();

    ResponseEntity<ResponseStructure<Education>> updatebyid(Integer id, Education education);

    ResponseEntity<ResponseStructure<String>> deletebyid(Integer id);

}
