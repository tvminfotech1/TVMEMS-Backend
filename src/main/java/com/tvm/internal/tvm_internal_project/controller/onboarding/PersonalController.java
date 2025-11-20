package com.tvm.internal.tvm_internal_project.controller.onboarding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.DTO.WishesDto;
import com.tvm.internal.tvm_internal_project.service.onboarding.PersonalService;
import com.tvm.internal.tvm_internal_project.serviceImpl.onboarding.PersonalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("personal")
public class PersonalController {
    @Autowired
    private PersonalService personalService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PersonalServiceImpl personalServiceIm;

    @GetMapping("/wishes")
    public  Map<String,List<WishesDto>> wishes() {
        personalServiceIm.sendScheduledWishes();
        return personalService.prepareWishes();
    }

    @Autowired ObjectMapper objectMapper;
    @PostMapping("/savejson")
    public ResponseEntity<?> handleRawOnboarding(@RequestBody String rawBody) {
        try {
            Map<String, JsonNode> parsedSections = extractJsonSections(rawBody);
            JsonNode personalNode = parsedSections.get("personal");
            if (personalNode == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Missing 'personal' section in JSON"));
            }
            JsonNode employeeIdNode = parsedSections.get("employeeId");
            User user;
            if (employeeIdNode != null && !employeeIdNode.isNull()) {
                Long employeeId = employeeIdNode.asLong();
                user = userRepo.findById(employeeId)
                        .orElseThrow(() -> new RuntimeException("User not found with employeeId " + employeeId));
            } else {
                user = new User();
                user.setStatus(true);
                user.setPassword("test@123");
                user.setRoles(Set.of("USER"));
            }
            user.setFullName(buildFullName(
                    personalNode.path("fname").asText(""),
                    personalNode.path("mname").asText(""),
                    personalNode.path("lname").asText("")
            ));
            user.setGender(personalNode.path("gender").asText());
            user.setAadhar(parsedSections.get("kyc") != null
                    ? parsedSections.get("kyc").path("aadhar").asText(null)
                    : null);
            user.setMobile(personalNode.path("current_contact").asLong());
            user.setStatus(true);
            if (personalNode.hasNonNull("dob")) {
                try {
                    Date dob = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                            .parse(personalNode.path("dob").asText());
                    user.setDob(dob);
                } catch (Exception e) {
                    System.out.println("⚠️ Could not parse DOB: " + e.getMessage());
                }
            }
           user.setJoiningDate(new Date());
            User savedUser = userRepo.save(user);
            personalService.processOnboardingDataWithUser(parsedSections, savedUser);
            return ResponseEntity.ok(Map.of(
                    "message", "User and all onboarding data saved successfully",
                    "employeeId", savedUser.getEmployeeId()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    private Map<String, JsonNode> extractJsonSections(String raw) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(raw);
        Map<String, JsonNode> sections = new LinkedHashMap<>();
        Iterator<Map.Entry<String, JsonNode>> fields = root.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            sections.put(entry.getKey(), entry.getValue());
        }
        return sections;
    }

    private String buildFullName(String fname, String mname, String lname) {
        return String.join(" ", Arrays.asList(fname, mname, lname)).replaceAll("\\s+", " ").trim();
    }
}


