package com.tvm.internal.tvm_internal_project.serviceImpl.onboarding;

import com.tvm.internal.tvm_internal_project.exception.SkillsNotFoundException;
import com.tvm.internal.tvm_internal_project.model.onboarding.Skills;
import com.tvm.internal.tvm_internal_project.repo.onboarding.SkillsRepository;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.onboarding.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillsServiceImpl implements SkillsService {
    @Autowired
    private SkillsRepository skillsRepository;

    public ResponseEntity<ResponseStructure<Skills>> saveSkills(Skills skills) {
        Skills saved = skillsRepository.save(skills);
        ResponseStructure<Skills> skillsDTO = new ResponseStructure<>();
        skillsDTO.setBody(saved);
        skillsDTO.setMessage("Skills Saved Successfully!!!");
        skillsDTO.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(skillsDTO, HttpStatus.CREATED);

    }

    public ResponseEntity<ResponseStructure<Skills>> getbyid(Integer id) {
        Optional<Skills> optional = skillsRepository.findById(id);
        if (optional.isEmpty()) {
            throw new SkillsNotFoundException("Skill ID is Not Found:" + id);
        }
        ResponseStructure<Skills> skillsDTO = new ResponseStructure<>();
        skillsDTO.setBody(optional.get());
        skillsDTO.setMessage("SKills Fetch Successfully!!");
        skillsDTO.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(skillsDTO, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<List<Skills>>> getall() {
        List<Skills> skills = skillsRepository.findAll();
        ResponseStructure<List<Skills>> skillsDTO = new ResponseStructure<>();
        skillsDTO.setBody(skills);
        skillsDTO.setMessage("List of Skills");
        skillsDTO.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(skillsDTO, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Skills>> Updatebyid(Integer id, Skills skills) {
        Optional<Skills> optional = skillsRepository.findById(id);
        if (optional.isEmpty()) {
            throw new SkillsNotFoundException("Skills ID Not Found:" + id);
        }
        Skills skills1 = optional.get();
        skills1.setExperience_month(skills.getExperience_month());
        skills1.setSkillCategories(skills.getSkillCategories());
        skills1.setSkillName(skills.getSkillName());
        skills1.setUser(skills.getUser());
        skills1.setSelfRate(skills.getSelfRate());
        skills1.setVersionNum(skills.getVersionNum());
        ResponseStructure<Skills> skillsDTO = new ResponseStructure<>();
        skillsDTO.setBody(skillsRepository.save(skills1));
        skillsDTO.setMessage("Skills Updated Successfully!!");
        skillsDTO.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(skillsDTO, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<String>> deletebyid(Integer id) {
        Optional<Skills> optional = skillsRepository.findById(id);
        if (optional.isEmpty()) {
            throw new SkillsNotFoundException("Skill ID Not Found:" + id);
        }
        skillsRepository.deleteById(id);
        ResponseStructure<String> skillsDTO = new ResponseStructure<>();
        skillsDTO.setBody("Skills Deleted Successfully!!");
        skillsDTO.setMessage("Success");
        skillsDTO.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(skillsDTO, HttpStatus.OK);
    }

}
