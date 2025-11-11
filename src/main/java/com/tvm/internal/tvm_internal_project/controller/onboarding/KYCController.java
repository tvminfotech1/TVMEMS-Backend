package com.tvm.internal.tvm_internal_project.controller.onboarding;


import com.tvm.internal.tvm_internal_project.model.onboarding.KYC;
import com.tvm.internal.tvm_internal_project.repo.onboarding.KYCRepository;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.response.UserKycDto;
import com.tvm.internal.tvm_internal_project.response.UserPayroleDto;
import com.tvm.internal.tvm_internal_project.service.onboarding.KYCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("kyc")
public class KYCController {

    @Autowired
    private KYCService kycService;

    @Autowired
    private KYCRepository kycRepo;

    @PostMapping
    public ResponseEntity<ResponseStructure<KYC>> saveKYC(@RequestBody KYC kyc) {
        return kycService.saveKyc(kyc);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<KYC>> getById(@PathVariable Integer id) {
        return kycService.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<KYC>> updateKyc(@PathVariable Integer id, @RequestBody KYC kyc) {
        return kycService.updateKyc(kyc, id);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<KYC>>> getAll() {
        return kycService.getAllKyc();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteKyc(@PathVariable Integer id) {
        return kycService.deleteKyc(id);
    }


    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ResponseStructure<UserKycDto>> getByEmployeeId(@PathVariable Long employeeId) {
        ResponseStructure<UserKycDto> response = new ResponseStructure<>();
        try {
            KYC kyc = (KYC) kycRepo.findByUserEmployeeId(employeeId)
                    .orElseThrow(() -> new RuntimeException("KYC not found for employee ID: " + employeeId));

            UserKycDto dto = new UserKycDto();
            dto.setKyc(kyc);
            dto.setUser(kyc.getUser());

            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("KYC fetched successfully");
            response.setBody(dto);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }




}