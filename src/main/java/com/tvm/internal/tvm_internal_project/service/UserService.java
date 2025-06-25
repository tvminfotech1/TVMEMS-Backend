package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<String> createUser(User user);

    boolean checkUserByEmail(String token);

    boolean checkUserByMobile(Long mob, String password);
}