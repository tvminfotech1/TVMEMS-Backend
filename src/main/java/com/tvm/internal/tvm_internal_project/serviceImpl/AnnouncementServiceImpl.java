package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.model.Announcements;
import com.tvm.internal.tvm_internal_project.repo.AnnouncementRepo;
import com.tvm.internal.tvm_internal_project.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepo announcementRepo;

    public List<Announcements> getAllAnnouncements() {
        return announcementRepo.findAll();
    }

    public Announcements createAnnouncement(Announcements announcements){
        return announcementRepo.save(announcements);
    }

    public void deleteAnnouncement(Long id) {
        announcementRepo.deleteById(id);
    }

    public Announcements updateAnnouncement(Long id, Announcements updatedAnnouncement) {
        return announcementRepo.findById(id).map(existing -> {
            existing.setTitle(updatedAnnouncement.getTitle());
            existing.setDate(updatedAnnouncement.getDate());
            existing.setStartTime(updatedAnnouncement.getStartTime());
            existing.setEndTime(updatedAnnouncement.getEndTime());
            existing.setPlace(updatedAnnouncement.getPlace());
            existing.setDescription(updatedAnnouncement.getDescription());
            return announcementRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Announcement not found with id " + id));
    }

}