package com.tvm.internal.tvm_internal_project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
    public class PayRoleEmployee {
        @Id
        private Long id;
        private String firstName;
        private String lastName;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getReportingManager() {
        return reportingManager;
    }

    public void setReportingManager(String reportingManager) {
        this.reportingManager = reportingManager;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCtc() {
        return ctc;
    }

    public void setCtc(Long ctc) {
        this.ctc = ctc;
    }

    public Long getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Long basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Long getInHandSalary() {
        return inHandSalary;
    }

    public void setInHandSalary(Long inHandSalary) {
        this.inHandSalary = inHandSalary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public PayRoleBankDetails getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(PayRoleBankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }

    public List<SalaryHistory> getSalaryHistoryList() {
        return salaryHistoryList;
    }

    public void setSalaryHistoryList(List<SalaryHistory> salaryHistoryList) {
        this.salaryHistoryList = salaryHistoryList;
    }
}