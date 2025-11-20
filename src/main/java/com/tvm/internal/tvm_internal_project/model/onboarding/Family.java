package com.tvm.internal.tvm_internal_project.model.onboarding;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tvm.internal.tvm_internal_project.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fatherName;
    private String fatherDOB;
    private String motherName;
    private String motherDOB;
    private String spouseName;
    private String spouseDOB;
    private String spouseGender;
    private String children;

    @OneToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private User user;
}
