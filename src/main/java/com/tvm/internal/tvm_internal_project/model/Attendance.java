package com.tvm.internal.tvm_internal_project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
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

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnoreProperties("attendance")
    private User user;
}
