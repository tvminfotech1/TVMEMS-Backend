package com.tvm.internal.tvm_internal_project.serviceImpl.onboarding;

import com.tvm.internal.tvm_internal_project.exception.FamilyNotFoundException;
import com.tvm.internal.tvm_internal_project.model.onboarding.Family;
import com.tvm.internal.tvm_internal_project.repo.onboarding.FamilyRepository;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.onboarding.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FamilyServiceImpl implements FamilyService {
    @Autowired
    private FamilyRepository familyRepository;


    public ResponseEntity<ResponseStructure<Family>> saveFamily(Family family) {
        Family saved = familyRepository.save(family);
        ResponseStructure<Family> familyDTO = new ResponseStructure<>();
        familyDTO.setBody(saved);
        familyDTO.setMessage("Family Saved Successfully");
        familyDTO.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(familyDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseStructure<Family>> getbyid(Long id) {
        Optional<Family> optional = familyRepository.findById(id);
        if (optional.isEmpty()) {
            throw new FamilyNotFoundException("Family ID is Not Found!!");
        }
        ResponseStructure<Family> familyDTO = new ResponseStructure<>();
        familyDTO.setBody(optional.get());
        familyDTO.setMessage("Family Fetch Successfully!!");
        familyDTO.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(familyDTO, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<List<Family>>> getall() {
        List<Family> families = familyRepository.findAll();
        ResponseStructure<List<Family>> familyDto = new ResponseStructure<>();
        familyDto.setBody(families);
        familyDto.setMessage("List of Families");
        familyDto.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(familyDto, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Family>> updateall(Long id, Family family) {
        Optional<Family> optional = familyRepository.findById(id);
        if (optional.isEmpty()) {
            throw new FamilyNotFoundException("Family ID is Not Found!!!");
        }
        Family family1 = optional.get();
        family1.setFatherDOB(family.getFatherDOB());
        family1.setChildren(family.getChildren());
        family1.setFatherName(family.getFatherName());
        family1.setMotherName(family.getMotherName());
        family1.setMotherDOB(family.getMotherDOB());
        family1.setSpouseName(family.getSpouseName());
        family1.setSpouseDOB(family.getSpouseDOB());
        family1.setSpouseGender(family.getSpouseGender());
        ResponseStructure<Family> familyDTO = new ResponseStructure<>();
        familyDTO.setBody(familyRepository.save(family1));
        familyDTO.setMessage("Family Updated Successfully");
        familyDTO.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(familyDTO, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<String>> deletebyid(Long id) {
        Optional<Family> optional = familyRepository.findById(id);
        if (optional.isEmpty()) {
            throw new FamilyNotFoundException("Family ID is Not Found!!!");
        }
        familyRepository.deleteById(id);
        ResponseStructure<String> familyDTO = new ResponseStructure<>();
        familyDTO.setBody("Family Deleted Successfully");
        familyDTO.setMessage("Success");
        familyDTO.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(familyDTO, HttpStatus.OK);
    }
}
