package com.tvm.internal.tvm_internal_project.serviceImpl.onboarding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvm.internal.tvm_internal_project.exception.PersonalNotFoundException;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.onboarding.*;
import com.tvm.internal.tvm_internal_project.repo.EmployeeRepo;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.repo.onboarding.*;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.response.WishesDto;
import com.tvm.internal.tvm_internal_project.service.onboarding.PersonalService;
import io.jsonwebtoken.io.IOException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.*;

@Service
public class PersonalServiceImpl implements PersonalService {
    @Autowired
    private PersonalRepository personalRepository;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PendingUserRepo pendingUserRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private KYCRepository kycRepository;
    @Autowired
    private PassportRepository passportRepository;
    @Autowired
    private FamilyRepository familyRepository;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private SkillsRepository skillRepository;
    @Autowired
    private CertificationRepository certificationRepository;
    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private FinalRepository finalRepository;
    @Autowired
    private PreviousEmploymentRepository previousEmploymentRepository;

    @Autowired
    private UserRepo userRepository;

    public ResponseEntity<ResponseStructure<Personal>> savePersonalInfo(Personal personal) {
        Personal savedPersonal = personalRepository.save(personal);
        ResponseStructure<Personal> structure = new ResponseStructure<>();
        structure.setMessage("Personal info saved successfully");
        structure.setBody(savedPersonal); // ← now includes the generated ID
        structure.setStatusCode(HttpStatus.CREATED.value());

        return new ResponseEntity<>(structure, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseStructure<List<Personal>>> findAllDetailsUsingName(String name) {
        ResponseStructure<List<Personal>> structure = new ResponseStructure<>();
        List<Personal> personals = personalRepository.findByFname(name);

        if (personals != null && !personals.isEmpty()) {
            structure.setMessage("Successfully found records with name: " + name);
            structure.setBody(personals);
            structure.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(structure, HttpStatus.OK);
        } else {
            structure.setMessage("No records found with name: " + name);
            structure.setBody(Collections.emptyList());
            structure.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseStructure<List<Personal>>> findAllDetailsUsingCity(String city) {
        ResponseStructure<List<Personal>> structure = new ResponseStructure<>();
        List<Personal> personals = personalRepository.findByPermanentCity(city);

        if (personals != null && !personals.isEmpty()) {
            structure.setMessage("Successfully found records with City: " + city);
            structure.setBody(personals);
            structure.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(structure, HttpStatus.OK);
        } else {
            structure.setMessage("No records found with City: " + city);
            structure.setBody(Collections.emptyList());
            structure.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseStructure<Personal>> findAllDetailsUsingPhone(Long contact) {
        ResponseStructure<Personal> structure = new ResponseStructure<>();
        Optional<Personal> personals = personalRepository.findByPermanentContact(contact);

        if (personals.isEmpty()) {
            throw new PersonalNotFoundException("No User with th Contact No :" + contact);
        }
        structure.setMessage("Successfully found records with the Contact No: " + contact);
        structure.setBody(personals.get());
        structure.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }


    public ResponseEntity<ResponseStructure<Personal>> findById(Integer id) {
        ResponseStructure<Personal> structure = new ResponseStructure<>();

        Optional<Personal> dbPersonal = personalRepository.findById(id);
        if (dbPersonal.isEmpty()) {
            throw new PersonalNotFoundException("Personal Id not found :" + id);
        }
        structure.setMessage("Personal id Successfully found:" + id);
        structure.setBody(dbPersonal.get());
        structure.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Personal>> updatePersonal(Personal personal, Integer id) {

        ResponseStructure<Personal> structure = new ResponseStructure<>();
        Optional<Personal> dbPersonal = personalRepository.findById(id);
        if (dbPersonal.isEmpty()) {
            throw new PersonalNotFoundException("Personal Id not Found :" + id);
        } else {
            Personal resPersonal = dbPersonal.get();
            resPersonal.setFname(personal.getFname());
            resPersonal.setLname(personal.getLname());
            resPersonal.setEmail(personal.getEmail());
            resPersonal.setGender(personal.getGender());
            resPersonal.setBloodGroup(personal.getBloodGroup());
            resPersonal.setCurrent_address(personal.getCurrent_address());
            resPersonal.setCurrent_city(personal.getCurrent_city());
            resPersonal.setCurrent_contact(personal.getCurrent_contact());
            resPersonal.setCurrent_state(personal.getCurrent_state());
            resPersonal.setCurrent_pincode(personal.getCurrent_pincode());
            resPersonal.setCurrent_country(personal.getCurrent_country());
            resPersonal.setPermanent_address(personal.getPermanent_address());
            resPersonal.setPermanent_state(personal.getPermanent_state());
            resPersonal.setPermanent_city(personal.getPermanent_city());
            resPersonal.setPermanent_pincode(personal.getPermanent_pincode());
            resPersonal.setPermanent_contact(personal.getPermanent_contact());
            resPersonal.setPermanent_country(personal.getPermanent_country());
            resPersonal.setBcp_address(personal.getBcp_address());
            resPersonal.setBcp_city(personal.getBcp_city());
            resPersonal.setBcp_country(personal.getBcp_country());
            resPersonal.setBcp_state(personal.getBcp_state());
            resPersonal.setBcp_pincode(personal.getBcp_pincode());
            resPersonal.setEmergency_contact_name(personal.getEmergency_contact_name());
            resPersonal.setEmergency_contact_number(personal.getEmergency_contact_number());
            resPersonal.setEmergency_relationship(personal.getEmergency_relationship());
            resPersonal.setExp_year(personal.getExp_year());
            resPersonal.setExp_month(personal.getExp_month());
            resPersonal.setRelevantYear(personal.getRelevantYear());

            Personal savedPersonal = personalRepository.save(resPersonal);
            structure.setMessage("Personal Details Updated Successfully With The Id:" + id);
            structure.setBody(savedPersonal);
            structure.setStatusCode(HttpStatus.ACCEPTED.value());

            return new ResponseEntity<>(structure, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<ResponseStructure<List<Personal>>> findAllPersonal() {
        ResponseStructure<List<Personal>> structure = new ResponseStructure<>();

        List<Personal> personals = personalRepository.findAll();
        if (personals.isEmpty()) {
            throw new PersonalNotFoundException("Personal Details not Found");
        }
        structure.setMessage("List of all Personal details");
        structure.setBody(personals);
        structure.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<String>> deleteById(Integer id) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        Optional<Personal> dbPersonal = personalRepository.findById(id);
        if (dbPersonal.isEmpty()) {
            throw new PersonalNotFoundException("Personal id not found:" + id);
        }
        personalRepository.deleteById(id);
        structure.setMessage("Personal Deleted With The Id : " + id);
        structure.setBody("Deleted with personal id");
        structure.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public List<WishesDto> wishesService() {
        System.out.println("Birthday Wishes is running........");
        List<Personal> personals = personalRepository.findAll();
        LocalDate today = LocalDate.now();

        List<WishesDto> wishesList = new ArrayList<>();

        for (Personal personal : personals) {
            LocalDate dob = LocalDate.parse(personal.getDob()); // ensure dob is LocalDate
            if (dob != null && dob.getDayOfMonth() == today.getDayOfMonth() && dob.getMonth() == today.getMonth()) {

                WishesDto dto = new WishesDto();
                dto.setName(personal.getFname());
                dto.setDob(String.valueOf(dob));

                wishesList.add(dto);
            }
        }
        return wishesList;
    }

    public ResponseEntity<String> savedetails(Personal personal) {
        personalRepository.save(personal);
        return ResponseEntity.ok("Succesfully");

    }



    private final ObjectMapper objectMappers = new ObjectMapper();

    //
    @Override
    @Transactional
    public void processOnboardingDataWithUser(Map<String, JsonNode> parsedSections, User user) {

        if (user.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee ID is required");
        }

        // Find existing user by employeeId
        User existingUser = userRepository.findByEmployeeId(user.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("User not found with employeeId: "));

        if (existingUser != null) {
            //  Update existing user instead of new insert
            existingUser.setFullName(user.getFullName());
            existingUser.setEmail(user.getEmail());
            existingUser.setMobile(user.getMobile());

            user = existingUser; // important ✔
        }

        // Save (this will do UPDATE if exists)
        User savedUser = userRepository.save(user);

        // --- Personal ---
        JsonNode personalNode = parsedSections.get("personal");
        if (personalNode != null) {
            Personal personal = objectMapper.convertValue(personalNode, Personal.class);
            personal.setUser(user);// Link the saved user
            personalRepository.save(personal);
            System.out.println("Employee ID linked to personal: " + user.getEmployeeId());
        }

        //  Process KYC
        JsonNode kycNode = parsedSections.get("kyc");
        if (kycNode != null) {
            KYC kyc = objectMapper.convertValue(kycNode, KYC.class);
            kyc.setUser(savedUser);
            kycRepository.save(kyc);
        }

        // --- Passport ---
        JsonNode passportNode = parsedSections.get("passport");
        if (passportNode != null) {
            Passport passport = objectMapper.convertValue(passportNode, Passport.class);
            passport.setUser(user);
            passportRepository.save(passport);
        }

        // --- Family ---
        JsonNode familyNode = parsedSections.get("family");
        if (familyNode != null) {
            Family family = objectMapper.convertValue(familyNode, Family.class);
            family.setUser(user);
            familyRepository.save(family);
        }
        // --- Education ---
        JsonNode educationNode = parsedSections.get("education");
        if (educationNode != null) {
            Education education = objectMapper.convertValue(educationNode, Education.class);
            education.setUser(user);
            educationRepository.save(education);
        }
        // --- Previous Employment (Array) ---
        JsonNode prevNode = parsedSections.get("previousEmployment");
        if (prevNode != null && prevNode.isArray()) {
            List<PreviousEmployment> previousEmploymentList = new ArrayList<>();
            for (JsonNode node : prevNode) {
                PreviousEmployment prev = objectMapper.convertValue(node, PreviousEmployment.class);
                prev.setUser(user);
                previousEmploymentList.add(prev);
            }
            previousEmploymentRepository.saveAll(previousEmploymentList);
        }
        // --- Skills (Array) ---
        JsonNode skillsNode = parsedSections.get("skills");
        if (skillsNode != null && skillsNode.isArray()) {
            List<Skills> skillsList = new ArrayList<>();
            for (JsonNode node : skillsNode) {
                Skills skill = objectMapper.convertValue(node, Skills.class);
                skill.setUser(user);
                skillsList.add(skill);
            }
            skillRepository.saveAll(skillsList);
        }
        // --- Certification (Array) ---
        JsonNode certificationNode = parsedSections.get("certification");
        if (certificationNode != null && certificationNode.isArray()) {
            List<Certification> certificationList = new ArrayList<>();
            for (JsonNode node : certificationNode) {
                Certification cert = objectMapper.convertValue(node, Certification.class);
                cert.setUser(user);
                certificationList.add(cert);
            }
            certificationRepository.saveAll(certificationList);
        }
        // --- Resume ---
        JsonNode resumeNode = parsedSections.get("resume");
        if (resumeNode != null) {
            Resume resume = objectMapper.convertValue(resumeNode, Resume.class);
            resume.setUser(user);
            resumeRepository.save(resume);
        }
        // --- Final (aFinal section) ---
        JsonNode afinalNode = parsedSections.get("aFinal");
        if (afinalNode != null) {
            Final afinal = objectMapper.convertValue(afinalNode, Final.class);
            afinal.setUser(user);
            finalRepository.save(afinal);
        }
        List<String> required = Arrays.asList(
                "personal", "kyc", "passport", "family",
                "education", "previousEmployment", "skills",
                "certification", "resume", "aFinal"
        );

        boolean allPresent = true;
        for (String key : required) {
            JsonNode node = parsedSections.get(key);
            if (node == null || node.isNull()) {
                allPresent = false;
                break;
            }
        }

        if (allPresent) {
            // mark user completed and delete pending
            User u = userRepo.findById(user.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            u.setOnboardingCompleted(true);
            userRepo.save(u);

            pendingUserRepo.deleteByEmployeeId(u.getEmployeeId());
            System.out.println(" PendingUser deleted for employeeId: " + u.getEmployeeId());

        }
    }

}



