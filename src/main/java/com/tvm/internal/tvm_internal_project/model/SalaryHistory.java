package com.tvm.internal.tvm_internal_project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class SalaryHistory {

    private String salaryId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @JsonBackReference("employee-salary")
    private PayRoleEmployee payRoleEmployee;

    public String getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(String salaryId) {
        this.salaryId = salaryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Double getHra() {
        return hra;
    }

    public void setHra(Double hra) {
        this.hra = hra;
    }

    public Double getMedicalAllowance() {
        return medicalAllowance;
    }

    public void setMedicalAllowance(Double medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public Double getConveyanceAllowance() {
        return conveyanceAllowance;
    }

    public void setConveyanceAllowance(Double conveyanceAllowance) {
        this.conveyanceAllowance = conveyanceAllowance;
    }

    public Double getFlexiBenefit() {
        return flexiBenefit;
    }

    public void setFlexiBenefit(Double flexiBenefit) {
        this.flexiBenefit = flexiBenefit;
    }

    public Double getLeaveTravel() {
        return leaveTravel;
    }

    public void setLeaveTravel(Double leaveTravel) {
        this.leaveTravel = leaveTravel;
    }

    public Double getSpecialAllowance() {
        return specialAllowance;
    }

    public void setSpecialAllowance(Double specialAllowance) {
        this.specialAllowance = specialAllowance;
    }

    public Double getPf() {
        return pf;
    }

    public void setPf(Double pf) {
        this.pf = pf;
    }

    public Double getEsi() {
        return esi;
    }

    public void setEsi(Double esi) {
        this.esi = esi;
    }

    public Double getProfessionalTax() {
        return professionalTax;
    }

    public void setProfessionalTax(Double professionalTax) {
        this.professionalTax = professionalTax;
    }

    public Double getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(Double incomeTax) {
        this.incomeTax = incomeTax;
    }

    public Double getLeaveDeduction() {
        return leaveDeduction;
    }

    public void setLeaveDeduction(Double leaveDeduction) {
        this.leaveDeduction = leaveDeduction;
    }

    public Double getOtherDeduction() {
        return otherDeduction;
    }

    public void setOtherDeduction(Double otherDeduction) {
        this.otherDeduction = otherDeduction;
    }

    public Double getNetPay() {
        return netPay;
    }

    public void setNetPay(Double netPay) {
        this.netPay = netPay;
    }

    public Double getCtc() {
        return ctc;
    }

    public void setCtc(Double ctc) {
        this.ctc = ctc;
    }

    public Double getRemainingCtc() {
        return remainingCtc;
    }

    public void setRemainingCtc(Double remainingCtc) {
        this.remainingCtc = remainingCtc;
    }

    public Integer getNwd() {
        return nwd;
    }

    public void setNwd(Integer nwd) {
        this.nwd = nwd;
    }

    public Integer getNol() {
        return nol;
    }

    public void setNol(Integer nol) {
        this.nol = nol;
    }

    public PayRoleEmployee getPayRoleEmployee() {
        return payRoleEmployee;
    }

    public void setPayRoleEmployee(PayRoleEmployee payRoleEmployee) {
        this.payRoleEmployee = payRoleEmployee;
    }
}