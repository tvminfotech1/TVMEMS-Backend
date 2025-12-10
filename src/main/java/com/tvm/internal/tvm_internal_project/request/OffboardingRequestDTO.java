package com.tvm.internal.tvm_internal_project.request;

import lombok.Data;
@Data
public class OffboardingRequestDTO {
    private String name;
    private String employeeId;
    private String reason;
    private String explanation;
    private String date;
    private String status;
    private Boolean acknowledge;
}
