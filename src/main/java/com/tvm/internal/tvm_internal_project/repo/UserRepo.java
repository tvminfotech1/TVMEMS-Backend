package com.tvm.internal.tvm_internal_project.repo;

import com.tvm.internal.tvm_internal_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByMobile(Long mob);

    Optional<User> findByEmailOrMobile(String email, Long mobile);

    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Long findIdByEmail(@Param("email") String email);

    @Query("SELECT u.fullName FROM User u WHERE u.email = :email")
    String findNameByEmail(@Param("email") String email);

    Optional<User> findByEmployeeId(long employeeId);
}