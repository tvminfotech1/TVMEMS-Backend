package com.tvm.internal.tvm_internal_project.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "work_from_home")
public class WorkFromHome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    private Long employeeId;
    private String employeeEmail;
    private String employeeName;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Long days;
    private String reason;
    private String approver;
    private String status;
    private String action;
}