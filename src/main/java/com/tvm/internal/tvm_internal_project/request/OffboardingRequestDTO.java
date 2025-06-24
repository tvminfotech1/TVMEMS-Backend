package com.tvm.internal.tvm_internal_project.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OffboardingRequestDTO {
    private String name;
    private Long employeeId;
    private String reason;
    private String explanation;
    private String date;
    private String status;
    private LocalDate noticePeriodStartDate;
    private LocalDate noticePeriodEndDate;
    private LocalDate relievingDate;
    private String exitReason;
    private String exitType;
    private String exitStatus;
    private String exitInterviewFeedback;
    private String exitInterviewer;
    private Boolean offboardingDocumentsSubmitted;
}
