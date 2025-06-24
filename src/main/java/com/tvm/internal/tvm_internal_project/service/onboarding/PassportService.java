package com.tvm.internal.tvm_internal_project.service.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.Passport;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PassportService {
    ResponseEntity<ResponseStructure<Passport>> savePassport(Passport passport);

    ResponseEntity<ResponseStructure<Passport>> getById(Integer id);

    ResponseEntity<ResponseStructure<List<Passport>>> getAll();

    ResponseEntity<ResponseStructure<Passport>> updatePassport(Passport passport, Integer id);

    ResponseEntity<ResponseStructure<String>> deletePassport(Integer id);

}
