package com.tvm.internal.tvm_internal_project.request;

import lombok.Data;

@Data
public class ApplicationRequest {
    private Long candidateId;
    private Long jobId;
    private String status;
}
