package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.Application;
import com.tvm.internal.tvm_internal_project.model.Candidate;
import com.tvm.internal.tvm_internal_project.model.JobOpening;
import com.tvm.internal.tvm_internal_project.request.ApplicationRequest;
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

    @PostMapping("/candidates")
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) {
        return ResponseEntity.ok(hiringService.createCandidate(candidate));
    }

    @GetMapping("/candidates")
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        return ResponseEntity.ok(hiringService.getAllCandidates());
    }

    @PutMapping("/candidates/{id}")
    public ResponseEntity<Candidate> updateCandidate(@PathVariable Long id, @RequestBody Candidate candidate) {
        return ResponseEntity.ok(hiringService.updateCandidate(id, candidate));
    }

    @DeleteMapping("/candidates/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        hiringService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/applications")
    public ResponseEntity<Application> applyToJob(@RequestBody ApplicationRequest request) {
        Application application = hiringService.applyToJob(request);
        return ResponseEntity.ok(application);
    }

    @GetMapping("/applications")
    public ResponseEntity<List<Application>> getAllApplications() {
        return ResponseEntity.ok(hiringService.getAllApplications());
    }

    @PutMapping("/applications/{id}/status")
    public ResponseEntity<Application> updateApplicationStatus(@RequestBody ApplicationRequest request) {
        Application updated = hiringService.updateApplicationStatus(request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/applications/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        hiringService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
