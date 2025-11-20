package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.JobOpening;
import java.util.List;

public interface HiringService {

    JobOpening createJob(JobOpening job);

    List<JobOpening> getAllJobs();

    JobOpening updateJob(Long id, JobOpening job);

    void deleteJob(Long id);
}
