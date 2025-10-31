package com.tvm.internal.tvm_internal_project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Date;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Date")
    private Date date;
    @Schema(example = "12:00:00")
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "OfficeHours")
    private LocalTime officeHours;
    @Schema(example = "12:00:00")
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "SystemHours")
    private LocalTime systemHours;
    @Schema(example = "12:00:00")
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "Total")
    private LocalTime total;
    @Schema(example = "12:00:00")
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "Break")
    private LocalTime breakTime;
    @Schema(example = "12:00:00")
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "Working")
    private LocalTime workingTime;
    private String name;
    private String department;
    private String designation;
    @JsonFormat(pattern = "HH:mm")
    public LocalTime entryTime;
    public String remarks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LocalTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalTime entryTime) {
        this.entryTime = entryTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnoreProperties("attendance")

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(LocalTime officeHours) {
        this.officeHours = officeHours;
    }

    public LocalTime getSystemHours() {
        return systemHours;
    }

    public void setSystemHours(LocalTime systemHours) {
        this.systemHours = systemHours;
    }

    public LocalTime getTotal() {
        return total;
    }

    public void setTotal(LocalTime total) {
        this.total = total;
    }

    public LocalTime getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(LocalTime breakTime) {
        this.breakTime = breakTime;
    }

    public LocalTime getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(LocalTime workingTime) {
        this.workingTime = workingTime;
    }


}
