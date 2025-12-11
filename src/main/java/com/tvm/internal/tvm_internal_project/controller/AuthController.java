package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.DTO.UserDto;
import com.tvm.internal.tvm_internal_project.config.JWTUtil;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.request.AuthRequest;
import com.tvm.internal.tvm_internal_project.service.UserService;
import com.tvm.internal.tvm_internal_project.serviceImpl.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping()
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/admin/newuser")
    public ResponseEntity<Map<String, Object>> createUser(@Valid @RequestBody User user) {
        emailService.sendRegistrationEmail(user.getEmail(), user.getFullName(), user.getEmail(), user.getPassword());
        user.setStatus(false);
        return userService.createUser(user);
    }

    @PostMapping("/userlogin")
    public ResponseEntity<?> loginByEmail(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        User user = userRepo.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails,user);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/userlogin/mobile")
    public ResponseEntity<?> loginByMobile(@RequestBody AuthRequest authRequest) {
        String mobileStr = String.valueOf(authRequest.getMobile());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(mobileStr, authRequest.getPassword())
        );
        User user = userRepo.findByMobile(authRequest.getMobile())
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails, user);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable Long employeeId){
        UserDto dto = userService.getUserDetails(employeeId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody AuthRequest authRequest) {
        User user = null;
        if (authRequest.getEmail() != null) {
            user = userRepo.findByEmail(authRequest.getEmail())
                    .orElse(null);
        } else if (authRequest.getMobile() != null) {
            user = userRepo.findByMobile(authRequest.getMobile())
                    .orElse(null);
        }
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "User not found"));
        }
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid previous password"));
        }
        user.setPassword(passwordEncoder.encode(authRequest.getNewPassword()));
        userRepo.save(user);
        return ResponseEntity.ok(Map.of("message", "Password has been changed"));
    }

    @PostMapping("/validate-password")
    public ResponseEntity<Boolean> validatePassword(@RequestBody Map<String, Object> body) {
        Long employeeId = Long.valueOf(body.get("employeeId").toString());
        String currentPassword = body.get("currentPassword").toString();
        boolean isValid = userService.validateCurrentPassword(employeeId, currentPassword);
        return ResponseEntity.ok(isValid);
    }




}