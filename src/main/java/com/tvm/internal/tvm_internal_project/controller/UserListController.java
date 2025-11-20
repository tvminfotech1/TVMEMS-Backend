package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.serviceImpl.UserListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/userlist")
public class UserListController {

    @Autowired
    UserListServiceImpl userListServiceImpl;

    @GetMapping("/all")
    public List<User> getAllUser(){
        return userListServiceImpl.getAllUser();
    }

    @DeleteMapping("/delete/{employeeId}")
    public void deleteUser(@PathVariable("employeeId") long employeeId){
        userListServiceImpl.deleteUser(employeeId);
    }
}
