package com.tvm.internal.tvm_internal_project.serviceImpl.onboarding;

import com.tvm.internal.tvm_internal_project.exception.CertificationNotFoundException;
import com.tvm.internal.tvm_internal_project.model.onboarding.Certification;
import com.tvm.internal.tvm_internal_project.repo.onboarding.CertificationRepository;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.onboarding.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificationServiceImpl implements CertificationService {
    @Autowired
    private CertificationRepository certificationRepository;

    public ResponseEntity<ResponseStructure<Certification>> saveCertification(Certification certification) {
        Certification saved = certificationRepository.save(certification);
        ResponseStructure<Certification> structure = new ResponseStructure<>();
        structure.setBody(saved);
        structure.setMessage("Certification Saved Successfully!!!");
        structure.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(structure, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseStructure<Certification>> getById(Integer id) {
        Optional<Certification> optionalCertification = certificationRepository.findById(id);
        if (optionalCertification.isEmpty()) {
            throw new CertificationNotFoundException("Certification Id is not Found");
        }
        ResponseStructure<Certification> structure = new ResponseStructure<>();
        structure.setBody(optionalCertification.get());
        structure.setMessage("Certification fetch Successfully!!!!");
        structure.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<List<Certification>>> getall() {
        List<Certification> certifications = certificationRepository.findAll();
        ResponseStructure<List<Certification>> structure = new ResponseStructure<>();
        structure.setBody(certifications);
        structure.setMessage("List of all Certification Details");
        structure.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Certification>> updateAll(Integer id, Certification certification) {
        Optional<Certification> optional = certificationRepository.findById(id);
        if (optional.isEmpty()) {
            throw new CertificationNotFoundException("Certification Not Found for ID: " + id);
        }
        Certification existing = optional.get();
        existing.setCertificateName(certification.getCertificateName());
        existing.setCertifiedBy(certification.getCertifiedBy());
        existing.setMarks(certification.getMarks());
        existing.setCompletionDate(certification.getCompletionDate());
        ResponseStructure<Certification> structure = new ResponseStructure<>();
        structure.setBody(certificationRepository.save(existing));
        structure.setMessage("Certification Updated Successfully!!!!");
        structure.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<String>> deletebyid(Integer id) {
        Optional<Certification> delete = certificationRepository.findById(id);
        if (delete.isEmpty()) {
            throw new CertificationNotFoundException("Certification Not Found For ID:" + id);
        }
        certificationRepository.deleteById(id);
        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setBody("Certification Deleted Successfully!!");
        structure.setMessage("Success");
        structure.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }
}
