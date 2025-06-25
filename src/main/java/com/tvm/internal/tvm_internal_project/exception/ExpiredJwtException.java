package com.tvm.internal.tvm_internal_project.exception;

public class ExpiredJwtException extends RuntimeException {

    public ExpiredJwtException(String message) {
        super(message);
    }
}