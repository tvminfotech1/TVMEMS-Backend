package com.tvm.internal.tvm_internal_project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class PayRoleBankDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String branch;
    @OneToOne
    @JoinColumn(name = "payRoleEmployee_id")
    @JsonBackReference
    private PayRoleEmployee payRoleEmployee;

    public String getBankName() {
        return bankName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public PayRoleEmployee getPayRoleEmployee() {
        return payRoleEmployee;
    }

    public void setPayRoleEmployee(PayRoleEmployee payRoleEmployee) {
        this.payRoleEmployee = payRoleEmployee;
    }
}