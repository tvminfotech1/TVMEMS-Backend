package com.tvm.internal.tvm_internal_project.service.onboarding;

import com.fasterxml.jackson.databind.JsonNode;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.onboarding.Personal;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.DTO.WishesDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface PersonalService {



    ResponseEntity<ResponseStructure<Personal>> findById(Integer id);


    ResponseEntity<ResponseStructure<String>> deleteById(Integer id);

    Map<String,List<WishesDto>> prepareWishes();


    void processOnboardingDataWithUser(Map<String, JsonNode> parsedSections, User user);
}
