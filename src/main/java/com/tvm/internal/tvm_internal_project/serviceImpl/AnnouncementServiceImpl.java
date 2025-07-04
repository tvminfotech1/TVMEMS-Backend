package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvm.internal.tvm_internal_project.model.Announcements;
import com.tvm.internal.tvm_internal_project.repo.AnnouncementRepo;
import com.tvm.internal.tvm_internal_project.request.AnnouncementFilterRequest;
import com.tvm.internal.tvm_internal_project.service.AnnouncementService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepo announcementRepo;

    @Autowired
    private EntityManager entityManager;

    public List<Announcements> getAllAnnouncements() {
        return announcementRepo.findAll();
    }

    public Optional<Announcements> getAnnouncementById(Long id) {
        return announcementRepo.findById(id);
    }

    public Announcements createAnnouncement(Announcements announcements){
        return announcementRepo.save(announcements);
    }

//    public Announcements createAnnouncement(String announcementJson, MultipartFile attachment) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Announcements announcement = objectMapper.readValue(announcementJson, Announcements.class);
//            if (attachment != null) {
//                announcement.setAttachment(attachment.getBytes());
//            }
//            return announcementRepo.save(announcement);
//        } catch (IOException e) {
//            throw new RuntimeException("Error processing announcement creation", e);
//        }
//    }

//    public Announcements updateAnnouncement(Long id, Announcements announcement, MultipartFile file) throws IOException {
//        announcement.setId(id);
//        if (file != null) {
//            announcement.setAttachment(file.getBytes());
//        }
//        return announcementRepo.save(announcement);
//    }

    public void deleteAnnouncement(Long id) {
        announcementRepo.deleteById(id);
    }

    public List<Announcements> getFilteredAnnouncements(AnnouncementFilterRequest filterRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Announcements> query = cb.createQuery(Announcements.class);
        Root<Announcements> root = query.from(Announcements.class);
        List<Predicate> predicates = new ArrayList<>();
        if (filterRequest.getCategory() != null) {
            predicates.add(cb.equal(root.get("category"), filterRequest.getCategory()));
        }
        if (filterRequest.getLocation() != null) {
            predicates.add(cb.equal(root.get("location"), filterRequest.getLocation()));
        }
        if (filterRequest.getStatus() != null) {
            predicates.add(cb.equal(root.get("notifyAll"), filterRequest.getStatus()));
        }
        if (filterRequest.getPinAllAnnouncement() != null) {
            predicates.add(cb.equal(root.get("pinAllAnnouncement"), filterRequest.getPinAllAnnouncement()));
        }
        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }
}