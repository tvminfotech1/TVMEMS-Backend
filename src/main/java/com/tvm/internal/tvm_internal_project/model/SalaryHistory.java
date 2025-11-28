package com.tvm.internal.tvm_internal_project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SalaryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "salary_id", unique = true, nullable = false)
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

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference(value = "employee-salary")
    private PayRoleEmployee payRoleEmployee;

}