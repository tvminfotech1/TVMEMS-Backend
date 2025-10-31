package com.tvm.internal.tvm_internal_project.service.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.BankDetailsDocument;
import com.tvm.internal.tvm_internal_project.model.onboarding.Documents;
import com.tvm.internal.tvm_internal_project.model.onboarding.EducationDocument;
import com.tvm.internal.tvm_internal_project.model.onboarding.KYCDocument;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentsService {
    Documents saveDocuments(Long employeeId,MultipartFile panCard, MultipartFile aadharCard, MultipartFile pSizePhoto, MultipartFile matric, MultipartFile intermediate, MultipartFile graduationMarksheet, MultipartFile postGraduation, MultipartFile checkLeaf, MultipartFile passbook) throws IOException;

    KYCDocument saveKYC(MultipartFile aadhar, MultipartFile pan, MultipartFile passport) throws IOException;

    EducationDocument saveEduDoc(MultipartFile doc10, MultipartFile doc12, MultipartFile graDoc, MultipartFile postGraDoc) throws IOException;

    BankDetailsDocument saveBankDetails(MultipartFile checkLeaf, MultipartFile bankPassbook) throws IOException;

    ResponseEntity<?> getAathatById(Long id);

    ResponseEntity<?> getPanById(Long id);

    ResponseEntity<?> getPassPortById(Long id);

    ResponseEntity<?> getDoc10ById(Long id);

    ResponseEntity<?> getDoc12ById(Long id);

    ResponseEntity<?> getGraDocById(Long id);

    ResponseEntity<?> getPostGraDocById(Long id);

    ResponseEntity<?> getCheckLeafById(Long id);

    ResponseEntity<?> getBankPassBookById(Long id);

     Documents getDocumentsWithBase64(Integer id);
}
