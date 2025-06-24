package com.tvm.internal.tvm_internal_project.service.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.Skills;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SkillsService {
    ResponseEntity<ResponseStructure<Skills>> saveSkills(Skills skills);

    ResponseEntity<ResponseStructure<Skills>> getbyid(Integer id);

    ResponseEntity<ResponseStructure<List<Skills>>> getall();

    ResponseEntity<ResponseStructure<Skills>> Updatebyid(Integer id, Skills skills);

    ResponseEntity<ResponseStructure<String>> deletebyid(Integer id);

}
