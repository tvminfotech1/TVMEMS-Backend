package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.Task;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {
    ResponseEntity<ResponseStructure<Task>> createTask(Task task);

    ResponseEntity<ResponseStructure<List<Task>>> getAllTasks();

    ResponseEntity<ResponseStructure<Task>> getTaskById(Long id);

    ResponseEntity<ResponseStructure<Task>> updateTask(Long id, Task taskDetails);

    ResponseEntity<ResponseStructure<String>> deleteTask(Long id);

}

