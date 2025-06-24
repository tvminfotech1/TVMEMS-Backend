package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.model.UserDetail;
import com.tvm.internal.tvm_internal_project.repo.UserDetailRepo;
import com.tvm.internal.tvm_internal_project.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailRepo userDetailRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public ResponseEntity<String> createUser(UserDetail user) {
        Optional<UserDetail> existingUserEmail = userDetailRepo.findByEmailOrMobile(user.getEmail(), user.getMobile());
        if (existingUserEmail.isPresent()) {
            return ResponseEntity.ok("Email and Mobile already exists");
        }
        String encriptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encriptedPassword);
        userDetailRepo.save(user);

        return ResponseEntity.ok("User created successfully.");
    }

    public boolean checkUserByEmail(String email, String password) {
        Optional<UserDetail> optionalUser = userDetailRepo.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserDetail user = optionalUser.get();
            String encryptedPassword = user.getPassword();
            if (passwordEncoder.matches(password, encryptedPassword)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean checkUserByMobile(Long mob, String password) {
        Optional<UserDetail> optionalUser = userDetailRepo.findByMobile(mob);
        if (optionalUser.isPresent()) {
            UserDetail user = optionalUser.get();
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

