package com.tvm.internal.tvm_internal_project.service.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.Certification;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CertificationService {

    ResponseEntity<ResponseStructure<Certification>> saveCertification(Certification certification);

    ResponseEntity<ResponseStructure<Certification>> getById(Integer id);

    ResponseEntity<ResponseStructure<List<Certification>>> getall();

    ResponseEntity<ResponseStructure<Certification>> updateAll(Integer id, Certification certification);

    ResponseEntity<ResponseStructure<String>> deletebyid(Integer id);

}
