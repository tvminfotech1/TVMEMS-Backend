package com.tvm.internal.tvm_internal_project.service.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.Resume;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ResumeService {
    ResponseEntity<ResponseStructure<Resume>> saveResume(Resume resume);

    ResponseEntity<ResponseStructure<Resume>> getById(Integer id);

    ResponseEntity<ResponseStructure<List<Resume>>> getAllResume();

    ResponseEntity<ResponseStructure<Resume>> updateResume(Resume resume, Integer id);

    ResponseEntity<ResponseStructure<String>> deleteById(Integer id);

}
