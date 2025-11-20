package com.tvm.internal.tvm_internal_project.serviceImpl.onboarding;

import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.onboarding.BankDetailsDocument;
import com.tvm.internal.tvm_internal_project.model.onboarding.Documents;
import com.tvm.internal.tvm_internal_project.model.onboarding.EducationDocument;
import com.tvm.internal.tvm_internal_project.model.onboarding.KYCDocument;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.repo.onboarding.BankDetailsDocumentRepo;
import com.tvm.internal.tvm_internal_project.repo.onboarding.DocumentsRepository;
import com.tvm.internal.tvm_internal_project.repo.onboarding.EducationDocumentRepo;
import com.tvm.internal.tvm_internal_project.repo.onboarding.KYCDocumentRepo;
import com.tvm.internal.tvm_internal_project.service.onboarding.DocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentsService {

    @Autowired
    private KYCDocumentRepo kycDocumentRepo;

    @Autowired
    private EducationDocumentRepo educationDocumentRepo;

    @Autowired
    private BankDetailsDocumentRepo bankDetailsDocumentRepo;

    @Autowired
    private DocumentsRepository documentsRepository;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Documents saveDocuments(Long employeeId,MultipartFile panCard, MultipartFile aadharCard, MultipartFile pSizePhoto, MultipartFile matric, MultipartFile intermediate, MultipartFile graduationMarksheet, MultipartFile postGraduation, MultipartFile checkLeaf, MultipartFile passbook) throws IOException {

        User user = userRepo.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("User not found with employeeId: " + employeeId));

        Documents docs = new Documents();
        docs.setUser(user);
        docs.setPanCard(panCard.getBytes());
        docs.setAadharCard(aadharCard.getBytes());
        docs.setpSizePhoto(pSizePhoto.getBytes());
        docs.setMatric(matric.getBytes());
        docs.setIntermediate(intermediate.getBytes());
        docs.setGraduationMarksheet(graduationMarksheet.getBytes());
        if(postGraduation!=null) docs.setPostGraduation(postGraduation.getBytes());
        docs.setCheckLeaf(checkLeaf.getBytes());
        docs.setPassbook(passbook.getBytes());

        return documentsRepository.save(docs);
    }

    public String getUserProfilePhoto(Long employeeId) {
        Documents documents = documentsRepository.findByUserEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Documents not found for employeeId: " + employeeId));

        if (documents.getpSizePhoto() == null) {
            return null; // no photo uploaded
        }

        String base64Photo = Base64.getEncoder().encodeToString(documents.getpSizePhoto());
        return "data:image/jpeg;base64," + base64Photo;
    }

}