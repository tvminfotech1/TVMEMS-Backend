package com.tvm.internal.tvm_internal_project.repo;

import com.tvm.internal.tvm_internal_project.model.Offboarding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OffboardingRepo extends JpaRepository<Offboarding, Long> {
    Optional<Offboarding> findByEmployeeId(Long employeeId);
}
