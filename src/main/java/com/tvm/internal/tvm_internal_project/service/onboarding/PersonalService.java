package com.tvm.internal.tvm_internal_project.service.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.Personal;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.response.WishesDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PersonalService {
    ResponseEntity<ResponseStructure<Personal>> savePersonalInfo(Personal personal);

    ResponseEntity<ResponseStructure<List<Personal>>> findAllDetailsUsingName(String name);

    ResponseEntity<ResponseStructure<List<Personal>>> findAllDetailsUsingCity(String city);

    ResponseEntity<ResponseStructure<Personal>> findAllDetailsUsingPhone(Long contact);

    ResponseEntity<ResponseStructure<Personal>> findById(Integer id);

    ResponseEntity<ResponseStructure<Personal>> updatePersonal(Personal personal, Integer id);

    ResponseEntity<ResponseStructure<List<Personal>>> findAllPersonal();

    ResponseEntity<ResponseStructure<String>> deleteById(Integer id);

    List<WishesDto> wishesService();
}
