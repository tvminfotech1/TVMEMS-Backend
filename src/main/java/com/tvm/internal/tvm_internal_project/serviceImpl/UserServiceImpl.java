package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.config.JWTUtil;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userDetailRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtil jwtUtil;


    public ResponseEntity<String> createUser(User user) {
        Optional<User> existingUserEmail = userDetailRepo.findByEmailOrMobile(user.getEmail(), user.getMobile());
        if (existingUserEmail.isPresent()) {
            return ResponseEntity.ok("Email and Mobile already exists");
        }
        String encriptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encriptedPassword);
        userDetailRepo.save(user);

        return ResponseEntity.ok("User created successfully.");
    }

    public boolean checkUserByEmail(String token) {
        return jwtUtil.validateToken(token);

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
}