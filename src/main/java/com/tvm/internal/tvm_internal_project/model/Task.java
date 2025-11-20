package com.tvm.internal.tvm_internal_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskName;
    private String taskOwner;
    private String project;
    @Lob
    private String description;
    private String priority;
    private LocalDate assignedDate;
    private LocalDate dueDate;
    private String status;
    private LocalDateTime todayDate;
    @PrePersist
    protected void onCreate() {
        this.todayDate = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @JsonProperty("employeeId")
    public Long getEmployeeId() {
        return user != null ? user.getEmployeeId() : null;
    }
}