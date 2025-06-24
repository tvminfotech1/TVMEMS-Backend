package com.tvm.internal.tvm_internal_project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "OfficeHours")
    private LocalTime officeHours;
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "SystemHours")
    private LocalTime systemHours;
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "Total")
    private LocalTime total;
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "Break")
    private LocalTime breakTime;
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "Working")
    private LocalTime workingTime;
    @Column(name = "status")
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
