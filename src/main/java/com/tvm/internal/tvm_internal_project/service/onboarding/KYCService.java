package com.tvm.internal.tvm_internal_project.service.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.KYC;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KYCService {
    ResponseEntity<ResponseStructure<KYC>> saveKyc(KYC kyc);

    ResponseEntity<ResponseStructure<KYC>> getById(Integer id);

    ResponseEntity<ResponseStructure<KYC>> updateKyc(KYC kyc, Integer id);

    ResponseEntity<ResponseStructure<List<KYC>>> getAllKyc();

    ResponseEntity<ResponseStructure<String>> deleteKyc(Integer id);

}
