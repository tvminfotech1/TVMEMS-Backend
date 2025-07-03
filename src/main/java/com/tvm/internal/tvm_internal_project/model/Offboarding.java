package com.tvm.internal.tvm_internal_project.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Offboarding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String employeeId;
    private String reason;
    private String explanation;
    private String date;
    private String status;

    //extra fields for future
    private LocalDate noticePeriodStartDate;
    private LocalDate noticePeriodEndDate;
    private LocalDate relievingDate;

    @Enumerated(EnumType.STRING)
    private ExitReason exitReason;

    @Enumerated(EnumType.STRING)
    private ExitType exitType;

    @Enumerated(EnumType.STRING)
    private ExitStatus exitStatus;
    private String exitInterviewFeedback;
    private String exitInterviewer;
    private Boolean offboardingDocumentsSubmitted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    public Boolean getAcknowledge() {
        return acknowledge;
    }

    public void setAcknowledge(Boolean acknowledge) {
        this.acknowledge = acknowledge;
    }

    private Boolean acknowledge;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getNoticePeriodStartDate() {
        return noticePeriodStartDate;
    }

    public void setNoticePeriodStartDate(LocalDate noticePeriodStartDate) {
        this.noticePeriodStartDate = noticePeriodStartDate;
    }

    public LocalDate getNoticePeriodEndDate() {
        return noticePeriodEndDate;
    }

    public void setNoticePeriodEndDate(LocalDate noticePeriodEndDate) {
        this.noticePeriodEndDate = noticePeriodEndDate;
    }

    public LocalDate getRelievingDate() {
        return relievingDate;
    }

    public void setRelievingDate(LocalDate relievingDate) {
        this.relievingDate = relievingDate;
    }

    public ExitReason getExitReason() {
        return exitReason;
    }

    public void setExitReason(ExitReason exitReason) {
        this.exitReason = exitReason;
    }

    public ExitType getExitType() {
        return exitType;
    }

    public void setExitType(ExitType exitType) {
        this.exitType = exitType;
    }

    public ExitStatus getExitStatus() {
        return exitStatus;
    }

    public void setExitStatus(ExitStatus exitStatus) {
        this.exitStatus = exitStatus;
    }

    public String getExitInterviewFeedback() {
        return exitInterviewFeedback;
    }

    public void setExitInterviewFeedback(String exitInterviewFeedback) {
        this.exitInterviewFeedback = exitInterviewFeedback;
    }

    public String getExitInterviewer() {
        return exitInterviewer;
    }

    public void setExitInterviewer(String exitInterviewer) {
        this.exitInterviewer = exitInterviewer;
    }

    public Boolean getOffboardingDocumentsSubmitted() {
        return offboardingDocumentsSubmitted;
    }

    public void setOffboardingDocumentsSubmitted(Boolean offboardingDocumentsSubmitted) {
        this.offboardingDocumentsSubmitted = offboardingDocumentsSubmitted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}

