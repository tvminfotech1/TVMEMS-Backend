package com.tvm.internal.tvm_internal_project.repo;

import com.tvm.internal.tvm_internal_project.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByEmailAndPassword(String email, String password);

    Optional<Employee> findByMobileAndPassword(Long mobile, String password);

//    Optional<Employee> findByEmployeeRepo(String employeeRepo);

//    @Query("SELECT e FROM Employee e WHERE e.status = 1")
//    List<Employee> findBySuccessStatus(int i);
//
//    @Query("SELECT e FROM Employee e WHERE e.status = 0")
//    List<Employee> findByFailedStatus(int i);
}
