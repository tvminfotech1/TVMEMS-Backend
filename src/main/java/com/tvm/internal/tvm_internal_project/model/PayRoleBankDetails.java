package com.tvm.internal.tvm_internal_project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class PayRoleBankDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String branch;
    @OneToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference("employee-bank")
    private PayRoleEmployee payRoleEmployee;

}