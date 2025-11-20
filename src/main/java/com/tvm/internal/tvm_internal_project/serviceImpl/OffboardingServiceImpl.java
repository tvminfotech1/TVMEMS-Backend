package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.model.Offboarding;
import com.tvm.internal.tvm_internal_project.repo.OffboardingRepo;
import com.tvm.internal.tvm_internal_project.request.OffboardingRequestDTO;
import com.tvm.internal.tvm_internal_project.response.OffboardingResponseDTO;
import com.tvm.internal.tvm_internal_project.service.OffboardingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OffboardingServiceImpl implements OffboardingService {

    @Autowired
    private OffboardingRepo offboardingRepo;

    public OffboardingResponseDTO create(OffboardingRequestDTO dto) {
        Offboarding entity = mapToEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        offboardingRepo.save(entity);
        return mapToDto(entity);
    }

    public OffboardingResponseDTO update(Long id, OffboardingRequestDTO dto) {
        Offboarding entity = offboardingRepo.findById(id).orElseThrow(() -> new RuntimeException("Offboarding not found"));
        updateEntityFromDto(entity, dto);
        entity.setUpdatedAt(LocalDateTime.now());
        offboardingRepo.save(entity);
        return mapToDto(entity);
    }

    public OffboardingResponseDTO getByEmployeeId(String employeeId) {
        return offboardingRepo.findByEmployeeId(employeeId).map(this::mapToDto).orElseThrow(() -> new RuntimeException("Offboarding not found"));
    }

    public List<OffboardingResponseDTO> getAll() {
        return offboardingRepo.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private Offboarding mapToEntity(OffboardingRequestDTO dto) {
        Offboarding e = new Offboarding();
        updateEntityFromDto(e, dto);
        return e;
    }

    private void updateEntityFromDto(Offboarding e, OffboardingRequestDTO dto) {
        e.setEmployeeId(dto.getEmployeeId());
        e.setName(dto.getName());
        e.setReason(dto.getReason());
        e.setExplanation(dto.getExplanation());
        e.setDate(dto.getDate());
        e.setStatus(dto.getStatus());
        e.setAcknowledge(dto.getAcknowledge());
    }

    private OffboardingResponseDTO mapToDto(Offboarding e) {
        OffboardingResponseDTO dto = new OffboardingResponseDTO();
        BeanUtils.copyProperties(e, dto);
        return dto;
    }
}
