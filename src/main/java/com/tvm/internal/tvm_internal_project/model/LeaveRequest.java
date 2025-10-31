package com.tvm.internal.tvm_internal_project.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LEAVE_TYPE")
    private String leaveType;

    private String startDate;
    private String endDate;

    @Column(name = "TOTAL_DAYS")
    private int totalDays;

    @Column(name = "REASON_FOR_LEAVE")
    private String reason;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DATE_OF_REQUEST")
    private LocalDate dateOfRequest;

    @PrePersist
    public void onCreate() {
        this.dateOfRequest = LocalDate.now();
    }


    @Hidden
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private User user;

    // 🔹 Extra field just for frontend binding
    @Transient
    private Long employeeId;


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

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }


    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(LocalDate dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public Long getEmployeeId() {
        if (user != null) {
            return user.getEmployeeId();
        }
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}