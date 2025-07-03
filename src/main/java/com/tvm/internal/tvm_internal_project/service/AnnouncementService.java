package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.Announcements;
import com.tvm.internal.tvm_internal_project.request.AnnouncementFilterRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AnnouncementService {

    List<Announcements> getAllAnnouncements();

    Optional<Announcements> getAnnouncementById(Long id);

    Announcements createAnnouncement(Announcements announcementJson);

   // Announcements updateAnnouncement(Long id, Announcements announcement, MultipartFile file) throws IOException;

    void deleteAnnouncement(Long id);

    List<Announcements> getFilteredAnnouncements(AnnouncementFilterRequest filterRequest);
}