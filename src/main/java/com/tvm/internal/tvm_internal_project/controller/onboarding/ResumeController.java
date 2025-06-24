package com.tvm.internal.tvm_internal_project.controller.onboarding;


import com.tvm.internal.tvm_internal_project.model.onboarding.Resume;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.service.onboarding.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping
    public ResponseEntity<ResponseStructure<Resume>> saveResume(@RequestBody Resume resume) {
        return resumeService.saveResume(resume);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseStructure<Resume>> getById(@PathVariable Integer id) {
        return resumeService.getById(id);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Resume>>> findAllResume() {
        return resumeService.getAllResume();
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseStructure<Resume>> updateById(@RequestBody Resume resume, @PathVariable Integer id) {
        return resumeService.updateResume(resume, id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseStructure<String>> deleteById(@PathVariable Integer id) {
        return resumeService.deleteById(id);
    }

}

