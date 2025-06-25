package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.exception.NoTaskFoundException;
import com.tvm.internal.tvm_internal_project.model.Task;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.repo.TaskRepo;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    private TaskRepo taskRepository;

    @Autowired
    private UserRepo userRepo;

    public ResponseEntity<ResponseStructure<Task>> createTask(Task task, UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        task.setUser(user);
        Task saved = taskRepository.save(task);
        ResponseStructure<Task> taskDto = new ResponseStructure<>();
        taskDto.setBody(saved);
        taskDto.setMessage("Saved Successfully");
        taskDto.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseStructure<List<Task>>> getTaskById(UserDetails userDetails) {
        String email = userDetails.getUsername();

        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Task> tasks = taskRepository.findByUser(user);
        ResponseStructure<List<Task>> response = new ResponseStructure<>();
        response.setBody(tasks);
        response.setMessage("Tasks fetched successfully");
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Task>> updateTask(Long taskId, Task taskDetails, UserDetails userDetails) throws NoTaskFoundException {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Task> optional = taskRepository.findByIdAndUser(taskId, user);
        if (optional.isEmpty()) {
            throw new NoTaskFoundException("Task Id Not Present or doesn't belong to this user");
        }
        Task task = optional.get();
        task.setTaskName(taskDetails.getTaskName());
        task.setTaskOwner(taskDetails.getTaskOwner());
        task.setDescription(taskDetails.getDescription());
        task.setPriority(taskDetails.getPriority());
        task.setAssignedDate(taskDetails.getAssignedDate());
        task.setDueDate(taskDetails.getDueDate());
        task.setStatus(taskDetails.getStatus());
        task.setReminder(taskDetails.getReminder());
        ResponseStructure<Task> taskDto = new ResponseStructure<>();
        taskDto.setBody(taskRepository.save(task));
        taskDto.setMessage("Task Updated Successfully");
        taskDto.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<String>> deleteTask(Long id, UserDetails userDetails) throws NoTaskFoundException {
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Task> optional = taskRepository.findByIdAndUser(id, user);
        if (optional.isEmpty()) {
            throw new NoTaskFoundException("Task Id Not Present or doesn't belong to this user");
        }
        taskRepository.deleteById(id);
        ResponseStructure<String> taskDto = new ResponseStructure<>();
        taskDto.setBody("Task Deleted Successfully");
        taskDto.setMessage("Sucess");
        taskDto.setStatusCode((HttpStatus.OK.value()));
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }

}
