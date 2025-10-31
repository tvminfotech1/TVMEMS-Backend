package com.tvm.internal.tvm_internal_project.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tvm.internal.tvm_internal_project.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface UserService {

    ResponseEntity<Map<String,Object>> createUser(User user);

    boolean checkUserByEmail(String token, UserDetails userDetails);

    boolean checkUserByMobile(Long mob, String password);

//    void processOnboardingData(Map<String, JsonNode> parsedSections);
    public boolean emailExists(String email);
    public boolean mobileExists(Long mobile);


}