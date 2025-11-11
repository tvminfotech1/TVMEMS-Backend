package com.tvm.internal.tvm_internal_project.controller.onboarding;

import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.onboarding.KYC;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.repo.onboarding.KYCRepository;
import com.tvm.internal.tvm_internal_project.response.UserPayroleDto;
import com.tvm.internal.tvm_internal_project.service.onboarding.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    PersonalService personalService;

    @Autowired
    KYCRepository kycRepository;



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


    @GetMapping("/payrole/{id}")
    public ResponseEntity<UserPayroleDto> getUserDtoById(@PathVariable Long id) {
        return userRepo.findById(id)
                .map(user -> {
                    // Fetch KYC for this user (based on employeeId)
                    KYC kyc = (KYC) kycRepository.findByUserEmployeeId(user.getEmployeeId())
                            .orElse(null);  // returns null if not found

                    // Build DTO by merging User + KYC data
                    String pan = (kyc != null) ? kyc.getPan() : null;

                    UserPayroleDto dto = new UserPayroleDto(
                            user.getEmployeeId(),
                            user.getFullName(),
                            user.getMobile(),
                            user.getEmail(),
                            user.getAadhar(),
                            user.getJoiningDate(),
                            pan
                    );

                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }


}