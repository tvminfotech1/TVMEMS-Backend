package com.tvm.internal.tvm_internal_project.model.onboarding;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tvm.internal.tvm_internal_project.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String qualification;
    private String specilization;
    private String instituteName;
    private String universityName;
    private String time;
    private String fromDate;
    private String toDate;
    private String percentage;
    private String rollNo;
    private String educationType;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private User user;
}

