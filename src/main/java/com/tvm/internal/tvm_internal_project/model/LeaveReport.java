package com.tvm.internal.tvm_internal_project.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;

@Entity
public class LeaveReport
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "employee_id")
    private Long employeeId;  // Required, unique employee ID

    @Column(name = "employee_name")
    private String employeeName; // Required, name of the employee

    @Column(name = "profile_picture",columnDefinition = "LONGBLOB")
    @Lob
    private byte[] profilePicture; // Optional, path to the employee's profile picture

    @Column(name = "leave_booked")
    private Integer leaveBooked; // Optional, number of leave days booked

    @Column(name = "general_timing")
    private String generalTiming; // Optional, general working hours of the employee

    @Column(name = "status", nullable = false)
    private String status="Pending"; // Required, e.g., "active", "on leave"


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


    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Integer getLeaveBooked() {
        return leaveBooked;
    }

    public void setLeaveBooked(Integer leaveBooked) {
        this.leaveBooked = leaveBooked;
    }

    public String getGeneralTiming() {
        return generalTiming;
    }

    public void setGeneralTiming(String generalTiming) {
        this.generalTiming = generalTiming;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
