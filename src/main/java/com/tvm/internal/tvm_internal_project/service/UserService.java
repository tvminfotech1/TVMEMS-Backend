package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface UserService {


    boolean checkUserByEmail(String token, UserDetails userDetails);

    boolean checkUserByMobile(Long mob, String password);

    ResponseEntity<Map<String, Object>> createUser(User user);

}