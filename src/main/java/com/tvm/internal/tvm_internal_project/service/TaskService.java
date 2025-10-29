package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.Task;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface TaskService {
    ResponseEntity<ResponseStructure<Task>> createTask(Task task, UserDetails userDetails);

    ResponseEntity<ResponseStructure<List<Task>>> getTaskById(UserDetails userDetails);

    ResponseEntity<ResponseStructure<Task>> updateTask(Long id, Task taskDetails, UserDetails userDetails);

//    ResponseEntity<ResponseStructure<String>> deleteTask(Long id, UserDetails userDetails);

    ResponseEntity<ResponseStructure<List<Task>>> getAlltask();

    void deleteTask(Long id);
}