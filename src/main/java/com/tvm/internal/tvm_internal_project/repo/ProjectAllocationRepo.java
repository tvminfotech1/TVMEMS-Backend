package com.tvm.internal.tvm_internal_project.repo;

import com.tvm.internal.tvm_internal_project.model.ProjectAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectAllocationRepo extends JpaRepository<ProjectAllocation,Integer> {
}
