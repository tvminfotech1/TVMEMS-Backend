package com.tvm.internal.tvm_internal_project.response;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class OffboardingResponseDTO {
    private Long id;
    private String name;
    @Column(name = "employee_id")
    private String employeeId;
    private String reason;
    private String explanation;
    private String date;
    private String status;
    private Boolean acknowledge;
}
