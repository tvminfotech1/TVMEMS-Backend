package com.tvm.internal.tvm_internal_project.repo;

import com.tvm.internal.tvm_internal_project.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepo extends JpaRepository<UserDetail, Integer> {

    Optional<UserDetail> findByEmail(String email);

    Optional<UserDetail> findByMobile(Long mob);

    Optional<UserDetail> findByEmailOrMobile(String email, Long mobile);

}
