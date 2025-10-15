package com.tvm.internal.tvm_internal_project.serviceImpl.onboarding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvm.internal.tvm_internal_project.exception.PersonalNotFoundException;
import com.tvm.internal.tvm_internal_project.model.onboarding.*;
import com.tvm.internal.tvm_internal_project.repo.EmployeeRepo;
import com.tvm.internal.tvm_internal_project.repo.onboarding.*;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.response.WishesDto;
import com.tvm.internal.tvm_internal_project.service.onboarding.PersonalService;
import io.jsonwebtoken.io.IOException;
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

    @Autowired private KYCRepository kycRepository;
    @Autowired private PassportRepository passportRepository;
    @Autowired private FamilyRepository familyRepository;
    @Autowired private EducationRepository educationRepository;
    @Autowired private SkillsRepository skillRepository;
    @Autowired private CertificationRepository certificationRepository;
    @Autowired private ResumeRepository resumeRepository;
    @Autowired private FinalRepository finalRepository;
    @Autowired
    private PreviousEmploymentRepository previousEmploymentRepository;


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
    public ResponseEntity<String> savedetails( Personal personal){
         personalRepository.save(personal);
         return ResponseEntity.ok("Succesfully");

    }

//    @Override
//    public void saveAll(OnboardingDTO dto) {
//
//    }

    private final ObjectMapper objectMappers = new ObjectMapper();


    @Override
    public void processOnboardingData(Map<String, JsonNode> sections) {
        try {
            JsonNode personal = sections.get("personal");
            JsonNode kyc = sections.get("kyc");
            JsonNode passport = sections.get("passport");
            JsonNode family = sections.get("family");
            JsonNode previousEmployment = sections.get("previousEmployment");
            JsonNode education = sections.get("education");
            JsonNode skills = sections.get("skills");
            JsonNode certification = sections.get("certification");
            JsonNode resume = sections.get("resume");
            JsonNode afinal = sections.get("aFinal");


            if (personal != null) {
                Personal personalEntity = objectMapper.treeToValue(personal, Personal.class);
                personalRepository.save(personalEntity);
            }

            if (kyc != null) {
                KYC kycEntity = objectMapper.treeToValue(kyc, KYC.class);
                kycRepository.save(kycEntity);
            }

            if (passport != null) {
                Passport passportEntity = objectMapper.treeToValue(passport, Passport.class);
                passportRepository.save(passportEntity);
            }

            if (family != null) {
                Family familyEntity = objectMapper.treeToValue(family, Family.class);
                familyRepository.save(familyEntity);
            }

            if (education != null) {
                Education educationEntity = objectMapper.treeToValue(education, Education.class);
                educationRepository.save(educationEntity);
            }

            if (skills != null && skills.isArray()) {
                for (JsonNode node : skills) {
                    Skills skill = objectMapper.treeToValue(node, Skills.class);
                    skillRepository.save(skill);
                }
            }

            if (certification != null && certification.isArray()) {
                for (JsonNode node : certification) {
                    Certification cert = objectMapper.treeToValue(node, Certification.class);
                    certificationRepository.save(cert);
                }
            }

            if (resume != null) {
                Resume resumeEntity = objectMapper.treeToValue(resume, Resume.class);
                resumeRepository.save(resumeEntity);
            }

            if (previousEmployment != null && previousEmployment.isArray()) {
                for (JsonNode node : previousEmployment){
                    PreviousEmployment previousEntity = objectMapper.treeToValue(node, PreviousEmployment.class);
                    previousEmploymentRepository.save(previousEntity);
                }
            }

            if (afinal != null) {
                Final finalEntity = objectMapper.treeToValue(afinal, Final.class);
                finalRepository.save(finalEntity);
            }

            System.out.println(" All Onboarding Sections Saved to Database Successfully!");

        } catch (Exception e) {
            // Handle all exceptions here so method compiles
            System.err.println(" Error while saving onboarding data: " + e.getMessage());
            e.printStackTrace();
        }
    }

}



