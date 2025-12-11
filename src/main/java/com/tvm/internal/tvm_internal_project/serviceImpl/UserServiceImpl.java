package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvm.internal.tvm_internal_project.DTO.UserDto;
import com.tvm.internal.tvm_internal_project.config.JWTUtil;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.onboarding.*;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.repo.onboarding.*;
import com.tvm.internal.tvm_internal_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userDetailRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired private PendingUserRepo pendingUserRepo;

     public ResponseEntity<Map<String, Object>> createUser(User user) {
        Map<String, Object> errorResponse = new HashMap<>();
        boolean emailExists = userDetailRepo.findByEmail(user.getEmail()).isPresent();
        boolean mobileExists = userDetailRepo.findByMobile(user.getMobile()).isPresent();

        if (emailExists || mobileExists) {
            errorResponse.put("emailExists", emailExists);
            errorResponse.put("mobileExists", mobileExists);
            errorResponse.put("message", "Duplicate entry detected");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
        String encriptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encriptedPassword);
        user.setRoles(Set.of("ROLE_USER"));
        user.setJoiningDate(new Date());
        User savedUser=userDetailRepo.save(user);
            PendingUser pending = new PendingUser();
            pending.setEmployeeId(savedUser.getEmployeeId());
            pending.setUser(savedUser);
            pending.setFullName(savedUser.getFullName());
            pending.setEmail(savedUser.getEmail());
            pending.setMobile(savedUser.getMobile());
            pending.setDob(savedUser.getDob());
            pending.setGender(savedUser.getGender());
            pending.setAadhar(savedUser.getAadhar());
            pending.setPassword(savedUser.getPassword());
            pending.setStatus(savedUser.getStatus());
            pending.setRole("ROLE_USER");
            pendingUserRepo.save(pending);
        errorResponse.put("status", "success");
        errorResponse.put("message", "User created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(errorResponse);
    }

    public boolean emailExists(String email) {
        return userDetailRepo.findByEmail(email).isPresent();
    }

    public boolean mobileExists(Long mobile) {
        return userDetailRepo.findByMobile(mobile).isPresent();
    }

    public boolean checkUserByEmail(String token, UserDetails userDetails) {
        return jwtUtil.validateToken(token,userDetails);
    }

    public boolean checkUserByMobile(Long mob, String password) {
        Optional<User> optionalUser = userDetailRepo.findByMobile(mob);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String encryptedPassword = user.getPassword();
            if (passwordEncoder.matches(password, encryptedPassword)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    @Override
    public User getUserById(Long userId) {
        return userDetailRepo.findById(userId).orElse(null);
    }

    @Override
    public UserDto getUserDetails(Long employeeId) {
        Optional<User> userOpt = userDetailRepo.findById(employeeId);
        if (userOpt.isEmpty()) {
            return null;
        }
        User user = userOpt.get();
        UserDto dto = new UserDto();
        dto.setEmployeeId(user.getEmployeeId());
        dto.setFullName(user.getFullName());
        dto.setMobile(user.getMobile());
        dto.setEmail(user.getEmail());
        dto.setAadhar(user.getAadhar());
        dto.setDob(user.getDob());
        dto.setGender(user.getGender());
        dto.setStatus(user.getStatus());
        dto.setJoiningDate(user.getJoiningDate());
        return dto;
    }

    public boolean validateCurrentPassword(Long employeeId, String currentPassword) {
        User user = userDetailRepo.findById(employeeId).orElse(null);
        if (user == null) {
            return false;
        }
        return passwordEncoder.matches(currentPassword, user.getPassword());
    }
}