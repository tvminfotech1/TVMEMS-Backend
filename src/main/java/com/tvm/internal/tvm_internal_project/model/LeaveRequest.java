package com.tvm.internal.tvm_internal_project.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LEAVE_TYPE")
    private String leaveType;

    @Column(name = "START_END_DATE")
    private String leaveDate;

    @Column(name = "TOTAL_DAYS")
    private int totalDays;

    @Column(name = "REASON_FOR_LEAVE")
    private String reason;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DATE_OF_REQUEST")
    private LocalDate dateOfRequest;

    @Column(name = "BOOKED")
    private int booked;

    @Column(name = "teamEmail")
    private String teamEmail;

    @Column(name = "COLOR")
    private String color;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveTypes;


    @Hidden
    @ManyToOne
    @JoinColumn(name = "user_id")
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

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
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

    public int getBooked() {
        return booked;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }

    public String getTeamEmail() {
        return teamEmail;
    }

    public void setTeamEmail(String teamEmail) {
        this.teamEmail = teamEmail;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LeaveType getLeaveTypes() {
        return leaveTypes;
    }

    public void setLeaveTypes(LeaveType leaveTypes) {
        this.leaveTypes = leaveTypes;
    }
}
