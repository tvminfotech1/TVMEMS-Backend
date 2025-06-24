package com.tvm.internal.tvm_internal_project.controller.onboarding;


import com.tvm.internal.tvm_internal_project.model.onboarding.Skills;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.onboarding.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("skills")
public class SkillsController {
    @Autowired
    private SkillsService skillsService;

    @PostMapping
    public ResponseEntity<ResponseStructure<Skills>> saveSkills(@RequestBody Skills skills) {
        return skillsService.saveSkills(skills);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseStructure<Skills>> findById(@PathVariable Integer id) {
        return skillsService.getbyid(id);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Skills>>> findAllSkills() {
        return skillsService.getall();
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseStructure<Skills>> updateById(@PathVariable Integer id, @RequestBody Skills skills) {
        return skillsService.Updatebyid(id, skills);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseStructure<String>> deleteById(@PathVariable Integer id) {
        return skillsService.deletebyid(id);
    }

}
