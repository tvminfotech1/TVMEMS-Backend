package com.tvm.internal.tvm_internal_project.model;

public enum ExitReason {
    RESIGNATION, TERMINATION, RETIREMENT, CONTRACT_END, OTHER;

    public static boolean isValid(String value) {
        try {
            ExitReason.valueOf(value.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
