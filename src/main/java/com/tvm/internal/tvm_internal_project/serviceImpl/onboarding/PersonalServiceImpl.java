package com.tvm.internal.tvm_internal_project.serviceImpl.onboarding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvm.internal.tvm_internal_project.exception.PersonalNotFoundException;
import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.model.onboarding.*;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import com.tvm.internal.tvm_internal_project.repo.onboarding.*;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import com.tvm.internal.tvm_internal_project.DTO.WishesDto;
import com.tvm.internal.tvm_internal_project.service.onboarding.PersonalService;
import com.tvm.internal.tvm_internal_project.serviceImpl.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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
    private DocumentsRepository documentRepo;
    @Autowired
    private UserRepo userRepository;
    @Autowired
    EmailService emailService;

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
    public void sendScheduledWishes() {
        Map<String, List<WishesDto>> wishesList = prepareWishes();
        wishesList.remove("Onboarded");
        if(!wishesList.get("Anniversary").isEmpty()){
            emailService.sendAnniversaryWishes(wishesList.get("Anniversary"));
        }
        if(!wishesList.get("BirthDay").isEmpty()){
            emailService.sendBirthdayWishes(wishesList.get("BirthDay"));
        }
    }

    public Map<String, List<WishesDto>> prepareWishes() {
        LocalDate localDate = LocalDate.now();
        List<User> birthday = userRepo.findBirthdays(localDate.getMonthValue(), localDate.getDayOfMonth())
                .orElse(Collections.emptyList());
        List<User> anniversary = userRepo.findAnniversaries(localDate.getMonthValue(), localDate.getDayOfMonth())
                .orElse(Collections.emptyList());
        List<User> onboarded = userRepo.findTodayOnboardings(localDate.getDayOfYear(), localDate.getMonthValue(), localDate.getDayOfMonth())
                .orElse(Collections.emptyList());
        Map<String, List<WishesDto>> wishesList = new HashMap<>();
        wishesList.put("BirthDay", getWishesData(birthday));
        wishesList.put("Anniversary", getWishesData(anniversary));
        wishesList.put("Onboarded", getWishesData(onboarded));
        return wishesList;
    }

   public List<WishesDto> getWishesData(List<User> users){
       return users.stream().map(emp->{
           WishesDto wishesDto=new WishesDto();
           wishesDto.setName(emp.getFullName());
           wishesDto.setJoiningDate(emp.getJoiningDate());
           wishesDto.setDob(emp.getDob());
           wishesDto.setEmail(emp.getEmail());
           if(wishesDto.getJoiningDate()!=null){
               LocalDate joinDate = wishesDto.getJoiningDate()
                       .toInstant()
                       .atZone(ZoneId.systemDefault())
                       .toLocalDate();
               LocalDate today = LocalDate.now();
               Period diff = Period.between(joinDate, today);
               String totalExp = diff.getYears() + " years " + diff.getMonths() + " months";
               wishesDto.setTotalExp(totalExp);
           }
           wishesDto.setpSizePhoto(
                   documentRepo.findByUserEmployeeId(emp.getEmployeeId())
                           .map(Documents::getpSizePhoto) // only call if present
                           .orElse(null)                  // fallback if missing
           );
           return wishesDto;
       }).toList();
   }


    private final ObjectMapper objectMappers = new ObjectMapper();
    @Override
    @Transactional
    public void processOnboardingDataWithUser(Map<String, JsonNode> parsedSections, User user) {
        if (user.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee ID is required");
        }
        User existingUser = userRepository.findByEmployeeId(user.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("User not found with employeeId: "));
        if (existingUser != null) {
            existingUser.setFullName(user.getFullName());
            existingUser.setEmail(user.getEmail());
            existingUser.setMobile(user.getMobile());
            user = existingUser;
        }
        User savedUser = userRepository.save(user);
        JsonNode personalNode = parsedSections.get("personal");
        if (personalNode != null) {
            Personal personal = objectMapper.convertValue(personalNode, Personal.class);
            personal.setUser(user);// Link the saved user
            personalRepository.save(personal);
            System.out.println("Employee ID linked to personal: " + user.getEmployeeId());
        }
        JsonNode kycNode = parsedSections.get("kyc");
        if (kycNode != null) {
            KYC kyc = objectMapper.convertValue(kycNode, KYC.class);
            kyc.setUser(savedUser);
            kycRepository.save(kyc);
        }
        JsonNode passportNode = parsedSections.get("passport");
        if (passportNode != null) {
            Passport passport = objectMapper.convertValue(passportNode, Passport.class);
            passport.setUser(user);
            passportRepository.save(passport);
        }
        JsonNode familyNode = parsedSections.get("family");
        if (familyNode != null) {
            Family family = objectMapper.convertValue(familyNode, Family.class);
            family.setUser(user);
            familyRepository.save(family);
        }
        JsonNode educationNode = parsedSections.get("education");
        if (educationNode != null) {
            Education education = objectMapper.convertValue(educationNode, Education.class);
            education.setUser(user);
            educationRepository.save(education);
        }
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
        JsonNode resumeNode = parsedSections.get("resume");
        if (resumeNode != null) {
            Resume resume = objectMapper.convertValue(resumeNode, Resume.class);
            resume.setUser(user);
            resumeRepository.save(resume);
        }
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
            User u = userRepo.findById(user.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            u.setOnboardingCompleted(true);
            u.setJoiningDate(new Date());
            userRepo.save(u);
            pendingUserRepo.deleteByEmpId(u.getEmployeeId());
            System.out.println(" PendingUser deleted for employeeId: " + u.getEmployeeId());

        }
    }

}



