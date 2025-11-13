package com.tvm.internal.tvm_internal_project.repo.onboarding;

import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.onboarding.Final;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinalRepository extends JpaRepository<Final,Integer> {
    Optional<Final> findByUser(User user);
    boolean existsByUser_EmployeeId(Integer employee_Id);
}
