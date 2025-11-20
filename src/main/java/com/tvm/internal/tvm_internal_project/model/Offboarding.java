package com.tvm.internal.tvm_internal_project.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Offboarding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "employee_id")
    private String employeeId;
    private String reason;
    private String explanation;
    private String date;
    private String status;
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
    private Boolean acknowledge;

}

