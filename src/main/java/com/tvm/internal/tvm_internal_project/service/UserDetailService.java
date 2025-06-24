package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.UserDetail;
import org.springframework.http.ResponseEntity;

public interface UserDetailService {

    ResponseEntity<String> createUser(UserDetail user);

    boolean checkUserByEmail(String userName, String password);

    boolean checkUserByMobile(Long mob, String password);
}
