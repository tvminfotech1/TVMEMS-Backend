package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.User;

import java.util.List;

public interface UserListService {

    List<User> getAllUser();

    void deleteUser(long employeeId);
}
