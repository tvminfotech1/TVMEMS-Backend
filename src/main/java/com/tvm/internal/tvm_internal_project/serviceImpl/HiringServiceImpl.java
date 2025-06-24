package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.model.Application;
import com.tvm.internal.tvm_internal_project.model.Candidate;
import com.tvm.internal.tvm_internal_project.model.JobOpening;
import com.tvm.internal.tvm_internal_project.model.Status;
import com.tvm.internal.tvm_internal_project.repo.ApplicationRepo;
import com.tvm.internal.tvm_internal_project.repo.CandidateRepo;
import com.tvm.internal.tvm_internal_project.repo.JobOpeningRepo;
import com.tvm.internal.tvm_internal_project.request.ApplicationRequest;
import com.tvm.internal.tvm_internal_project.service.HiringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HiringServiceImpl implements HiringService {

    @Autowired
    private JobOpeningRepo jobRepo;
    @Autowired
    private CandidateRepo candidateRepo;
    @Autowired
    private ApplicationRepo applicationRepo;

    public JobOpening createJob(JobOpening job) {
        return jobRepo.save(job);
    }

    public List<JobOpening> getAllJobs() {
        return jobRepo.findAll();
    }

    public JobOpening updateJob(Long id, JobOpening job) {
        JobOpening existing = jobRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        existing.setTitle(job.getTitle());
        existing.setQualifications(job.getQualifications());
        existing.setYearOfPassout(job.getYearOfPassout());
        existing.setExperience(job.getExperience());
        existing.setDescription(job.getDescription());
        existing.setSkills(job.getSkills());
        existing.setLocation(job.getLocation());
        existing.setStatus(job.getStatus());
        return jobRepo.save(existing);
    }

    public void deleteJob(Long id) {
        jobRepo.deleteById(id);
    }

    public Candidate createCandidate(Candidate candidate) {
        return candidateRepo.save(candidate);
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepo.findAll();
    }

    public Candidate updateCandidate(Long id, Candidate candidate) {
        Candidate existing = candidateRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
        existing.setName(candidate.getName());
        existing.setEmail(candidate.getEmail());
        existing.setPhone(candidate.getPhone());
        return candidateRepo.save(existing);
    }

    public void deleteCandidate(Long id) {
        candidateRepo.deleteById(id);
    }

    public Application applyToJob(ApplicationRequest request) {
        if (!Status.isValid(request.getStatus())) {
            throw new IllegalArgumentException("Invalid status: " + request.getStatus());
        }
        Candidate candidate = candidateRepo.findById(request.getCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        JobOpening job = jobRepo.findById(request.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Application application = new Application();
        application.setCandidate(candidate);
        application.setJob(job);
        application.setStatus(Status.valueOf(request.getStatus().toUpperCase()));
        application.setAppliedDate(LocalDate.now());
        return applicationRepo.save(application);
    }

    public List<Application> getAllApplications() {
        return applicationRepo.findAll();
    }

    public Application updateApplicationStatus(ApplicationRequest request) {
        if (!Status.isValid(request.getStatus())) {
            throw new IllegalArgumentException("Invalid status: " + request.getStatus());
        }
        Application app = applicationRepo.findByCandidateIdAndJobId(request.getCandidateId(), request.getJobId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        Candidate candidate = candidateRepo.findById(app.getCandidate().getId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        JobOpening job = jobRepo.findById(app.getJob().getId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        app.setCandidate(candidate);
        app.setJob(job);
        app.setStatus(Status.valueOf(request.getStatus().toUpperCase()));
        return applicationRepo.save(app);
    }

    public void deleteApplication(Long id) {
        applicationRepo.deleteById(id);
    }
}
