package com.tvm.internal.tvm_internal_project.repo;

import com.tvm.internal.tvm_internal_project.model.Task;
import com.tvm.internal.tvm_internal_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

    Optional<Task> findByIdAndUser(Long id, User user);

    List<Task> findAll();
}