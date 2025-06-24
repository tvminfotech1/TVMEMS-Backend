package com.tvm.internal.tvm_internal_project.model;

public enum ExitStatus {
    INITIATED, IN_PROGRESS, COMPLETED;

    public static boolean isValid(String value) {
        try {
            ExitStatus.valueOf(value.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
