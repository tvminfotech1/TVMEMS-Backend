package com.tvm.internal.tvm_internal_project.serviceImpl.onboarding;

import com.tvm.internal.tvm_internal_project.exception.KYCNotFoundException;
import com.tvm.internal.tvm_internal_project.model.onboarding.KYC;
import com.tvm.internal.tvm_internal_project.repo.onboarding.KYCRepository;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.onboarding.KYCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KYCServiceImpl implements KYCService {
    @Autowired
    private KYCRepository kycRepository;

    public ResponseEntity<ResponseStructure<KYC>> saveKyc(KYC kyc) {
        KYC savedKyc = kycRepository.save(kyc);
        ResponseStructure<KYC> response = new ResponseStructure<>();
        response.setMessage("KYC saved successfully");
        response.setBody(savedKyc);
        response.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseStructure<KYC>> getById(Integer id) {
        ResponseStructure<KYC> response = new ResponseStructure<>();
        Optional<KYC> optionalKYC = kycRepository.findById(id);
        if (optionalKYC.isEmpty()) {
            throw new KYCNotFoundException("KYC not found with ID: " + id);
        }
        response.setMessage("KYC Found with ID ");
        response.setBody(optionalKYC.get());
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public ResponseEntity<ResponseStructure<KYC>> updateKyc(KYC kyc, Integer id) {

        ResponseStructure<KYC> response = new ResponseStructure<>();
        Optional<KYC> optionalKYC = kycRepository.findById(id);

        if (optionalKYC.isEmpty()) {
            throw new KYCNotFoundException("KYC not found with id " + id);
        } else {
            KYC existingKYC = optionalKYC.get();
            existingKYC.setPf(kyc.getPf());
            existingKYC.setPan(kyc.getPan());
            existingKYC.setAadhar(kyc.getAadhar());
            existingKYC.setAadharName(kyc.getAadharName());
            existingKYC.setHdfc(kyc.getHdfc());
            existingKYC.setPanName(kyc.getPanName());
            existingKYC.setUan(kyc.getUan());

            KYC updatedKyc = kycRepository.save(existingKYC);
            response.setBody(updatedKyc);
            response.setMessage("KYC successfully updated for id " + id);
            response.setStatusCode(HttpStatus.ACCEPTED.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseStructure<List<KYC>>> getAllKyc() {
        ResponseStructure<List<KYC>> structure = new ResponseStructure<>();

        List<KYC> kycList = kycRepository.findAll();
        if (kycList.isEmpty()) {
            throw new KYCNotFoundException("KYC Id is no records found");
        }

        structure.setMessage("List of all KYC id records");
        structure.setBody(kycList);
        structure.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<String>> deleteKyc(Integer id) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        Optional<KYC> optionalKYC = kycRepository.findById(id);

        if (optionalKYC.isEmpty()) {
            throw new KYCNotFoundException("KYC not found with id " + id);
        }
        kycRepository.deleteById(id);
        structure.setMessage("KYC successfully deleted.");
        structure.setBody("Deleted KYC with ID: " + id);
        structure.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }
}
