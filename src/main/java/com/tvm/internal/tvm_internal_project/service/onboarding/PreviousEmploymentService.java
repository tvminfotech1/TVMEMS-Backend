package com.tvm.internal.tvm_internal_project.service.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.PreviousEmployment;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PreviousEmploymentService {
    ResponseEntity<ResponseStructure<PreviousEmployment>> saveEmployment(PreviousEmployment previousEmployment);

    ResponseEntity<ResponseStructure<PreviousEmployment>> findById(Integer id);

    ResponseEntity<ResponseStructure<PreviousEmployment>> updateResume(PreviousEmployment previousEmployment, Integer id);

    ResponseEntity<ResponseStructure<List<PreviousEmployment>>> findAllResume();

    ResponseEntity<ResponseStructure<String>> deleteById(Integer id);

}
