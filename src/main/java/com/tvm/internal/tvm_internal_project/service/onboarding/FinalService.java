package com.tvm.internal.tvm_internal_project.service.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.Final;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FinalService {
    ResponseEntity<ResponseStructure<Final>> saveFinal(Final aFinal);

    ResponseEntity<ResponseStructure<Final>> getById(Integer id);

    ResponseEntity<ResponseStructure<Final>> updateFinal(Final aFinal, Integer id);

    ResponseEntity<ResponseStructure<List<Final>>> getFinalAll();

    ResponseEntity<ResponseStructure<String>> deleteFinal(Integer id);
    public boolean isOnboardingSubmitted(Integer employee_Id);

}
