package com.tvm.internal.tvm_internal_project.controller.onboarding;


import com.tvm.internal.tvm_internal_project.model.onboarding.Passport;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.onboarding.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private PassportService passportService;

    @PostMapping
    public ResponseEntity<ResponseStructure<Passport>> savePassport(@RequestBody Passport passport) {
        return passportService.savePassport(passport);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Passport>> getById(@PathVariable Integer id) {
        return passportService.getById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseStructure<List<Passport>>> getAllPassports() {
        return passportService.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<Passport>> updatePassport(@PathVariable Integer id, @RequestBody Passport passport) {
        return passportService.updatePassport(passport, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deletePassport(@PathVariable Integer id) {
        return passportService.deletePassport(id);
    }
}

