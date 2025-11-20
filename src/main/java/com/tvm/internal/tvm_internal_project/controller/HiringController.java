package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.JobOpening;
import com.tvm.internal.tvm_internal_project.service.HiringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/hiring")
public class HiringController {

    @Autowired
    private HiringService hiringService;

    @PostMapping("/jobs")
    public ResponseEntity<JobOpening> createJob(@RequestBody JobOpening job) {
        return ResponseEntity.ok(hiringService.createJob(job));
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobOpening>> getAllJobs() {
        return ResponseEntity.ok(hiringService.getAllJobs());
    }

    @PutMapping("/jobs/{id}")
    public ResponseEntity<JobOpening> updateJob(@PathVariable Long id, @RequestBody JobOpening job) {
        return ResponseEntity.ok(hiringService.updateJob(id, job));
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        hiringService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

}
