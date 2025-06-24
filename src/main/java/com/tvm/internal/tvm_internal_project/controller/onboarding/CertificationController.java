package com.tvm.internal.tvm_internal_project.controller.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.Certification;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.onboarding.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("certification")
public class CertificationController {
    @Autowired
    private CertificationService certificationService;

    @PostMapping
    public ResponseEntity<ResponseStructure<Certification>> saveCertification(@RequestBody Certification certification) {
        return certificationService.saveCertification(certification);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Certification>> getbyid(@PathVariable Integer id) {
        return certificationService.getById(id);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Certification>>> getall() {
        return certificationService.getall();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<Certification>> updatebyid(@PathVariable Integer id, @RequestBody Certification certification) {
        return certificationService.updateAll(id, certification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> delete(@PathVariable Integer id) {
        return certificationService.deletebyid(id);
    }

}
