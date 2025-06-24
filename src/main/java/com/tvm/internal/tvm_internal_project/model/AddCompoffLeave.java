package com.tvm.internal.tvm_internal_project.model;

import jakarta.persistence.*;

@Entity
public class AddCompoffLeave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectName;
    private String duration;
    private String requestType;
    private String requestPerson;
    @Enumerated(EnumType.STRING)
    private ComoffLeave status;
    private String approvedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestPerson() {
        return requestPerson;
    }

    public void setRequestPerson(String requestPerson) {
        this.requestPerson = requestPerson;
    }

    public ComoffLeave getStatus() {
        return status;
    }

    public void setStatus(ComoffLeave status) {
        this.status = status;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
}
