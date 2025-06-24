package com.tvm.internal.tvm_internal_project.model;

public enum LeaveType {
    CASUAL_LEAVE("Casual Leave"),
    SICK_LEAVE("Sick Leave"),
    COMPENSATORY_OFF("Compensatory Off"),
    EARNED_LEAVE("Earned Leave"),
    UNPAID_LEAVE("Unpaid Leave"),
    PRIVILEGE_LEAVE("Privilege Leave"),
    MATERNITY_LEAVE("Maternity Leave"),
    PATERNITY_LEAVE("Paternity Leave"),
    SABBATICAL_LEAVE("Sabbatical Leave");

    private final String description;

    LeaveType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
