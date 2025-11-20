package com.tvm.internal.tvm_internal_project.controller;

import com.tvm.internal.tvm_internal_project.model.Announcements;
import com.tvm.internal.tvm_internal_project.serviceImpl.AnnouncementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementServiceImpl announcementService;

    @PostMapping("/add")
    public ResponseEntity<Announcements> createAnnouncement(@RequestBody Announcements announcements) {
        Announcements createAnnouncement = announcementService.createAnnouncement(announcements);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAnnouncement);
    }

    @GetMapping
    public ResponseEntity<List<Announcements>> getAllAnnouncements() {
        return ResponseEntity.ok(announcementService.getAllAnnouncements());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Announcements> updateAnnouncement(@PathVariable Long id, @RequestBody Announcements announcements) {
        Announcements updated = announcementService.updateAnnouncement(id, announcements);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
    }

}