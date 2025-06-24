package com.tvm.internal.tvm_internal_project.model;

public enum Status {
    APPLIED, INTERVIEWED, SELECTED, REJECTED, OFFERED;

    public static boolean isValid(String value) {
        for (Status s : Status.values()) {
            if (s.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
