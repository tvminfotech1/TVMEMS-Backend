package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.request.OffboardingRequestDTO;
import com.tvm.internal.tvm_internal_project.response.OffboardingResponseDTO;

import java.util.List;

public interface OffboardingService {
    OffboardingResponseDTO create(OffboardingRequestDTO dto);

    OffboardingResponseDTO update(Long id, OffboardingRequestDTO dto);

    OffboardingResponseDTO getById(Long id);

    OffboardingResponseDTO getByEmployeeId(String employeeId);

    List<OffboardingResponseDTO> getAll();

    void delete(Long id);
}
