package com.tvm.internal.tvm_internal_project.controller.onboarding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.service.UserService;
import com.tvm.internal.tvm_internal_project.service.onboarding.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    PersonalService personalService;



    @GetMapping("/byid/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        try {
            // Convert id from String to Long (assuming your employeeId is Long)
            Long employeeId = Long.parseLong(id);

            // Fetch the user from repository
            User user = userRepo.findByEmployeeId(employeeId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + employeeId));

            // Return user if found
            return ResponseEntity.ok(user);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null); // invalid id format
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }




}
