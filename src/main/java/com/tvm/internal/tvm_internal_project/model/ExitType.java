package com.tvm.internal.tvm_internal_project.model;

public enum ExitType {
    VOLUNTARY, INVOLUNTARY;

    public static boolean isValid(String value) {
        try {
            ExitType.valueOf(value.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
