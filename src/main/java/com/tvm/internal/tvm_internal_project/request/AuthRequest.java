package com.tvm.internal.tvm_internal_project.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
    private Long mobile;
}