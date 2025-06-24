package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.exception.EmployeeNotFoundException;
import com.tvm.internal.tvm_internal_project.model.Employee;
import com.tvm.internal.tvm_internal_project.repo.EmployeeRepo;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;

//    To send the email to user while saving the user

    public ResponseEntity<ResponseStructure<Employee>> addUser(Employee employee) {
        ResponseStructure<Employee> structure = new ResponseStructure<>();

        structure.setMessage("User Registered Successfully!!");
        structure.setBody(employeeRepo.save(employee));
        structure.setStatusCode(HttpStatus.CREATED.value());

        return new ResponseEntity<>(structure, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseStructure<Employee>> loginByEmail(String email, String password) {
        Employee user = employeeRepo.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new EmployeeNotFoundException("Invalid email or password"));

        ResponseStructure<Employee> structure = new ResponseStructure<>();
        structure.setMessage("Login Successful");
        structure.setBody(user);
        structure.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Employee>> loginByPhone(Long mobile, String password) {
        ResponseStructure<Employee> structure = new ResponseStructure<>();
        Optional<Employee> users = employeeRepo.findByMobileAndPassword(mobile, password);
        if (users.isEmpty()) {
            throw new EmployeeNotFoundException("Invalid Phone No Or Password..!!!");
        }
        structure.setMessage("User Login Success..!!!");
        structure.setBody(users.get());
        structure.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<ResponseStructure<Employee>>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<String>> deleteById(Integer id) {
        ResponseStructure<String> structure = new ResponseStructure<>();

        Optional<Employee> dbUser = employeeRepo.findById(id);
        if (dbUser.isEmpty()) {
            throw new EmployeeNotFoundException("User id not found:" + id);
        }
        employeeRepo.deleteById(id);
        structure.setMessage("User Deleted With The Id : " + id);
        structure.setBody("User Deleted");
        structure.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

}
