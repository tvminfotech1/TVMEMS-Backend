package com.tvm.internal.tvm_internal_project.response;

import lombok.Data;

@Data
public class ResponseStructure<T> {
    private String message;
    private T body;
    private int statusCode;
}
