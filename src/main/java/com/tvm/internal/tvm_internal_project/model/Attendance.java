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
@Table(
        name = "attendance",
        uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "Date"})
)
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Date")
    private Date date;
    private String name;
    private String department;
    @JsonFormat(pattern = "HH:mm")
    public LocalTime entryTime;
    public String remarks;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnoreProperties("attendance")
    private User user;
}
