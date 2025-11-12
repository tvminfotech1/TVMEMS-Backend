package com.tvm.internal.tvm_internal_project.request;

import lombok.Data;

@Data
public class SalaryHistoryRequestDTO {
    private String salaryId;
    private String month;
    private String year;
    private Double basicSalary;
    private Double hra;
    private Double medicalAllowance;
    private Double conveyanceAllowance;
    private Double flexiBenefit;
    private Double leaveTravel;
    private Double specialAllowance;
    private Double pf;
    private Double esi;
    private Double professionalTax;
    private Double incomeTax;
    private Double leaveDeduction;
    private Double otherDeduction;
    private Double netPay;
    private Double ctc;
    private Double remainingCtc;
    private Integer nwd;
    private Integer nol;
    private Long payRoleEmployee;
}
