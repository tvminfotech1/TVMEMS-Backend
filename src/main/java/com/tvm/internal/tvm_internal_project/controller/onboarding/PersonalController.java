package com.tvm.internal.tvm_internal_project.controller.onboarding;

import com.tvm.internal.tvm_internal_project.model.onboarding.Personal;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.response.WishesDto;
import com.tvm.internal.tvm_internal_project.service.onboarding.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("personal")
//@CrossOrigin(origins = "http://localhost:4200")
public class PersonalController {
    @Autowired
    private PersonalService personalService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResponseStructure<Personal>> savePersonalInfo(
            @RequestPart("jsonData") Personal personal,
            @RequestPart(value = "panCard", required = false) MultipartFile panCard,
            @RequestPart(value = "aadharCard", required = false) MultipartFile aadharCard,
            @RequestPart(value = "pSizePhoto", required = false) MultipartFile pSizePhoto,
            @RequestPart(value = "matric", required = false) MultipartFile matric,
            @RequestPart(value = "intermediate", required = false) MultipartFile intermediate,
            @RequestPart(value = "graduationMarksheet", required = false) MultipartFile graduationMarksheet,
            @RequestPart(value = "postGraduation", required = false) MultipartFile postGraduation,
            @RequestPart(value = "checkLeaf", required = false) MultipartFile checkLeaf
    ) throws IOException {
        if (panCard != null && !panCard.isEmpty()) {
            personal.getDocuments().setPanCard(panCard.getBytes());
        }
        if (aadharCard != null && !aadharCard.isEmpty()) {
            personal.getDocuments().setAadharCard(aadharCard.getBytes());
        }
        // Do the same for the rest...

        return personalService.savePersonalInfo(personal);
    }
    @GetMapping("/searchByName/{name}")
    public ResponseEntity<ResponseStructure<List<Personal>>> serachDetailsByName(@PathVariable String name) {
        return personalService.findAllDetailsUsingName(name);
    }

    @GetMapping("/searchByCity/{city}")
    public ResponseEntity<ResponseStructure<List<Personal>>> serachDetailsByCity(@PathVariable String city) {
        return personalService.findAllDetailsUsingCity(city);
    }

    @GetMapping("/searchByContact/{contact}")
    public ResponseEntity<ResponseStructure<Personal>> serachDetailsByContact(@PathVariable Long contact) {
        return personalService.findAllDetailsUsingPhone(contact);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseStructure<Personal>> findById(@PathVariable Integer id) {
        return personalService.findById(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseStructure<Personal>> updateById(@RequestBody Personal personal, @PathVariable Integer id) {
        return personalService.updatePersonal(personal, id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseStructure<String>> deleteById(@PathVariable Integer id) {
        return personalService.deleteById(id);
    }

    @GetMapping("/findAll")
    public ResponseEntity<ResponseStructure<List<Personal>>> findAllSkills() {
        return personalService.findAllPersonal();
    }

    @GetMapping("/wishes")
    public List<WishesDto> wishes() {
        return personalService.wishesService();
    }
}
