package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

    @Autowired private PersonalRepository personalRepo;
    @Autowired private KYCRepository kycRepository;
    @Autowired private PassportRepository passportRepository;
    @Autowired private FamilyRepository familyRepository;
    @Autowired private EducationRepository educationRepository;
    @Autowired private SkillsRepository skillRepository;
    @Autowired private CertificationRepository certificationRepository;
    @Autowired private ResumeRepository resumeRepository;
    @Autowired private FinalRepository finalRepository;
    @Autowired private PreviousEmploymentRepository previousEmploymentRepository;
//    @Autowired private PendingUser pendingUser;
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
//        Optional<User> existingUserEmail = userDetailRepo.findByEmailOrMobile(user.getEmail(), user.getMobile());
//        if (existingUserEmail.isPresent()) {
//            return ResponseEntity.ok("Email and Mobile already exists");
//        }
        String encriptedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encriptedPassword);
        user.setRoles(Set.of("ROLE_USER"));
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

}