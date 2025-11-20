package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.Announcements;
import java.util.List;

public interface AnnouncementService {

    List<Announcements> getAllAnnouncements();

    Announcements createAnnouncement(Announcements announcementJson);

    void deleteAnnouncement(Long id);

    Announcements updateAnnouncement(Long id, Announcements announcements);
}