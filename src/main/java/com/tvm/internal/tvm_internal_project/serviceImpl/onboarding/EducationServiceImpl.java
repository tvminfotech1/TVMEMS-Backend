package com.tvm.internal.tvm_internal_project.serviceImpl.onboarding;

import com.tvm.internal.tvm_internal_project.exception.EducationNotFoundException;
import com.tvm.internal.tvm_internal_project.model.onboarding.Education;
import com.tvm.internal.tvm_internal_project.repo.onboarding.EducationRepository;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.onboarding.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EducationServiceImpl implements EducationService {
    @Autowired
    private EducationRepository educationRepository;

    public ResponseEntity<ResponseStructure<Education>> saveEducation(Education education) {
        Education saved = educationRepository.save(education);
        ResponseStructure<Education> educationDTO = new ResponseStructure<>();
        educationDTO.setBody(saved);
        educationDTO.setMessage("Education Details Saved Successfully!!!");
        educationDTO.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(educationDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseStructure<Education>> getbyid(Integer id) {
        Optional<Education> optional = educationRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EducationNotFoundException("Education ID is Not Found");
        }
        ResponseStructure<Education> educationDTO = new ResponseStructure<>();
        educationDTO.setBody(optional.get());
        educationDTO.setMessage("Education Fetch Successfully!!");
        educationDTO.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(educationDTO, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<List<Education>>> getall() {
        List<Education> educations = educationRepository.findAll();
        ResponseStructure<List<Education>> educationDto = new ResponseStructure<>();
        educationDto.setBody(educations);
        educationDto.setMessage("List of all Education Details");
        educationDto.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(educationDto, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Education>> updatebyid(Integer id, Education education) {
        Optional<Education> education2 = educationRepository.findById(id);
        if (education2.isEmpty()) {
            throw new EducationNotFoundException("Education ID is Not Found!!");
        }
        Education education1 = education2.get();
        education1.setEducationType(education.getEducationType());
        education1.setFromDate(education.getFromDate());
        education1.setToDate(education.getToDate());
        education1.setPercentage(education.getPercentage());
        education1.setQualification(education.getQualification());
        education1.setSpecilization(education.getSpecilization());
        education1.setInstituteName(education.getInstituteName());
        education1.setRollNo(education.getRollNo());
        education1.setUniversityName(education.getUniversityName());
        education1.setTime(education.getTime());
        ResponseStructure<Education> educationDTO = new ResponseStructure<>();
        educationDTO.setBody(educationRepository.save(education1));
        educationDTO.setMessage("Updates the Education Details!!!");
        educationDTO.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(educationDTO, HttpStatus.OK);

    }

    public ResponseEntity<ResponseStructure<String>> deletebyid(Integer id) {
        Optional<Education> education = educationRepository.findById(id);
        if (education.isEmpty()) {
            throw new EducationNotFoundException("Education ID is Not Found!!");
        }
        educationRepository.deleteById(id);
        ResponseStructure<String> stringDTO = new ResponseStructure<>();
        stringDTO.setBody("Education Deleted Successfully!!!");
        stringDTO.setMessage("Success");
        stringDTO.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(stringDTO, HttpStatus.OK);
    }

}
