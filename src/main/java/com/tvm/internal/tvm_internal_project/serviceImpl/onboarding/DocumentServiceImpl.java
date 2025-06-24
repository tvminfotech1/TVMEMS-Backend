package com.tvm.internal.tvm_internal_project.serviceImpl.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.BankDetailsDocument;
import com.tvm.internal.tvm_internal_project.model.onboarding.EducationDocument;
import com.tvm.internal.tvm_internal_project.model.onboarding.KYCDocument;
import com.tvm.internal.tvm_internal_project.repo.onboarding.BankDetailsDocumentRepo;
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
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentsService {

    @Autowired
    private KYCDocumentRepo kycDocumentRepo;

    @Autowired
    private EducationDocumentRepo educationDocumentRepo;

    @Autowired
    private BankDetailsDocumentRepo bankDetailsDocumentRepo;

    public KYCDocument saveKYC(MultipartFile aadhar, MultipartFile pan, MultipartFile passport) throws IOException {
        KYCDocument kycDocument = new KYCDocument();
        kycDocument.setAadharCard(aadhar.getBytes());
        kycDocument.setAadharCardType(aadhar.getContentType());
        kycDocument.setAadharCardName(aadhar.getOriginalFilename());
        kycDocument.setPassportCard(passport.getBytes());
        kycDocument.setPassportCardType(passport.getContentType());
        kycDocument.setPassposrtCardName(passport.getOriginalFilename());
        kycDocument.setPanCard(pan.getBytes());
        kycDocument.setPanCardName(pan.getOriginalFilename());
        kycDocument.setPanCardType(pan.getContentType());
        return kycDocumentRepo.save(kycDocument);
    }


    public EducationDocument saveEduDoc(MultipartFile doc10, MultipartFile doc12, MultipartFile graDoc, MultipartFile postGraDoc) throws IOException {
        EducationDocument educationDocument = new EducationDocument();
        educationDocument.setDoc_10(doc10.getBytes());
        educationDocument.setDoc_10Type(doc10.getContentType());
        educationDocument.setDoc_10Name(doc10.getOriginalFilename());
        educationDocument.setDoc_12(doc12.getBytes());
        educationDocument.setDoc_12Type(doc12.getContentType());
        educationDocument.setDoc_12Name(doc12.getOriginalFilename());
        educationDocument.setGraduationDoc(graDoc.getBytes());
        educationDocument.setGraDocNType(graDoc.getContentType());
        educationDocument.setGraDocName(graDoc.getOriginalFilename());
        educationDocument.setPostGradutionDoc(postGraDoc.getBytes());
        educationDocument.setPostGraDocNType(postGraDoc.getContentType());
        educationDocument.setPostGraDocName(postGraDoc.getOriginalFilename());
        return educationDocumentRepo.save(educationDocument);
    }


    public BankDetailsDocument saveBankDetails(MultipartFile checkLeaf, MultipartFile bankPassbook) throws IOException {
        BankDetailsDocument bankDetail = new BankDetailsDocument();
        bankDetail.setCheckLeaf(checkLeaf.getBytes());
        bankDetail.setCheckLeafType(checkLeaf.getContentType());
        bankDetail.setCheckLeafName(checkLeaf.getOriginalFilename());
        bankDetail.setBankPassbook(bankPassbook.getBytes());
        bankDetail.setBankPassbookType(bankPassbook.getContentType());
        bankDetail.setBankPassBookName(bankPassbook.getOriginalFilename());
        return bankDetailsDocumentRepo.save(bankDetail);
    }


    public ResponseEntity<?> getAathatById(Long id) {
        Optional<KYCDocument> optionalDocument = kycDocumentRepo.findById(id);

        if (optionalDocument.isPresent()) {
            KYCDocument kycDocument = optionalDocument.get();
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(kycDocument.getAadharCardType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + kycDocument.getAadharCardName() + "\"").body(kycDocument.getAadharCard());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aadhar Card  Not Found");
        }
    }

    public ResponseEntity<?> getPanById(Long id) {
        Optional<KYCDocument> optionalDocument = kycDocumentRepo.findById(id);

        if (optionalDocument.isPresent()) {
            KYCDocument kycDocument = optionalDocument.get();
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(kycDocument.getPanCardType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + kycDocument.getPanCardName() + "\"").body(kycDocument.getPanCard());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pan Card  Not Found");
        }
    }


    public ResponseEntity<?> getPassPortById(Long id) {
        Optional<KYCDocument> optionalDocument = kycDocumentRepo.findById(id);

        if (optionalDocument.isPresent()) {
            KYCDocument kycDocument = optionalDocument.get();
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(kycDocument.getPassportCardType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + kycDocument.getPassposrtCardName() + "\"").body(kycDocument.getPassportCard());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PassPort Card  Not Found");
        }
    }


    public ResponseEntity<?> getDoc10ById(Long id) {
        Optional<EducationDocument> optionalDocument = educationDocumentRepo.findById(id);

        if (optionalDocument.isPresent()) {
            EducationDocument educationDocument = optionalDocument.get();
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(educationDocument.getDoc_10Type())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + educationDocument.getDoc_10Name() + "\"").body(educationDocument.getDoc_10());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("10th Document Not Found");
        }
    }


    public ResponseEntity<?> getDoc12ById(Long id) {
        Optional<EducationDocument> optionalDocument = educationDocumentRepo.findById(id);

        if (optionalDocument.isPresent()) {
            EducationDocument educationDocument = optionalDocument.get();
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(educationDocument.getDoc_12Type())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + educationDocument.getDoc_12Name() + "\"").body(educationDocument.getDoc_12());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("12th Document Not Found");
        }
    }


    public ResponseEntity<?> getGraDocById(Long id) {
        Optional<EducationDocument> optionalDocument = educationDocumentRepo.findById(id);

        if (optionalDocument.isPresent()) {
            EducationDocument educationDocument = optionalDocument.get();
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(educationDocument.getGraDocNType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + educationDocument.getGraDocName() + "\"").body(educationDocument.getGraduationDoc());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Graduation Document Not Found");
        }
    }


    public ResponseEntity<?> getPostGraDocById(Long id) {
        Optional<EducationDocument> optionalDocument = educationDocumentRepo.findById(id);

        if (optionalDocument.isPresent()) {
            EducationDocument educationDocument = optionalDocument.get();
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(educationDocument.getPostGraDocNType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + educationDocument.getPostGraDocName() + "\"").body(educationDocument.getPostGradutionDoc());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post Graduation Document Not Found");
        }
    }


    public ResponseEntity<?> getCheckLeafById(Long id) {
        Optional<BankDetailsDocument> optionalDocument = bankDetailsDocumentRepo.findById(id);

        if (optionalDocument.isPresent()) {
            BankDetailsDocument bankDetailsDocument = optionalDocument.get();
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(bankDetailsDocument.getCheckLeafType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + bankDetailsDocument.getCheckLeafName() + "\"").body(bankDetailsDocument.getCheckLeaf());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Check Leaf   Not Found");
        }
    }


    public ResponseEntity<?> getBankPassBookById(Long id) {
        Optional<BankDetailsDocument> optionalDocument = bankDetailsDocumentRepo.findById(id);

        if (optionalDocument.isPresent()) {
            BankDetailsDocument bankDetailsDocument = optionalDocument.get();
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(bankDetailsDocument.getBankPassbookType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + bankDetailsDocument.getBankPassBookName() + "\"").body(bankDetailsDocument.getBankPassbook());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank Passbook Not Found");
        }
    }
}

