package com.tvm.internal.tvm_internal_project.controller.onboarding;

import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.onboarding.KYC;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.repo.onboarding.KYCRepository;
import com.tvm.internal.tvm_internal_project.DTO.UserPayroleDto;
import com.tvm.internal.tvm_internal_project.service.onboarding.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            Long employeeId = Long.parseLong(id);
            User user = userRepo.findByEmployeeId(employeeId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + employeeId));
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
                    KYC kyc = (KYC) kycRepository.findByUserEmployeeId(user.getEmployeeId())
                            .orElse(null);
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