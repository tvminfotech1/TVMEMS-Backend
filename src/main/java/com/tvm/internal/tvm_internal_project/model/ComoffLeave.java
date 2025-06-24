package com.tvm.internal.tvm_internal_project.model;

public enum ComoffLeave {
    PENDING, APPROVED, REJECTED, CANCELLED;

    public static boolean isValid(String value) {
        try {
            ExitReason.valueOf(value.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
