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
    public String getUserProfilePhoto(Long employeeId);
}
