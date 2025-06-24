package com.tvm.internal.tvm_internal_project.controller.onboarding;


import com.tvm.internal.tvm_internal_project.model.onboarding.KYC;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.onboarding.KYCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("kyc")
public class KYCController {

    @Autowired
    private KYCService kycService;

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


}
