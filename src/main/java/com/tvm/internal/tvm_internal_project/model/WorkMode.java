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

    public String toLabel() {
        return switch (this) {
            case WFO -> "Present";
            case WFH -> "WFH";
            case LEAVE -> "Leave";
            case HOLIDAY -> "Holiday";
        };
    }

    public int toWorkingHours() {
        return switch (this) {
            case WFO -> 8;
            case WFH -> 8;
            default -> 0;
        };
    }
    @JsonCreator
    public static WorkMode fromString(String value) {
        return WorkMode.valueOf(value.toUpperCase());
    }


}
