package com.tvm.internal.tvm_internal_project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
    public class PayRoleEmployee {
        @Id
        private Long id;

        private String fullName;
        private String email;
        private String phone;
        private String gender;
        private String dob;
        private String designation;
        private String department;
        private String joiningDate;
        private String employeeType;
        private String reportingManager;
        private String location;
        private String status;
        private Long ctc;
        private Long basicSalary;
        private Long inHandSalary;
        private String address;
        private String aadhaarNumber;
        private String panNumber;
        private String bloodGroup;
        private String emergencyContact;
        private String profileImageUrl;

        @OneToOne(mappedBy = "payRoleEmployee", cascade = CascadeType.ALL)
        @JsonManagedReference("employee-bank")
        private PayRoleBankDetails bankDetails;

        @OneToMany(mappedBy = "payRoleEmployee", cascade = CascadeType.ALL)
        @JsonManagedReference("employee-salary")
        private List<SalaryHistory> salaryHistoryList;
}