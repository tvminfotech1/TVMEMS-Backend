package com.tvm.internal.tvm_internal_project.model.onboarding;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tvm.internal.tvm_internal_project.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String skillName;
    private String skillCategories;
    private String versionNum;
    private String experience_year;
    private String experience_month;
    private String selfRate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private User user;
}
