package com.tvm.internal.tvm_internal_project.controller;


import com.tvm.internal.tvm_internal_project.model.Task;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/task")
    public ResponseEntity<ResponseStructure<Task>> createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping("/task")
    public ResponseEntity<ResponseStructure<List<Task>>> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<ResponseStructure<Task>> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<ResponseStructure<Task>> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }


}
