package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.model.JobOpening;
import com.tvm.internal.tvm_internal_project.repo.JobOpeningRepo;
import com.tvm.internal.tvm_internal_project.service.HiringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HiringServiceImpl implements HiringService {

    @Autowired
    private JobOpeningRepo jobRepo;

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

}
