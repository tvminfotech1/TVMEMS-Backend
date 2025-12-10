package com.tvm.internal.tvm_internal_project.controller.onboarding;

import com.tvm.internal.tvm_internal_project.service.onboarding.FinalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/final")
public class FinalController {

    @Autowired
    private FinalService finalService;

    @GetMapping("/check-status/{userId}")
    public ResponseEntity<Boolean> checkOnboardingStatus(@PathVariable Integer userId) {
        boolean submitted = finalService.isOnboardingSubmitted(userId);
        return ResponseEntity.ok(submitted);
    }
}
