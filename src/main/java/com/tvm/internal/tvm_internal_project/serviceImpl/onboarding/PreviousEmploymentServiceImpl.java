package com.tvm.internal.tvm_internal_project.serviceImpl.onboarding;

import com.tvm.internal.tvm_internal_project.exception.PersonalNotFoundException;
import com.tvm.internal.tvm_internal_project.exception.PreviousEmploymentNotFoundException;
import com.tvm.internal.tvm_internal_project.model.onboarding.Personal;
import com.tvm.internal.tvm_internal_project.model.onboarding.PreviousEmployment;
import com.tvm.internal.tvm_internal_project.repo.onboarding.PersonalRepository;
import com.tvm.internal.tvm_internal_project.repo.onboarding.PreviousEmploymentRepository;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.onboarding.PreviousEmploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreviousEmploymentServiceImpl implements PreviousEmploymentService {
    @Autowired
    private PreviousEmploymentRepository previousEmploymentRepository;

    @Autowired
    private PersonalRepository personalRepository;

    public ResponseEntity<ResponseStructure<PreviousEmployment>> saveEmployment(PreviousEmployment previousEmployment) {
//
        return null;
    }

    public ResponseEntity<ResponseStructure<PreviousEmployment>> findById(Integer id) {
        ResponseStructure<PreviousEmployment> structure = new ResponseStructure<>();
        Optional<PreviousEmployment> previousEmployment = previousEmploymentRepository.findById(id);
        if (previousEmployment.isEmpty()) {
            throw new PreviousEmploymentNotFoundException("Id Not Found");
        }
        structure.setMessage("previousEmployment Saved With The Id : " + id);
        structure.setBody(previousEmployment.get());
        structure.setStatusCode(HttpStatus.FOUND.value());

        return new ResponseEntity<ResponseStructure<PreviousEmployment>>(structure, HttpStatus.FOUND);
    }

    public ResponseEntity<ResponseStructure<PreviousEmployment>> updateResume(PreviousEmployment previousEmployment, Integer id) {
        ResponseStructure<PreviousEmployment> structure = new ResponseStructure<>();
        Optional<PreviousEmployment> previousEmployment1 = previousEmploymentRepository.findById(id);
        if (previousEmployment1.isEmpty()) {
            throw new PreviousEmploymentNotFoundException("Id Not Found");
        }
        PreviousEmployment resPreviousEmployment = previousEmployment1.get();
        resPreviousEmployment.setCompanyName(previousEmployment.getCompanyName());
        resPreviousEmployment.setDesignation((previousEmployment.getDesignation()));
        resPreviousEmployment.setEmploymentType(previousEmployment.getEmploymentType());
        resPreviousEmployment.setStartDate(previousEmployment.getStartDate());
        resPreviousEmployment.setEndDate(previousEmployment.getEndDate());
        structure.setMessage("PreviousEmployment Updated Successfully..!!!");
        structure.setBody(previousEmploymentRepository.save(resPreviousEmployment));
        structure.setStatusCode(HttpStatus.ACCEPTED.value());
        return new ResponseEntity<ResponseStructure<PreviousEmployment>>(structure, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseStructure<List<PreviousEmployment>>> findAllResume() {
        ResponseStructure<List<PreviousEmployment>> structure = new ResponseStructure<>();
        List<PreviousEmployment> previousEmploymentList = previousEmploymentRepository.findAll();
        structure.setMessage("List Of All Resumes...");
        structure.setBody(previousEmploymentList);
        structure.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<ResponseStructure<List<PreviousEmployment>>>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<String>> deleteById(Integer id) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        Optional<PreviousEmployment> previousEmployment = previousEmploymentRepository.findById(id);
        if (previousEmployment.isEmpty()) {
            throw new PreviousEmploymentNotFoundException("Id Not Found To Delete...");
        }
        previousEmploymentRepository.deleteById(id);
        structure.setMessage("previousEmployment Deleted Successfully..!!!");
        structure.setBody("Deleted Successfully...");
        structure.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);

    }
}
