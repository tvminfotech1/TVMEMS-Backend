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

    @GetMapping("/{id}")
    public ResponseEntity<OffboardingResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<OffboardingResponseDTO> getByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(service.getByEmployeeId(employeeId));
    }

    @GetMapping
    public ResponseEntity<List<OffboardingResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
