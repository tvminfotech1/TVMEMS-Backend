package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.request.OffboardingRequestDTO;
import com.tvm.internal.tvm_internal_project.response.OffboardingResponseDTO;
import com.tvm.internal.tvm_internal_project.service.OffboardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/offboarding")
public class OffboardingController {

    @Autowired
    private OffboardingService service;

    @PostMapping
    public ResponseEntity<OffboardingResponseDTO> create(@RequestBody OffboardingRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OffboardingResponseDTO> update(@PathVariable Long id, @RequestBody OffboardingRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }


    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<OffboardingResponseDTO> getByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(service.getByEmployeeId(employeeId));
    }

    @GetMapping
    public ResponseEntity<List<OffboardingResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
