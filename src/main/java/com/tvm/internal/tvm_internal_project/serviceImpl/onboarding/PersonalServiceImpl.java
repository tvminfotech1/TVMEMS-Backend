package com.tvm.internal.tvm_internal_project.serviceImpl.onboarding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvm.internal.tvm_internal_project.DTO.DocumentStatusDto;
import com.tvm.internal.tvm_internal_project.DTO.OnboardingResponseDTO;
import com.tvm.internal.tvm_internal_project.exception.ResourceNotFound;
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
import java.util.stream.Collectors;

@Service
public class PersonalServiceImpl implements PersonalService {
    @Autowired
    private PersonalRepository personalRepository;
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
            throw new ResourceNotFound("Personal Id not found :" + id);
        }
        structure.setMessage("Personal id Successfully found:" + id);
        structure.setBody(dbPersonal.get());
        structure.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<List<Personal>>> findAllPersonal() {
        ResponseStructure<List<Personal>> structure = new ResponseStructure<>();
        List<Personal> personals = personalRepository.findAll();
        if (personals.isEmpty()) {
            throw new ResourceNotFound("Personal Details not Found");
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
            throw new ResourceNotFound("Personal id not found:" + id);
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
                           .map(Documents::getpSizePhoto)
                           .orElse(null)
           );
           return wishesDto;
       }).toList();
   }

    public ResponseEntity<String> savedetails(Personal personal) {
        personalRepository.save(personal);
        return ResponseEntity.ok("Succesfully");

    }

    private final ObjectMapper objectMappers = new ObjectMapper();
    @Override
    @Transactional
    public void processOnboardingDataWithUser(Map<String, JsonNode> parsedSections, User user) {
        if (user.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee ID is required");
        }

        Long employeeId = user.getEmployeeId();

        Optional<User> maybeExisting = userRepository.findByEmployeeId(employeeId);
        User persistedUser;
        if (maybeExisting.isPresent()) {
            persistedUser = maybeExisting.get();
            persistedUser.setFullName(user.getFullName() != null ? user.getFullName() : persistedUser.getFullName());
            persistedUser.setEmail(user.getEmail() != null ? user.getEmail() : persistedUser.getEmail());
            persistedUser.setMobile(user.getMobile() != null ? user.getMobile() : persistedUser.getMobile());
        } else {
            persistedUser = user;
        }
        User savedUser = userRepository.save(persistedUser);

        JsonNode personalNode = parsedSections.get("personal");
        if (personalNode != null && !personalNode.isNull()) {
            Personal incoming = objectMapper.convertValue(personalNode, Personal.class);
            upsertPersonal(savedUser, incoming);
        }

        JsonNode kycNode = parsedSections.get("kyc");
        if (kycNode != null && !kycNode.isNull()) {
            KYC incoming = objectMapper.convertValue(kycNode, KYC.class);
            upsertKyc(savedUser, incoming);
        }

        JsonNode passportNode = parsedSections.get("passport");
        if (passportNode != null && !passportNode.isNull()) {
            Passport incoming = objectMapper.convertValue(passportNode, Passport.class);
            upsertPassport(savedUser, incoming);
        }

        JsonNode familyNode = parsedSections.get("family");
        if (familyNode != null && !familyNode.isNull()) {
            Family incoming = objectMapper.convertValue(familyNode, Family.class);
            upsertFamily(savedUser, incoming);
        }

        JsonNode educationNode = parsedSections.get("education");

        if (educationNode != null && !educationNode.isNull()) {

            Education incoming = objectMapper.convertValue(educationNode, Education.class);

            Education existing = educationRepository.findByUserEmployeeId(user.getEmployeeId())
                    .stream()
                    .findFirst()
                    .orElse(null);

            if (existing != null) {
                existing.setQualification(incoming.getQualification());
                existing.setSpecilization(incoming.getSpecilization());
                existing.setInstituteName(incoming.getInstituteName());
                existing.setUniversityName(incoming.getUniversityName());
                existing.setTime(incoming.getTime());
                existing.setFromDate(incoming.getFromDate());
                existing.setToDate(incoming.getToDate());
                existing.setPercentage(incoming.getPercentage());
                existing.setRollNo(incoming.getRollNo());
                existing.setEducationType(incoming.getEducationType());

                educationRepository.save(existing);

            } else {
                incoming.setUser(user);
                educationRepository.save(incoming);
            }
        }


        JsonNode prevNode = parsedSections.get("previousEmployment");
        if (prevNode != null && !prevNode.isNull()) {
            List<PreviousEmployment> incomingPrev = objectMapper.convertValue(
                    prevNode,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, PreviousEmployment.class)
            );
            syncPreviousEmploymentList(savedUser, incomingPrev);
        }

        JsonNode skillsNode = parsedSections.get("skills");
        if (skillsNode != null && !skillsNode.isNull()) {
            List<Skills> incomingSkills = objectMapper.convertValue(
                    skillsNode,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Skills.class)
            );
            syncSkillsList(savedUser, incomingSkills);
        }

        JsonNode certificationNode = parsedSections.get("certification");
        if (certificationNode != null && !certificationNode.isNull()) {
            List<Certification> incomingCerts = objectMapper.convertValue(
                    certificationNode,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Certification.class)
            );
            syncCertificationList(savedUser, incomingCerts);
        }

        JsonNode resumeNode = parsedSections.get("resume");
        if (resumeNode != null && !resumeNode.isNull()) {
            Resume incoming = objectMapper.convertValue(resumeNode, Resume.class);
            upsertResume(savedUser, incoming);
        }

        JsonNode afinalNode = parsedSections.get("aFinal");
        if (afinalNode != null && !afinalNode.isNull()) {
            Final incoming = objectMapper.convertValue(afinalNode, Final.class);
            upsertFinal(savedUser, incoming);
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
            User u = userRepo.findById(savedUser.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            u.setOnboardingCompleted(true);
            u.setJoiningDate(new Date());
            userRepo.save(u);

            pendingUserRepo.deleteByEmpId(u.getEmployeeId());
        }
    }

    private void upsertPersonal(User user, Personal incoming) {
        Optional<Personal> existing = personalRepository.findByUserEmployeeId(user.getEmployeeId());
        if (existing.isPresent()) {
            Personal p = existing.get();
            p.setFname(incoming.getFname());
            p.setMname(incoming.getMname());
            p.setLname(incoming.getLname());
            p.setEmail(incoming.getEmail());
            p.setGender(incoming.getGender());
            p.setBloodGroup(incoming.getBloodGroup());
            p.setDob(incoming.getDob());
            p.setMarital(incoming.getMarital());
            p.setMarriegedate(incoming.getMarriegedate());
            p.setCurrent_address(incoming.getCurrent_address());
            p.setCurrent_country(incoming.getCurrent_country());
            p.setCurrent_state(incoming.getCurrent_state());
            p.setCurrent_city(incoming.getCurrent_city());
            p.setCurrent_pincode(incoming.getCurrent_pincode());
            p.setCurrent_contact(incoming.getCurrent_contact());
            p.setPermanent_address(incoming.getPermanent_address());
            p.setPermanent_country(incoming.getPermanent_country());
            p.setPermanent_state(incoming.getPermanent_state());
            p.setPermanent_city(incoming.getPermanent_city());
            p.setPermanent_pincode(incoming.getPermanent_pincode());
            p.setPermanent_contact(incoming.getPermanent_contact());
            p.setBcp_address(incoming.getBcp_address());
            p.setBcp_country(incoming.getBcp_country());
            p.setBcp_state(incoming.getBcp_state());
            p.setBcp_city(incoming.getBcp_city());
            p.setBcp_pincode(incoming.getBcp_pincode());
            p.setEmergency_contact_name(incoming.getEmergency_contact_name());
            p.setEmergency_contact_number(incoming.getEmergency_contact_number());
            p.setEmergency_relationship(incoming.getEmergency_relationship());
            p.setExp_year(incoming.getExp_year());
            p.setExp_month(incoming.getExp_month());
            p.setRelevantYear(incoming.getRelevantYear());
            personalRepository.save(p);
        } else {
            incoming.setUser(user);
            personalRepository.save(incoming);
        }
    }

    private void upsertKyc(User user, KYC incoming) {
        Optional<KYC> existing = kycRepository.findByUserEmployeeId(user.getEmployeeId());
        if (existing.isPresent()) {
            KYC k = existing.get();
            k.setPan(incoming.getPan());
            k.setPanName(incoming.getPanName());
            k.setAadhar(incoming.getAadhar());
            k.setAadharName(incoming.getAadharName());
            k.setUan(incoming.getUan());
            k.setPf(incoming.getPf());
            k.setHdfc(incoming.getHdfc());
            kycRepository.save(k);
        } else {
            incoming.setUser(user);
            kycRepository.save(incoming);
        }
    }

    private void upsertPassport(User user, Passport incoming) {
        Optional<Passport> existing = passportRepository.findByUserEmployeeId(user.getEmployeeId());
        if (existing.isPresent()) {
            Passport p = existing.get();
            p.setNationality(incoming.getNationality());
            p.setIfPassport(incoming.getIfPassport());
            p.setPassportNumber(incoming.getPassportNumber());
            passportRepository.save(p);
        } else {
            incoming.setUser(user);
            passportRepository.save(incoming);
        }
    }

    private void upsertFamily(User user, Family incoming) {
        Optional<Family> existing = familyRepository.findByUserEmployeeId(user.getEmployeeId());
        if (existing.isPresent()) {
            Family f = existing.get();
            f.setFatherName(incoming.getFatherName());
            f.setFatherDOB(incoming.getFatherDOB());
            f.setMotherName(incoming.getMotherName());
            f.setMotherDOB(incoming.getMotherDOB());
            f.setSpouseName(incoming.getSpouseName());
            f.setSpouseDOB(incoming.getSpouseDOB());
            f.setSpouseGender(incoming.getSpouseGender());
            f.setChildren(incoming.getChildren());
            familyRepository.save(f);
        } else {
            incoming.setUser(user);
            familyRepository.save(incoming);
        }
    }

    private void upsertResume(User user, Resume incoming) {
        Optional<Resume> existing = resumeRepository.findByUserEmployeeId(user.getEmployeeId());
        if (existing.isPresent()) {
            Resume r = existing.get();
            r.setAchievements(incoming.getAchievements());
            r.setResumeCate(incoming.getResumeCate());
            resumeRepository.save(r);
        } else {
            incoming.setUser(user);
            resumeRepository.save(incoming);
        }
    }

    private void upsertFinal(User user, Final incoming) {
        Optional<Final> existing = finalRepository.findByUserEmployeeId(user.getEmployeeId());
        if (existing.isPresent()) {
            Final f = existing.get();
            f.setChecked(incoming.isChecked());
            f.setSignature(incoming.getSignature());
            f.setDate(incoming.getDate());
            finalRepository.save(f);
        } else {
            incoming.setUser(user);
            finalRepository.save(incoming);
        }
    }

    private void syncEducationList(User user, List<Education> incoming) {
        List<Education> existing = educationRepository.findByUserEmployeeId(user.getEmployeeId());
        Map<Integer, Education> existingById = existing.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(Education::getId, e -> e));

        Set<Integer> incomingIds = new HashSet<>();
        for (Education inc : incoming) {
            if (inc.getId() != null && existingById.containsKey(inc.getId())) {
                Education db = existingById.get(inc.getId());
                db.setQualification(inc.getQualification());
                db.setSpecilization(inc.getSpecilization());
                db.setInstituteName(inc.getInstituteName());
                db.setUniversityName(inc.getUniversityName());
                db.setTime(inc.getTime());
                db.setFromDate(inc.getFromDate());
                db.setToDate(inc.getToDate());
                db.setPercentage(inc.getPercentage());
                db.setRollNo(inc.getRollNo());
                db.setEducationType(inc.getEducationType());
                educationRepository.save(db);
                incomingIds.add(db.getId());
            } else {
                inc.setUser(user);
                Education saved = educationRepository.save(inc);
                if (saved.getId() != null) incomingIds.add(saved.getId());
            }
        }

        for (Education db : existing) {
            if (db.getId() != null && !incomingIds.contains(db.getId())) {
                educationRepository.deleteById(db.getId());
            }
        }
    }

    private void syncPreviousEmploymentList(User user, List<PreviousEmployment> incoming) {
        List<PreviousEmployment> existing = previousEmploymentRepository.findByUserEmployeeId(user.getEmployeeId());
        Map<Integer, PreviousEmployment> existingById = existing.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(PreviousEmployment::getId, e -> e));

        Set<Integer> incomingIds = new HashSet<>();
        for (PreviousEmployment inc : incoming) {
            if (inc.getId() != null && existingById.containsKey(inc.getId())) {
                PreviousEmployment db = existingById.get(inc.getId());
                db.setCompanyName(inc.getCompanyName());
                db.setDesignation(inc.getDesignation());
                db.setEmploymentType(inc.getEmploymentType());
                db.setStartDate(inc.getStartDate());
                db.setEndDate(inc.getEndDate());
                previousEmploymentRepository.save(db);
                incomingIds.add(db.getId());
            } else {
                inc.setUser(user);
                PreviousEmployment saved = previousEmploymentRepository.save(inc);
                if (saved.getId() != null) incomingIds.add(saved.getId());
            }
        }

        for (PreviousEmployment db : existing) {
            if (db.getId() != null && !incomingIds.contains(db.getId())) {
                previousEmploymentRepository.deleteById(db.getId());
            }
        }
    }

    private void syncSkillsList(User user, List<Skills> incoming) {
        List<Skills> existing = skillRepository.findByUserEmployeeId(user.getEmployeeId());
        Map<Integer, Skills> existingById = existing.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(Skills::getId, e -> e));

        Set<Integer> incomingIds = new HashSet<>();
        for (Skills inc : incoming) {
            if (inc.getId() != null && existingById.containsKey(inc.getId())) {
                Skills db = existingById.get(inc.getId());
                db.setSkillName(inc.getSkillName());
                db.setSkillCategories(inc.getSkillCategories());
                db.setVersionNum(inc.getVersionNum());
                db.setExperience_year(inc.getExperience_year());
                db.setExperience_month(inc.getExperience_month());
                db.setSelfRate(inc.getSelfRate());
                skillRepository.save(db);
                incomingIds.add(db.getId());
            } else {
                inc.setUser(user);
                Skills saved = skillRepository.save(inc);
                if (saved.getId() != null) incomingIds.add(saved.getId());
            }
        }

        for (Skills db : existing) {
            if (db.getId() != null && !incomingIds.contains(db.getId())) {
                skillRepository.deleteById(db.getId());
            }
        }
    }

    private void syncCertificationList(User user, List<Certification> incoming) {
        List<Certification> existing = certificationRepository.findByUserEmployeeId(user.getEmployeeId());
        Map<Integer, Certification> existingById = existing.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(Certification::getId, e -> e));

        Set<Integer> incomingIds = new HashSet<>();
        for (Certification inc : incoming) {
            if (inc.getId() != null && existingById.containsKey(inc.getId())) {
                Certification db = existingById.get(inc.getId());
                db.setCertificateName(inc.getCertificateName());
                db.setCertifiedBy(inc.getCertifiedBy());
                db.setCompletionDate(inc.getCompletionDate());
                db.setMarks(inc.getMarks());
                certificationRepository.save(db);
                incomingIds.add(db.getId());
            } else {
                inc.setUser(user);
                Certification saved = certificationRepository.save(inc);
                if (saved.getId() != null) incomingIds.add(saved.getId());
            }
        }

        for (Certification db : existing) {
            if (db.getId() != null && !incomingIds.contains(db.getId())) {
                certificationRepository.deleteById(db.getId());
            }
        }
    }


    @Override
    public OnboardingResponseDTO getFullOnboarding(Long employeeId) {
        User user = userRepo.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Documents documents = documentRepo.findByUserEmployeeId(employeeId).orElse(null);
        DocumentStatusDto docStatus = new DocumentStatusDto();

        if (documents != null) {
            docStatus.setPanCard(documents.getPanCard() != null);
            docStatus.setAadharCard(documents.getAadharCard() != null);
            docStatus.setPSizePhoto(documents.getpSizePhoto() != null);
            docStatus.setMatric(documents.getMatric() != null);
            docStatus.setIntermediate(documents.getIntermediate() != null);
            docStatus.setGraduationMarksheet(documents.getGraduationMarksheet() != null);
            docStatus.setPostGraduation(documents.getPostGraduation() != null);
            docStatus.setCheckLeaf(documents.getCheckLeaf() != null);
            docStatus.setPassbook(documents.getPassbook() != null);
        }

        OnboardingResponseDTO dto=new OnboardingResponseDTO();
        dto.setUser(user);
        dto.setPersonal(personalRepository.findByUserEmployeeId(employeeId).orElse(null));
        dto.setKyc(kycRepository.findByUserEmployeeId(employeeId).orElse(null));
        dto.setCertification(certificationRepository.findByUserEmployeeId(employeeId));
        dto.setFamily(familyRepository.findByUserEmployeeId(
                employeeId
        ).orElse(null));
        dto.setPassport(passportRepository.findByUserEmployeeId(employeeId).orElse(null));
        dto.setDocuments(docStatus);
        dto.setPreviousEmployment(previousEmploymentRepository.findByUserEmployeeId(employeeId));
        dto.setEducation(educationRepository.findByUserEmployeeId(employeeId));
        dto.setResume(resumeRepository.findByUserEmployeeId(employeeId).orElse(null));
        dto.setSkills(skillRepository.findByUserEmployeeId(employeeId));
        dto.setAFinal(finalRepository.findByUserEmployeeId(employeeId).orElse(null));

        return dto;
    }

    public String getDocumentBase64(Long employeeId, String docType) {

        Documents docs = documentRepo.findByUserEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Documents not found"));

        byte[] file = switch (docType.toLowerCase()) {
            case "pancard" -> docs.getPanCard();
            case "aadharcard" -> docs.getAadharCard();
            case "psizephoto" -> docs.getpSizePhoto();
            case "matric" -> docs.getMatric();
            case "intermediate" -> docs.getIntermediate();
            case "graduationmarksheet" -> docs.getGraduationMarksheet();
            case "postgraduation" -> docs.getPostGraduation();
            case "checkleaf" -> docs.getCheckLeaf();
            case "passbook" -> docs.getPassbook();
            default -> null;
        };

        if (file == null) {
            return null;
        }

        return Base64.getEncoder().encodeToString(file);
    }

}



