package com.tvm.internal.tvm_internal_project.controller.onboarding;


import com.tvm.internal.tvm_internal_project.model.onboarding.Family;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.onboarding.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("family")
public class FamilyController {
    @Autowired
    private FamilyService familyService;

    @PostMapping
    public ResponseEntity<ResponseStructure<Family>> saveFamily(@RequestBody Family family) {
        return familyService.saveFamily(family);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Family>> getbyid(@PathVariable Long id) {
        return familyService.getbyid(id);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Family>>> getall() {
        return familyService.getall();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<Family>> update(@PathVariable Long id, @RequestBody Family family) {
        return familyService.updateall(id, family);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deletebyid(@PathVariable Long id) {
        return familyService.deletebyid(id);
    }
}
