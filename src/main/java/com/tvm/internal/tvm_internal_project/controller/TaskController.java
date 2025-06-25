package com.tvm.internal.tvm_internal_project.controller;


import com.tvm.internal.tvm_internal_project.model.Task;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/task")
    public ResponseEntity<ResponseStructure<Task>> createTask(@RequestBody Task task,@AuthenticationPrincipal UserDetails userDetails) {
        return taskService.createTask(task,userDetails);
    }
    @GetMapping("/task")
    public ResponseEntity<ResponseStructure<List<Task>>> getTaskById(@AuthenticationPrincipal UserDetails userDetails) {
        return taskService.getTaskById(userDetails);
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<ResponseStructure<Task>> updateTask(@PathVariable Long id, @RequestBody Task task, @AuthenticationPrincipal UserDetails userDetails) {
        return taskService.updateTask(id, task, userDetails);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteTask(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return taskService.deleteTask(id, userDetails);
    }


}
