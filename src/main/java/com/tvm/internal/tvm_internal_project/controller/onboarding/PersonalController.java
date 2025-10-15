package com.tvm.internal.tvm_internal_project.controller.onboarding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvm.internal.tvm_internal_project.model.onboarding.*;
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
    private PersonalRepository personalRepository;

//    @PostMapping(
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
//    )
//    public ResponseEntity<ResponseStructure<Personal>> savePersonalInfo(
//            @RequestPart("jsonData") Personal personal,
//            @RequestPart(value = "panCard", required = false) MultipartFile panCard,
//            @RequestPart(value = "aadharCard", required = false) MultipartFile aadharCard,
//            @RequestPart(value = "pSizePhoto", required = false) MultipartFile pSizePhoto,
//            @RequestPart(value = "matric", required = false) MultipartFile matric,
//            @RequestPart(value = "intermediate", required = false) MultipartFile intermediate,
//            @RequestPart(value = "graduationMarksheet", required = false) MultipartFile graduationMarksheet,
//            @RequestPart(value = "postGraduation", required = false) MultipartFile postGraduation,
//            @RequestPart(value = "checkLeaf", required = false) MultipartFile checkLeaf,
//            @RequestPart(value = "passbook", required = false) MultipartFile passbook
//    ) throws IOException {
//        if (panCard != null && !panCard.isEmpty()) {
//            personal.getDocuments().setPanCard(panCard.getBytes());
//        }
//        if (aadharCard != null && !aadharCard.isEmpty()) {
//            personal.getDocuments().setAadharCard(aadharCard.getBytes());
//        }
//        if (pSizePhoto != null && !pSizePhoto.isEmpty()) {
//            personal.getDocuments().setpSizePhoto(pSizePhoto.getBytes());
//        }
//        if (matric != null && !matric.isEmpty()) {
//            personal.getDocuments().setMatric(matric.getBytes());
//        }
//        if (intermediate != null && !intermediate.isEmpty()) {
//            personal.getDocuments().setIntermediate(intermediate.getBytes());
//        }
//        if (graduationMarksheet != null && !graduationMarksheet.isEmpty()) {
//            personal.getDocuments().setGraduationMarksheet(graduationMarksheet.getBytes());
//        }
//        if (postGraduation != null && !postGraduation.isEmpty()) {
//            personal.getDocuments().setPostGraduation(postGraduation.getBytes());
//        }
//        if (postGraduation != null && !postGraduation.isEmpty()) {
//            personal.getDocuments().setPostGraduation(postGraduation.getBytes());
//        }
//        if (checkLeaf != null && !checkLeaf.isEmpty()) {
//            personal.getDocuments().setCheckLeaf(checkLeaf.getBytes());
//        }

    /// /        if (passbook != null && !passbook.isEmpty()) {
    /// /            personal.getP().setCheckLeaf(passbook.getBytes());
    /// /        }
//
//        return personalService.savePersonalInfo(personal);
//    }
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
            personalService.processOnboardingData(parsedSections);
            return ResponseEntity.ok(Map.of("message", "Data stored successfully"));
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

}


