package com.tvm.internal.tvm_internal_project.repo.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents,Integer> {

    Optional<Documents> findByUserEmployeeId(Long employeeId);

}
