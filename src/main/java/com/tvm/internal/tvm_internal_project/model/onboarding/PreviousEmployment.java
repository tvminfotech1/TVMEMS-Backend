package com.tvm.internal.tvm_internal_project.model.onboarding;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tvm.internal.tvm_internal_project.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PreviousEmployment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String companyName;
    private String designation;
    private String employmentType;
    private String startDate;
    private String endDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private User user;
}

