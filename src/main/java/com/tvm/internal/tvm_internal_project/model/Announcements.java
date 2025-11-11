package com.tvm.internal.tvm_internal_project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "announcements")
@Data
public class Announcements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;


    private String place;

    @Column(length = 1000)
    private String description;





//    @Column(name = "message", length = 500)
//    private String message;
//
//    @Lob
//    @Column(name = "attachment", columnDefinition = "BLOB")
//    private byte[] attachment;
//
//    @Column(name = "category")
//    private String category;

//    @Column(name = "expiry")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    private Date expiry;

//    @Column(name = "location")
//    private String location;

//    @Column(name = "notifyAll")
//    private boolean notifyAll;
//
//    @Column(name = "pinAllAnnouncement")
//    private boolean pinAllAnnouncement;
//
//    @Column(name = "disableComments")
//    private boolean disableComments;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public byte[] getAttachment() {
//        return attachment;
//    }
//
//    public void setAttachment(byte[] attachment) {
//        this.attachment = attachment;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public Date getExpiry() {
//        return expiry;
//    }
//
//    public void setExpiry(Date expiry) {
//        this.expiry = expiry;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public boolean isNotifyAll() {
//        return notifyAll;
//    }
//
//    public void setNotifyAll(boolean notifyAll) {
//        this.notifyAll = notifyAll;
//    }
//
//    public boolean isPinAllAnnouncement() {
//        return pinAllAnnouncement;
//    }
//
//    public void setPinAllAnnouncement(boolean pinAllAnnouncement) {
//        this.pinAllAnnouncement = pinAllAnnouncement;
//    }
//
//    public boolean isDisableComments() {
//        return disableComments;
//    }
//
//    public void setDisableComments(boolean disableComments) {
//        this.disableComments = disableComments;
//    }
}
