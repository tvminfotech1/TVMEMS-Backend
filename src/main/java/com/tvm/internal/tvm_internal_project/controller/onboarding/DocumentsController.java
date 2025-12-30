package com.tvm.internal.tvm_internal_project.controller.onboarding;

import com.tvm.internal.tvm_internal_project.DTO.OnboardingResponseDTO;
import com.tvm.internal.tvm_internal_project.model.onboarding.Documents;
import com.tvm.internal.tvm_internal_project.service.onboarding.DocumentsService;
import com.tvm.internal.tvm_internal_project.service.onboarding.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("documents")
public class DocumentsController {
    @Autowired
    private DocumentsService documentsService;
    @Autowired
    private PersonalService personalService;

    @PostMapping( value = "/upload/{employeeId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadDocuments(
            @PathVariable Long employeeId,
            @RequestParam("panCard") MultipartFile panCard,
            @RequestParam("aadharCard") MultipartFile aadharCard,
            @RequestParam("pSizePhoto") MultipartFile pSizePhoto,
            @RequestParam("matric") MultipartFile matric,
            @RequestParam("intermediate") MultipartFile intermediate,
            @RequestParam("graduationMarksheet") MultipartFile graduationMarksheet,
            @RequestParam(value = "postGraduation", required = false) MultipartFile postGraduation,
            @RequestParam("checkLeaf") MultipartFile checkLeaf,
            @RequestParam("passbook") MultipartFile passbook
    ) throws IOException {
        Documents savedDocs = documentsService.saveOrUpdateDocuments    (
                employeeId,panCard, aadharCard, pSizePhoto, matric, intermediate,
                graduationMarksheet, postGraduation, checkLeaf, passbook
        );
        return ResponseEntity.ok(Map.of("message", "Documents uploaded successfully! ID:"+employeeId));
    }

    @GetMapping("/photo/{employeeId}")
    public ResponseEntity<String> getUserProfilePhoto(@PathVariable Long employeeId) {
        String photoDataUrl = documentsService.getUserProfilePhoto(employeeId);
        if (photoDataUrl == null) {
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.ok(photoDataUrl);
    }

    @GetMapping("/onboardingDetails/{employeeId}")
    public ResponseEntity<OnboardingResponseDTO> getOnboardingDetails(@PathVariable Long employeeId){
        OnboardingResponseDTO response=personalService.getFullOnboarding(employeeId);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/view/{employeeId}/{docType}")
    public ResponseEntity<String> viewDocument(
            @PathVariable Long employeeId,
            @PathVariable String docType) {

        String base64 = personalService.getDocumentBase64(employeeId, docType);

        if (base64 == null) {
            return ResponseEntity.status(404).body("NO_FILE");
        }

        return ResponseEntity.ok(base64);
    }
}