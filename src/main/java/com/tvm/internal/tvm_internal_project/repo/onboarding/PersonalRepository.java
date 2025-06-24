package com.tvm.internal.tvm_internal_project.repo.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.Personal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Integer> {

    List<Personal> findByFname(String name);

    List<Personal> findByPermanentCity(String city);

    Optional<Personal> findByPermanentContact(Long contact);
}
