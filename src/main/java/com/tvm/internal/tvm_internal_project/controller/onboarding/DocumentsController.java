package com.tvm.internal.tvm_internal_project.controller.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.BankDetailsDocument;
import com.tvm.internal.tvm_internal_project.model.onboarding.Documents;
import com.tvm.internal.tvm_internal_project.model.onboarding.EducationDocument;
import com.tvm.internal.tvm_internal_project.model.onboarding.KYCDocument;
import com.tvm.internal.tvm_internal_project.service.onboarding.DocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("documents")
public class DocumentsController {
    @Autowired
    private DocumentsService documentsService;


    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocuments(
            @RequestParam("panCard") MultipartFile panCard,
            @RequestParam("aadharCard") MultipartFile aadharCard,
            @RequestParam("pSizePhoto") MultipartFile pSizePhoto,
            @RequestParam("matric") MultipartFile matric,
            @RequestParam("intermediate") MultipartFile intermediate,
            @RequestParam("graduationMarksheet") MultipartFile graduationMarksheet,
            @RequestParam("postGraduation") MultipartFile postGraduation,
            @RequestParam("checkLeaf") MultipartFile checkLeaf,
            @RequestParam("passbook") MultipartFile passbook
    ) throws IOException {
        Documents savedDocs = documentsService.saveDocuments(
                panCard, aadharCard, pSizePhoto, matric, intermediate,
                graduationMarksheet, postGraduation, checkLeaf, passbook
        );


        return ResponseEntity.ok("Documents uploaded successfully! ID: ");
    }

    @PostMapping(value = "/kycdocument", consumes = "multipart/form-data")
    public ResponseEntity<String> saveKYCDocument(@RequestPart MultipartFile aadhar, @RequestPart MultipartFile pan, @RequestPart MultipartFile passport) {
        try {
            KYCDocument savedKYC = documentsService.saveKYC(aadhar, pan, passport);
            return ResponseEntity.ok("Image uploaded successfully. ID: " + savedKYC.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }
    }

    @GetMapping("/kycdocument/aadhar/{id}")
    public ResponseEntity<?> getAadhar(@PathVariable Long id) {
        return documentsService.getAathatById(id);
    }


    @GetMapping("/kycdocument/pan/{id}")
    public ResponseEntity<?> getPan(@PathVariable Long id) {

        return documentsService.getPanById(id);
    }

    @GetMapping("/kycdocument/passport/{id}")
    public ResponseEntity<?> getPassPort(@PathVariable Long id) {
        return documentsService.getPassPortById(id);

    }
    @PostMapping(value = "/Educationdocument", consumes = "multipart/form-data")
    public ResponseEntity<String> saveEducationDocument(@RequestPart MultipartFile doc10, @RequestPart MultipartFile doc12, @RequestPart MultipartFile graDoc, @RequestPart MultipartFile postGraDoc) {
        try {
            EducationDocument savedEduDoc = documentsService.saveEduDoc(doc10, doc12, graDoc, postGraDoc);
            return ResponseEntity.ok("Files uploaded successfully. ID: " + savedEduDoc.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }
    }


    @GetMapping("/Educationdocument/doc10/{id}")
    public ResponseEntity<?> getDoc10(@PathVariable Long id) {
        return documentsService.getDoc10ById(id);
    }


    @GetMapping("/Educationdocument/doc12/{id}")
    public ResponseEntity<?> getDoc12(@PathVariable Long id) {
        return documentsService.getDoc12ById(id);
    }


    @GetMapping("/Educationdocument/graDoc/{id}")
    public ResponseEntity<?> getGraDoc(@PathVariable Long id) {
        return documentsService.getGraDocById(id);
    }


    @GetMapping("/Educationdocument/postGraDoc/{id}")
    public ResponseEntity<?> getPostGraDoc(@PathVariable Long id) {
        return documentsService.getPostGraDocById(id);
    }

    @PostMapping(value = "/bankdetailsdocument", consumes = "multipart/form-data")
    public ResponseEntity<String> saveBankDetails(@RequestPart MultipartFile checkLeaf, @RequestPart MultipartFile bankPassBook) {
        try {
            BankDetailsDocument saveBankDetail = documentsService.saveBankDetails(checkLeaf, bankPassBook);
            return ResponseEntity.ok("File Submited Successfully" + saveBankDetail.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }
    }

    @GetMapping("/bankdetailsdocument/checkleaf/{id}")
    public ResponseEntity<?> getCheckLeaf(@PathVariable Long id) {
        return documentsService.getCheckLeafById(id);
    }


    @GetMapping("/bankdetailsdocument/bankpassbook/{id}")
    public ResponseEntity<?> getBankDetails(@PathVariable Long id) {
        return documentsService.getBankPassBookById(id);
    }
}