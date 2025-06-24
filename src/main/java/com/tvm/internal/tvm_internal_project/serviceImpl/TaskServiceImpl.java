package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.exception.NoTaskFoundException;
import com.tvm.internal.tvm_internal_project.model.Task;
import com.tvm.internal.tvm_internal_project.repo.TaskRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    private TaskRepo taskRepository;

    public ResponseEntity<ResponseStructure<Task>> createTask(Task task) {
        Task saved = taskRepository.save(task);
        ResponseStructure<Task> taskDto = new ResponseStructure<>();
        taskDto.setBody(saved);
        taskDto.setMessage("Saved Sucessfully");
        taskDto.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseStructure<List<Task>>> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        ResponseStructure<List<Task>> taskDto = new ResponseStructure<>();
        taskDto.setBody(tasks);
        taskDto.setMessage("List of Task");
        taskDto.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Task>> getTaskById(Long id) {
        Optional<Task> optional = taskRepository.findById(id);
        if (optional.isEmpty()) {
            throw new NoTaskFoundException("Task Id is Not Found");
        }
        ResponseStructure<Task> taskDto = new ResponseStructure<>();
        taskDto.setBody(optional.get());
        taskDto.setMessage("Task Fetch Sucessfully");
        taskDto.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Task>> updateTask(Long id, Task taskDetails) throws NoTaskFoundException {
        Optional<Task> optional = taskRepository.findById(id);
        if (optional.isEmpty()) {
            throw new NoTaskFoundException("Task Id Not Present");
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

    public ResponseEntity<ResponseStructure<String>> deleteTask(Long id) throws NoTaskFoundException {
        Optional<Task> optional = taskRepository.findById(id);
        if (optional.isEmpty()) {
            throw new NoTaskFoundException("Id Not Present in the Database");
        }
        taskRepository.deleteById(id);
        ResponseStructure<String> taskDto = new ResponseStructure<>();
        taskDto.setBody("Task Deleted Successfully");
        taskDto.setMessage("Sucess");
        taskDto.setStatusCode((HttpStatus.OK.value()));
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }

}
