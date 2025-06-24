package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.Application;
import com.tvm.internal.tvm_internal_project.model.Candidate;
import com.tvm.internal.tvm_internal_project.model.JobOpening;
import com.tvm.internal.tvm_internal_project.request.ApplicationRequest;

import java.util.List;

public interface HiringService {

    JobOpening createJob(JobOpening job);

    List<JobOpening> getAllJobs();

    JobOpening updateJob(Long id, JobOpening job);

    void deleteJob(Long id);

    Candidate createCandidate(Candidate candidate);

    List<Candidate> getAllCandidates();

    Candidate updateCandidate(Long id, Candidate candidate);

    void deleteCandidate(Long id);

    Application applyToJob(ApplicationRequest application);

    List<Application> getAllApplications();

    Application updateApplicationStatus(ApplicationRequest request);

    void deleteApplication(Long id);
}
