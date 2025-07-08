package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.config.JWTUtil;
import com.tvm.internal.tvm_internal_project.model.User;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    @PostMapping("/admin/newuser")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        emailService.sendRegistrationEmail(user.getEmail(), user.getFullName());
        return userService.createUser(user);
    }

    @PostMapping("/userlogin")
    public ResponseEntity<?> loginByEmail(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(Map.of("token", token));
    }
}