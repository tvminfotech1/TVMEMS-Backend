package com.tvm.internal.tvm_internal_project.controller.onboarding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.onboarding.*;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.repo.onboarding.PersonalRepository;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.response.WishesDto;
import com.tvm.internal.tvm_internal_project.service.UserService;
import com.tvm.internal.tvm_internal_project.service.onboarding.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("personal")
public class PersonalController {
    @Autowired
    private PersonalService personalService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PersonalRepository personalRepository;


    @GetMapping("/searchByName/{name}")
    public ResponseEntity<ResponseStructure<List<Personal>>> serachDetailsByName(@PathVariable String name) {
        return personalService.findAllDetailsUsingName(name);
    }

    @GetMapping("/searchByCity/{city}")
    public ResponseEntity<ResponseStructure<List<Personal>>> serachDetailsByCity(@PathVariable String city) {
        return personalService.findAllDetailsUsingCity(city);
    }

    @GetMapping("/searchByContact/{contact}")
    public ResponseEntity<ResponseStructure<Personal>> serachDetailsByContact(@PathVariable Long contact) {
        return personalService.findAllDetailsUsingPhone(contact);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseStructure<Personal>> findById(@PathVariable Integer id) {
        return personalService.findById(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseStructure<Personal>> updateById(@RequestBody Personal personal, @PathVariable Integer id) {
        return personalService.updatePersonal(personal, id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseStructure<String>> deleteById(@PathVariable Integer id) {
        return personalService.deleteById(id);
    }

    @GetMapping("/findAll")
    public ResponseEntity<ResponseStructure<List<Personal>>> findAllSkills() {
        return personalService.findAllPersonal();
    }

    @GetMapping("/wishes")
    public List<WishesDto> wishes() {
        return personalService.wishesService();
    }

//    @PostMapping("/savejson")
//    public ResponseEntity<String> save(@RequestBody  Personal personal){
//    return personalService.savedetails(personal);
//    }


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
                // Create new user
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
//            user.setEmail(personalNode.path("email").asText());
            user.setGender(personalNode.path("gender").asText());
            user.setAadhar(parsedSections.get("kyc") != null
                    ? parsedSections.get("kyc").path("aadhar").asText(null)
                    : null);

            user.setMobile(personalNode.path("current_contact").asLong());
            user.setStatus(true);
//            user.setPassword("test@123"); // you can hash or update later
//            user.setRoles(Set.of("USER"));

            // Parse DOB if available
            if (personalNode.hasNonNull("dob")) {
                try {
                    Date dob = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                            .parse(personalNode.path("dob").asText());
                    user.setDob(dob);
                } catch (Exception e) {
                    System.out.println("⚠️ Could not parse DOB: " + e.getMessage());
                }
            }


            User savedUser = userRepo.save(user);


            personalService.processOnboardingDataWithUser(parsedSections, savedUser);
//            personalService.processOnboardingData(parsedSections);
//            return ResponseEntity.ok(Map.of("message", "Data stored successfully"));
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

        // iterate through top-level fields
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


