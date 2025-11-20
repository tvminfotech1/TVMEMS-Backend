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
    @Transient
    private Long employeeId;

}