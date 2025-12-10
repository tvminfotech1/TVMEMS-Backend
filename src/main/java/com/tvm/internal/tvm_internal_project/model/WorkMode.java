package com.tvm.internal.tvm_internal_project.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum WorkMode {
    WFO, WFH, LEAVE, HOLIDAY;

    public static boolean isValid(String value) {
        try {
            WorkMode.valueOf(value.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @JsonCreator
    public static WorkMode fromString(String value) {
        return WorkMode.valueOf(value.toUpperCase());
    }

}
