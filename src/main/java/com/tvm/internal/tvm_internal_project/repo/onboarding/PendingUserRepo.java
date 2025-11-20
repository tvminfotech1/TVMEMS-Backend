package com.tvm.internal.tvm_internal_project.repo.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.PendingUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface PendingUserRepo extends JpaRepository<PendingUser, Long>{

    Optional<PendingUser> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM pending_user WHERE employee_id IN (SELECT employee_id FROM user) AND employee_id = :employeeId", nativeQuery = true)
    void deleteByEmpId(@Param("employeeId") Long employeeId);
}
