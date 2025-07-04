package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.Announcements;
import com.tvm.internal.tvm_internal_project.request.AnnouncementFilterRequest;
import com.tvm.internal.tvm_internal_project.serviceImpl.AnnouncementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementServiceImpl announcementService;

//    @PostMapping(value = "/add", consumes = "multipart/form-data")
//    public ResponseEntity<Announcements> createAnnouncement(@RequestPart("announcement") String announcementJson, @RequestPart(value = "attachment", required = false) MultipartFile attachment) throws IOException {
//
//        Announcements createdAnnouncement = announcementService.createAnnouncement(announcementJson, attachment);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnnouncement);
//    }

    @PostMapping("/add")
    public ResponseEntity<Announcements> createAnnouncement(@RequestBody Announcements announcements) {
        Announcements createAnnouncement = announcementService.createAnnouncement(announcements);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAnnouncement);
    }

//    @PutMapping(value = "/update/{id}", consumes = "multipart/form-data")
//    public ResponseEntity<Announcements> updateAnnouncement(@PathVariable Long id, @RequestPart("announcement") String announcementJson, @RequestPart(value = "attachment", required = false) MultipartFile attachment) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Announcements updatedAnnouncement = objectMapper.readValue(announcementJson, Announcements.class);
//
//            if (attachment != null) {
//                updatedAnnouncement.setAttachment(attachment.getBytes());
//            }
//            Announcements announcement = announcementService.updateAnnouncement(id, updatedAnnouncement, attachment);
//            return ResponseEntity.ok(announcement);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping
    public ResponseEntity<List<Announcements>> getAllAnnouncements() {

        return ResponseEntity.ok(announcementService.getAllAnnouncements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Announcements> getAnnouncementById(@PathVariable Long id) {
        Optional<Announcements> announcement = announcementService.getAnnouncementById(id);

        if (announcement.isPresent()) {
            return ResponseEntity.ok(announcement.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/filter")
    public ResponseEntity<List<Announcements>> getFilteredAnnouncements(@RequestBody AnnouncementFilterRequest filterRequest) {
        List<Announcements> announcements = announcementService.getFilteredAnnouncements(filterRequest);
        return ResponseEntity.ok(announcements);
    }
}