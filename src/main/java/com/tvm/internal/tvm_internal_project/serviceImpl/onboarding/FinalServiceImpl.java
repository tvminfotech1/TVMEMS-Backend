package com.tvm.internal.tvm_internal_project.serviceImpl.onboarding;

import com.tvm.internal.tvm_internal_project.exception.FinalNotFoundException;
import com.tvm.internal.tvm_internal_project.model.onboarding.Final;
import com.tvm.internal.tvm_internal_project.repo.onboarding.FinalRepository;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.onboarding.FinalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinalServiceImpl implements FinalService {
    @Autowired
    private FinalRepository finalRepository;


    public ResponseEntity<ResponseStructure<Final>> saveFinal(Final aFinal) {
        Final savedFinal = finalRepository.save(aFinal);

        ResponseStructure<Final> response = new ResponseStructure<>();
        response.setMessage("Final saved successfully");
        response.setBody(savedFinal);
        response.setStatusCode(HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    public ResponseEntity<ResponseStructure<Final>> getById(Integer id) {
        ResponseStructure<Final> structure = new ResponseStructure<>();
        Optional<Final> optionalFinal = finalRepository.findById(id);
        if (optionalFinal.isEmpty()) {
            throw new FinalNotFoundException("Final Id not found: " + id);
        }
        structure.setBody(optionalFinal.get());
        structure.setMessage("Final Id found by successfully");
        structure.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Final>> updateFinal(Final aFinal, Integer id) {
        ResponseStructure<Final> structure = new ResponseStructure<>();
        Optional<Final> optionalFinal = finalRepository.findById(id);

        if (optionalFinal.isEmpty()) {
            throw new FinalNotFoundException("Final Id is not found" + id);

        } else {
            Final existingFinal = optionalFinal.get();
            existingFinal.setChecked(aFinal.isChecked());

            existingFinal.setUser(aFinal.getUser());
            existingFinal.setSignature(aFinal.getSignature());
            existingFinal.setDate(aFinal.getDate());

            Final updatedFinal = finalRepository.save(existingFinal);
            structure.setBody(updatedFinal);
            structure.setMessage("Final Id successfully updated:  " + id);
            structure.setStatusCode(HttpStatus.ACCEPTED.value());

            return new ResponseEntity<>(structure, HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseStructure<List<Final>>> getFinalAll() {
        List<Final> finalList = finalRepository.findAll();
        ResponseStructure<List<Final>> structure = new ResponseStructure<>();

        if (finalList.isEmpty()) {
            throw new FinalNotFoundException("Final Id is no records found");
        }
        structure.setMessage("List of all Final id records");
        structure.setBody(finalList);
        structure.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<String>> deleteFinal(Integer id) {
        Optional<Final> optionalFinal = finalRepository.findById(id);
        ResponseStructure<String> structure = new ResponseStructure<>();

        if (optionalFinal.isEmpty()) {
            throw new FinalNotFoundException("Final Id not found : " + id);
        }

        finalRepository.deleteById(id);
        structure.setMessage("Final Id successfully deleted.");
        structure.setBody("Deleted Final with ID: " + id);
        structure.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public boolean isOnboardingSubmitted(Integer userId) {
        return finalRepository.existsByUser_EmployeeId(userId);
    }

}
