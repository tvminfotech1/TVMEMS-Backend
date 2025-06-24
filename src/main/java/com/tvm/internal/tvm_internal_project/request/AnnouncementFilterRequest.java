package com.tvm.internal.tvm_internal_project.request;

public class AnnouncementFilterRequest {
    private String category;
    private String location;
    private Boolean status;
    private Boolean pinAllAnnouncement;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getPinAllAnnouncement() {
        return pinAllAnnouncement;
    }

    public void setPinAllAnnouncement(Boolean pinAllAnnouncement) {
        this.pinAllAnnouncement = pinAllAnnouncement;
    }
}